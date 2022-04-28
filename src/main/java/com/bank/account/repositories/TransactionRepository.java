package com.bank.account.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.account.model.Account;
import com.bank.account.model.Transaction;


/**
 * The Repository TransactionRepository.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
	
	/**
	 * Find by source account.
	 *
	 * @param account the account
	 * @return the optional
	 */
	Optional<List<Transaction>> findBySourceAccount(Account account);
	
	/**
	 * Find by target account.
	 *
	 * @param account the account
	 * @return the optional
	 */
	Optional<List<Transaction>> findByTargetAccount(Account account);

}
