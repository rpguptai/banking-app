/**
 * 
 */
package com.bank.account.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.exception.ClientRequestException;
import com.bank.account.exception.DataNotFoundException;
import com.bank.account.exception.FunctionalException;
import com.bank.account.model.Account;
import com.bank.account.model.Card;
import com.bank.account.model.Customer;
import com.bank.account.model.Transaction;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CardRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.vo.AccountVO;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;

/**
 * service class for account operations
 *
 */
@Service
public class AccountService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CardRepository cardRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Transactional
	public String transferMoney(TransferVO transferVO) {

		Optional<Account> sourceAccount = accountRepository.findByAccountNo(transferVO.getSourceAccountNo());
		if (!sourceAccount.isPresent()) {
			return "Account number " + transferVO.getSourceAccountNo() + " does not exist";
		}
		Optional<Account> targetAccount = accountRepository.findByAccountNo(transferVO.getTargetAccountNo());
		if (!targetAccount.isPresent()) {
			return "Account number " + transferVO.getTargetAccountNo() + " does not exist";
		}

		if (isAmountAvailable(transferVO.getAmount(), sourceAccount.get().getCurrentBalance())) {

			sourceAccount.get().setCurrentBalance(sourceAccount.get().getCurrentBalance() - transferVO.getAmount());
			targetAccount.get().setCurrentBalance(targetAccount.get().getCurrentBalance() + transferVO.getAmount());

			Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.TRANSFER)
					.amount(transferVO.getAmount()).sourceAccount(sourceAccount.get())
					.targetAccount(targetAccount.get()).transactionDate(LocalDateTime.now())
					.reference(transferVO.getTransferType().toString()).build();

			accountRepository.save(sourceAccount.get());
			accountRepository.save(targetAccount.get());
			transactionRepository.save(transaction);

		} else {
			return "No Sufficient balance in account " + transferVO.getSourceAccountNo();
		}

		return "Money Transfer Successful!!";
	}

	@Transactional
	public String withdrawMoney(WithdrawVO withdrawVO) {

		Optional<Card> card = cardRepository.findByCardNumber(withdrawVO.getCardNo());
		if (!card.isPresent()) {
			throw new ClientRequestException("card number " + withdrawVO.getCardNo() + " does not exist");
		}
		if (!withdrawVO.getCvv().equals(card.get().getCvv())) {
			throw new DataNotFoundException("CVV for card number " + withdrawVO.getCardNo() + " is not valid");
		}
		Optional<Account> account = accountRepository.findByCard(card.get());
		if (!account.isPresent()) {
			throw new ClientRequestException("No account associated with" + withdrawVO.getCardNo());
		}

		if (isAmountAvailable(withdrawVO.getAmount(), account.get().getCurrentBalance())) {
			account.get().setCurrentBalance(account.get().getCurrentBalance() - withdrawVO.getAmount());

			Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.WITHDRAL)
					.amount(withdrawVO.getAmount()).sourceAccount(account.get()).targetAccount(account.get())
					.transactionDate(LocalDateTime.now()).build();

			accountRepository.save(account.get());
			transactionRepository.save(transaction);

		} else {
			throw new FunctionalException("No Sufficient balance " + withdrawVO.getCardNo());
		}
		return "Money Withdral Successful!!";
	}

	public List<AccountVO> getAllAccountBalanceForCustomer(String customerNo) {
		Optional<Customer> customer = customerRepository.findByCustomerNo(customerNo);
		if (!customer.isPresent()) {
			throw new ClientRequestException("Customer number " + customerNo + " does not exist");
		}
		Optional<List<Account>> accounts = accountRepository.findByCustomer(customer.get());
		if (accounts.isPresent() && !accounts.get().isEmpty()) {
			return accounts.get().stream().map(x -> new AccountVO(x.getAccountNo(), x.getCurrentBalance()))
					.collect(Collectors.toList());
		} else {
			throw new ClientRequestException("There is No Account with customer number " + customerNo);
		}

	}

	private boolean isAmountAvailable(double amount, double accountBalance) {
		return (accountBalance - amount) > 0;
	}
}
