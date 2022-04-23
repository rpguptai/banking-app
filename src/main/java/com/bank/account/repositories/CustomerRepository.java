package com.bank.account.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.account.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {
	
	Optional<Customer> findByCustomerNo(String cardNo);

}
