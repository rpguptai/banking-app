package com.bank.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bank.account.exception.ClientRequestException;
import com.bank.account.model.Account;
import com.bank.account.model.Card;
import com.bank.account.model.Card.CardType;
import com.bank.account.model.Transaction;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CardRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.services.TransactionService;
import com.bank.account.vo.WithdrawVO;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	AccountRepository accountRepository;

	@Mock
	CardRepository cardRepository;

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	TransactionService transactionService;

	@Test
	void givenTransaction_retrive_successfull() {
		Account sourceAccount1 = Account.builder().accountNo("NLBANK11223344").build();
		when(accountRepository.findByAccountNo("NLBANK11223344")).thenReturn(Optional.of(sourceAccount1));

		Account targetAccount1 = Account.builder().accountNo("NLBANK11223377").build();
		when(accountRepository.findByAccountNo("NLBANK11223377")).thenReturn(Optional.of(targetAccount1));

		Optional<Account> sourceAccount = accountRepository.findByAccountNo("NLBANK11223344");
		Optional<Account> targetAccount = accountRepository.findByAccountNo("NLBANK11223377");

		Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.WITHDRAL)
				.amount(59995.00).sourceAccount(sourceAccount.get()).targetAccount(targetAccount.get())
				.transactionDate(LocalDateTime.now()).build();

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		when(transactionRepository.findBySourceAccount(sourceAccount.get())).thenReturn(Optional.of(transactions));

		assertEquals(1, transactionService.getAllTransactionForAccount("NLBANK11223344").size());
	}
	
	@Test
	void givenTransaction_retriveTarget_successfull() {
		Account sourceAccount1 = Account.builder().accountNo("NLBANK11223344").build();
		when(accountRepository.findByAccountNo("NLBANK11223344")).thenReturn(Optional.of(sourceAccount1));

		Account targetAccount1 = Account.builder().accountNo("NLBANK11223377").build();
		when(accountRepository.findByAccountNo("NLBANK11223377")).thenReturn(Optional.of(targetAccount1));

		Optional<Account> sourceAccount = accountRepository.findByAccountNo("NLBANK11223344");
		Optional<Account> targetAccount = accountRepository.findByAccountNo("NLBANK11223377");

		Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.WITHDRAL)
				.amount(59995.00).sourceAccount(sourceAccount.get()).targetAccount(targetAccount.get())
				.transactionDate(LocalDateTime.now()).build();

		List<Transaction> transactions = new ArrayList<Transaction>();
		transactions.add(transaction);

		when(transactionRepository.findBySourceAccount(targetAccount.get())).thenReturn(Optional.of(transactions));

		assertEquals(1, transactionService.getAllTransactionForAccount("NLBANK11223377").size());
	}
	
	@Test
	void givenTransaction_retriveTarget_NotAvailable() {
		ClientRequestException thrown = Assertions.assertThrows(ClientRequestException.class, () -> {
			Account sourceAccount1 = Account.builder().accountNo("NLBANK11223344").build();
		when(accountRepository.findByAccountNo("NLBANK11223344")).thenReturn(Optional.ofNullable(null));
		transactionService.getAllTransactionForAccount("NLBANK11223344");
		});
		assertEquals("Account number NLBANK11223344 does not exist", thrown.getMessage());
	}
}
