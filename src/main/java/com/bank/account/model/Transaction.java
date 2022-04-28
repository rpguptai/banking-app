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

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * The Class Transaction.
 */
@Entity
@Data
@Builder

/**
 * Instantiates a new transaction.
 *
 * @param id the id
 * @param transactionType the transaction type
 * @param transactionDate the transaction date
 * @param reference the reference
 * @param amount the amount
 * @param charges the charges
 * @param sourceAccount the source account
 * @param targetAccount the target account
 */
@AllArgsConstructor

/**
 * Instantiates a new transaction.
 */
@NoArgsConstructor
public class Transaction {

	/**
	 * The Enum TransactionType.
	 */
	public enum TransactionType {
		
		/** The transfer. */
		TRANSFER("TRANSFER"), 
 /** The withdral. */
 WITHDRAL("WITHDRAL");

		/** The type. */
		private final String type;

		/**
		 * Instantiates a new transaction type.
		 *
		 * @param type the type
		 */
		TransactionType(final String type) {
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

	/** The transaction type. */
	@Enumerated(EnumType.STRING)
	@Column(name = "transaction_type")
	private TransactionType transactionType;

	/** The transaction date. */
	@Column(name = "transaction_date")
	private LocalDateTime transactionDate;

	/** The reference. */
	private String reference;
	
	/** The amount. */
	private Double amount;
	
	/** The charges. */
	private Double charges;

	/** The source account. */
	@ManyToOne
	@JoinColumn(name = "source_account_id")
	private Account sourceAccount;

	/** The target account. */
	@ManyToOne
	@JoinColumn(name = "target_account_id")
	private Account targetAccount;

}
