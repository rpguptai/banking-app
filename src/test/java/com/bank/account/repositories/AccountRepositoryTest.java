package com.bank.account.repositories;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bank.account.model.Account;

@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void givenAccount_whenSaved_thenGetOk() {

		Optional<Account> sourceAccount = accountRepository.findByAccountNo("NLBANK11223344");
		assertTrue(sourceAccount.isPresent());
	}

}
