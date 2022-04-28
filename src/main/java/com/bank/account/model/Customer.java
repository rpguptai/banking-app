package com.bank.account.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Class Customer.
 */
@Entity
@Data
@Builder

/**
 * Instantiates a new customer.
 *
 * @param id the id
 * @param customerName the customer name
 * @param customerNo the customer no
 * @param customerAddress the customer address
 * @param email the email
 * @param created the created
 */
@AllArgsConstructor

/**
 * Instantiates a new customer.
 */
@NoArgsConstructor
public class Customer {	
	
	/** The id. */
	@Id
	@Type(type="uuid-char")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	/** The customer name. */
	@Column(name="customer_name")
	private String customerName;
	
	/** The customer no. */
	@Column(name="customer_no")
	private String customerNo;
	
	/** The customer address. */
	@Column(name="customer_address")
	private String customerAddress;
	
	/** The email. */
	@Column(name="email_id")
	private String email;
	
	/** The created. */
	private LocalDateTime created;
}
