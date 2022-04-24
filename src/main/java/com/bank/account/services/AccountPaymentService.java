package com.bank.account.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.exception.ClientRequestException;
import com.bank.account.exception.DataNotFoundException;
import com.bank.account.exception.FunctionalException;
import com.bank.account.model.Account;
import com.bank.account.model.Transaction;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CardRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;

@Service
public class AccountPaymentService {

	private static final double CREDIT_CHARGES = 0.01;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CardRepository cardRepository;

	@Autowired
	CustomerRepository customerRepository;

	public String transferMoney(TransferVO transferVO) {
		Optional<Account> sourceAccount = accountRepository.findByAccountNo(transferVO.getSourceAccountNo());
		if (!sourceAccount.isPresent()) {
			throw new ClientRequestException("Account number " + transferVO.getSourceAccountNo() + " does not exist");
		}

		if (sourceAccount.get().getAccountType().toString().equals("CREDIT")) {
			throw new ClientRequestException(
					"you can not transfer money from credit account directly, please use your credit card");
		}

		Optional<Account> targetAccount = accountRepository.findByAccountNo(transferVO.getTargetAccountNo());
		if (!targetAccount.isPresent()) {
			throw new ClientRequestException("Account number " + transferVO.getTargetAccountNo() + " does not exist");
		}
		if (isAmountAvailable(transferVO.getAmount(), sourceAccount.get().getCurrentBalance())) {

			sourceAccount.get().setCurrentBalance(sourceAccount.get().getCurrentBalance() - transferVO.getAmount());
			targetAccount.get().setCurrentBalance(targetAccount.get().getCurrentBalance() + transferVO.getAmount());

			Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.TRANSFER)
					.amount(transferVO.getAmount()).sourceAccount(sourceAccount.get())
					.targetAccount(targetAccount.get()).transactionDate(LocalDateTime.now())
					.reference(transferVO.getTransferType().toString() + " transfer with account number "
							+ transferVO.getSourceAccountNo())
					.charges(0.00).build();

			accountRepository.save(sourceAccount.get());
			accountRepository.save(targetAccount.get());
			transactionRepository.save(transaction);
		} else {
			throw new FunctionalException("No Sufficient balance in account " + transferVO.getSourceAccountNo());
		}
		return "Money Transfer Successful!!";
	}

	public String withdrawMoney(WithdrawVO withdrawVO) {
		Optional<Account> account = accountRepository.findByAccountNo(withdrawVO.getAccountNo());
		if (!account.isPresent()) {
			throw new ClientRequestException("No account associated with" + withdrawVO.getCardNumber());
		}
		if (account.get().getAccountType().toString().equals("CREDIT")) {
			throw new ClientRequestException(
					"you can not transfer money from credit account directly, please use your credit card");
		}
		if (isAmountAvailable(withdrawVO.getAmount(), account.get().getCurrentBalance())) {
			account.get().setCurrentBalance(account.get().getCurrentBalance() - withdrawVO.getAmount());

			Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.WITHDRAL)
					.amount(withdrawVO.getAmount()).sourceAccount(account.get()).transactionDate(LocalDateTime.now())
					.reference(withdrawVO.getWithdrawType().toString() + " with account number "
							+ withdrawVO.getAccountNo())
					.charges(0.00).build();

			accountRepository.save(account.get());
			transactionRepository.save(transaction);

		} else {
			throw new FunctionalException("No Sufficient balance " + withdrawVO.getCardNumber());
		}
		return "Money Withdral Successful!!";
	}

	private boolean isAmountAvailable(double amount, double accountBalance) {
		return (accountBalance - amount) > 0;
	}

}
