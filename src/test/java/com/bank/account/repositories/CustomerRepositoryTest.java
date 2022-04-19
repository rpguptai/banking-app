package com.bank.account.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bank.account.model.Card;
import com.bank.account.model.Customer;

@DataJpaTest
public class CustomerRepositoryTest {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Test
	public void givenCustomer_whenSave_thenGetOk() {

		Customer customer = Customer.builder().customerName("TEST_CUSTOMER1").customerNo("cust12345").customerAddress("INDIA").build();
		customerRepository.save(customer);

		Optional<Customer> customer1 = customerRepository.findByCustomerNo("cust12345");
		assertTrue(customer1.isPresent());
		assertEquals("TEST_CUSTOMER1", customer1.get().getCustomerName());
		assertEquals("INDIA", customer1.get().getCustomerAddress());
		
		
	}

}
