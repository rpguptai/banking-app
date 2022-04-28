package com.bank.account.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.account.model.Account;
import com.bank.account.model.Card;
import com.bank.account.model.Customer;


/**
 * The Repository AccountRepository.
 */
@Repository
public interface AccountRepository  extends JpaRepository<Account, UUID> {

	/**
	 * Find by account no.
	 *
	 * @param accountNo the account no
	 * @return the optional
	 */
	Optional<Account> findByAccountNo(String accountNo);
	
	/**
	 * Find by customer.
	 *
	 * @param customer the customer
	 * @return the optional
	 */
	Optional<List<Account>> findByCustomer(Customer customer);
	
	/**
	 * Find by card.
	 *
	 * @param card the card
	 * @return the optional
	 */
	Optional<Account> findByCard(Card card);
}
