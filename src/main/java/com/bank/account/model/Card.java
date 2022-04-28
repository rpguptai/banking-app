/**
 * 
 */
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

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * The Class Card.
 */
@Entity
@Data
@Builder

/**
 * Instantiates a new card.
 *
 * @param id the id
 * @param cardType the card type
 * @param cardNumber the card number
 * @param cvv the cvv
 * @param created the created
 */
@AllArgsConstructor

/**
 * Instantiates a new card.
 */
@NoArgsConstructor
public class Card {
	
	/**
	 * The Enum CardType.
	 */
	public enum CardType {
		
		/** The credit. */
		CREDIT("CREDIT"), 
 /** The debit. */
 DEBIT("DEBIT");
		
		/** The type. */
		private final String type;
		
		/**
		 * Instantiates a new card type.
		 *
		 * @param type the type
		 */
		CardType(final String type) {
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
	@Type(type="uuid-char")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	/** The card type. */
	@Enumerated(EnumType.STRING)
	@Column(name="card_type")
	private CardType cardType;
	
	
	/** The card number. */
	@Column(name="card_number",length = 20)
	private String cardNumber;
	
	/** The cvv. */
	@Column(name="card_cvv")
	private int cvv;
	
	/** The created. */
	private LocalDateTime created;

}
