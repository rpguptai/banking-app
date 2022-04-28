package com.bank.account.model;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Class Account.
 */
@Entity
@Data
@Builder

/**
 * Instantiates a new account.
 *
 * @param id             the id
 * @param accountNo      the account no
 * @param currentBalance the current balance
 * @param accountType    the account type
 * @param card           the card
 * @param customer       the customer
 * @param created        the created
 */
@AllArgsConstructor

/**
 * Instantiates a new account.
 */
@NoArgsConstructor
public class Account {

	/**
	 * The Enum AccountType.
	 */
	public enum AccountType {

		/** The saving. */
		SAVING("SAVING"),
		/** The credit. */
		CREDIT("CREDIT");

		/** The type. */
		private final String type;

		/**
		 * Instantiates a new account type.
		 *
		 * @param type the type
		 */
		AccountType(final String type) {
			this.type = type;
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public String getType() {
			return type;
		}
	}

	/** The id. */
	@Id
	@Type(type = "uuid-char")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	/** The account no. */
	@Column(name = "account_no")
	private String accountNo;

	/** The current balance. */
	private Double currentBalance;

	/** The account type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	private AccountType accountType;

	/** The card. */
	@OneToOne
	@JoinColumn(name = "card_id")
	private Card card;

	/** The customer. */
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	/** The created. */
	private LocalDateTime created;
}
