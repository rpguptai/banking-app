package com.bank.account.services;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.exception.ClientRequestException;
import com.bank.account.exception.DataNotFoundException;
import com.bank.account.exception.FunctionalException;
import com.bank.account.model.Account;
import com.bank.account.model.Card;
import com.bank.account.model.Transaction;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CardRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;

@Service
public class CardPaymentService {
	
	private static final double CREDIT_CHARGES=0.01;

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CardRepository cardRepository;

	@Autowired
	CustomerRepository customerRepository;

	public String transferMoney(TransferVO transferVO) {
		
		double extraCharge;
		double totalAmount;

		Optional<Card> card = cardRepository.findByCardNumber(transferVO.getCardNumber());
		if (!card.isPresent()) {
			throw new ClientRequestException("card number " + transferVO.getCardNumber() + " does not exist");
		}
		if ((transferVO.getCvv() != card.get().getCvv())) {
			throw new ClientRequestException("CVV for card number " + transferVO.getCardNumber() + " is not valid");
		}
		Optional<Account> sourceAccount = accountRepository.findByCard(card.get());
		if (!sourceAccount.isPresent()) {
			throw new ClientRequestException("No account associated with" + transferVO.getCardNumber());
		}
		Optional<Account> targetAccount = accountRepository.findByAccountNo(transferVO.getTargetAccountNo());
		if (!targetAccount.isPresent()) {
			return "Account number " + transferVO.getTargetAccountNo() + " does not exist";
		}

		if (card.get().getCardType().toString().equals("CREDIT")) {
			extraCharge = transferVO.getAmount() * CREDIT_CHARGES;
		} else {
			extraCharge = 0.00;
		}

		totalAmount = transferVO.getAmount() + extraCharge;
		if (isAmountAvailable(totalAmount, sourceAccount.get().getCurrentBalance())) {

			sourceAccount.get().setCurrentBalance(sourceAccount.get().getCurrentBalance() - totalAmount);
			targetAccount.get().setCurrentBalance(targetAccount.get().getCurrentBalance() + transferVO.getAmount());

			Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.TRANSFER)
					.amount(transferVO.getAmount()).sourceAccount(sourceAccount.get())
					.targetAccount(targetAccount.get()).transactionDate(LocalDateTime.now())
					.reference(transferVO.getTransferType()+"card transfer with card number " + transferVO.getCardNumber()).charges(extraCharge)
					.build();

			accountRepository.save(sourceAccount.get());
			accountRepository.save(targetAccount.get());
			transactionRepository.save(transaction);

		} else {
			return "No Sufficient limit/balance in card " + transferVO.getCardNumber();
		}
		return "Money Transfer Successful!!";
	}

	public String withdrawMoney(WithdrawVO withdrawVO) {
		double extraCharge;
		double totalAmount;

		Optional<Card> card = cardRepository.findByCardNumber(withdrawVO.getCardNumber());
		if (!card.isPresent()) {
			throw new ClientRequestException("card number " + withdrawVO.getCardNumber() + " does not exist");
		}
		if ((withdrawVO.getCvv() != card.get().getCvv())) {
			throw new ClientRequestException("CVV for card number " + withdrawVO.getCardNumber() + " is not valid");
		}
		Optional<Account> account = accountRepository.findByCard(card.get());
		if (!account.isPresent()) {
			throw new ClientRequestException("No account associated with" + withdrawVO.getCardNumber());
		}

		if (card.get().getCardType().toString().equals("CREDIT")) {
			extraCharge = withdrawVO.getAmount() * CREDIT_CHARGES;
		} else {
			extraCharge = 0.00;
		}

		totalAmount = withdrawVO.getAmount() + extraCharge;
		if (isAmountAvailable(totalAmount, account.get().getCurrentBalance())) {
			account.get().setCurrentBalance(account.get().getCurrentBalance() - totalAmount);

			Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.WITHDRAL)
					.amount(totalAmount).sourceAccount(account.get()).targetAccount(account.get())
					.transactionDate(LocalDateTime.now()).charges(extraCharge)
					.reference(withdrawVO.getWithdrawType()+ " card transfer with card number "+withdrawVO.getCardNumber()+" EXTRA CHARGES FOR CREDIT CARD :" + extraCharge).build();

			accountRepository.save(account.get());
			transactionRepository.save(transaction);

		} else {
			throw new FunctionalException("No Sufficient card limit " + withdrawVO.getCardNumber());
		}
		return "Money Withdral Successful!!";

	}
	private boolean isAmountAvailable(double amount, double accountBalance) {
		return (accountBalance - amount) > 0;
	}
}
