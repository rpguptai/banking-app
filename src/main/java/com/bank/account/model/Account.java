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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Account {
	
	public enum AccountType {
		SAVING("SAVING"), CREDIT("CREDIT");
		private final String type;
		AccountType(final String type) {
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
	
	@Column(name="account_no")
	private String accountNo;
	
	private Double currentBalance;
	
	@Enumerated(EnumType.STRING)
	@Column(name="account_type")
	private AccountType accountType;
	
	@OneToOne
	@JoinColumn(name = "card_id")
	private Card card;
	
	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private LocalDateTime created;
}
