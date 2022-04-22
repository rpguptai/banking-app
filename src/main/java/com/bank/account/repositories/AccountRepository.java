package com.bank.account.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bank.account.model.Account;
import com.bank.account.model.Card;
import com.bank.account.model.Customer;

@Repository
public interface AccountRepository  extends JpaRepository<Account, UUID> {

	Optional<Account> findByAccountNo(String accountNo);
	
	Optional<List<Account>> findByCustomer(Customer customer);
	
	Optional<Account> findByCard(Card card);
}
