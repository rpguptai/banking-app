package com.bank.account.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bank.account.model.Account;
import com.bank.account.model.Transaction;

@DataJpaTest
public class TransactionRepositoryTest {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void givenTransaction_whenRetrive_thenGetOk() {

		Optional<Account> sourceAccount = accountRepository.findByAccountNo("NLBANK11223344");
		Optional<Account> targetAccount = accountRepository.findByAccountNo("NLBANK11223377");

		Transaction transaction = Transaction.builder().transactionType(Transaction.TransactionType.WITHDRAL)
				.amount(59995.00).sourceAccount(sourceAccount.get()).targetAccount(targetAccount.get())
				.transactionDate(LocalDateTime.now()).build();

		transactionRepository.save(transaction);

		Optional<List<Transaction>> transactionList = transactionRepository.findBySourceAccount(sourceAccount.get());
		assertTrue(transactionList.isPresent());
		assertEquals(1, transactionList.get().size());
	}

}
