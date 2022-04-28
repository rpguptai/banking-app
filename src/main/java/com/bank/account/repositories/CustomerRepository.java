package com.bank.account.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.account.model.Customer;


/**
 * The Repository CustomerRepository.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	
	/**
	 * Find by customer no.
	 *
	 * @param cardNo the card no
	 * @return the optional
	 */
	Optional<Customer> findByCustomerNo(String cardNo);

}
