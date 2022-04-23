package com.bank.account.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bank.account.model.Account;
import com.bank.account.model.Card;
import com.bank.account.model.Customer;

@DataJpaTest
public class AccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Test
	public void givenAccount_whenSaved_thenGetOk() {
		Optional<Account> sourceAccount = accountRepository.findByAccountNo("NLBANK11223344");
		assertTrue(sourceAccount.isPresent());
	}

	@Test
	public void givenAccount_whenNotSaved_thenGetNotOK() {
		Optional<Account> sourceAccount = accountRepository.findByAccountNo("NLBANK11223999");
		assertFalse(sourceAccount.isPresent());
	}
	
	@Test
	public void givenAccount_whenSaved_thenGetCard() {
		Optional<Account> sourceAccount = accountRepository.findByAccountNo("NLBANK11223344");
		Card card = sourceAccount.get().getCard();
		assertEquals("NL12345678",card.getCardNumber());
	}
	
	@Test
	public void givenAccount_whenSaved_thenGetCustomer() {
		Optional<Account> sourceAccount = accountRepository.findByAccountNo("NLBANK11223344");
		Customer card = sourceAccount.get().getCustomer();
		assertEquals("BANK22222",card.getCustomerNo());
	}
}
