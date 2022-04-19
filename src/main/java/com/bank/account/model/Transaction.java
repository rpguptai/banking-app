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

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
	
	public enum TransactionType {
		TRANSFER("TRANSFER"),WITHDRAL("WITHDRAL");
		private final String type;
		TransactionType(final String type) {
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
	@Column(name="transaction_type")
	private TransactionType transactionType;
	
	@Column(name="transaction_date")
	private LocalDateTime transactionDate;
	
	private String reference;
	private Double amount;
	
	@ManyToOne
	@JoinColumn(name = "source_account_id")
	private Account sourceAccount;
	
	@ManyToOne
	@JoinColumn(name = "target_account_id")
	private Account targetAccount;

}
