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



@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Card {
	
	public enum CardType {
		CREDIT("CREDIT"), DEBIT("DEBIT");
		private final String type;
		CardType(final String type) {
			this.type = type;
		}
		public String getType() {
			return type;
		}
	}

	@Id
	@Type(type="uuid-char")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	@Enumerated(EnumType.STRING)
	@Column(name="card_type")
	private CardType cardType;
	
	
	@Column(name="card_number",length = 20)
	private String cardNumber;
	
	@Column(name="card_cvv")
	private int cvv;
	
	private LocalDateTime created;

}
