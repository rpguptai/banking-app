/**
 * 
 */
package com.bank.account.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@AllArgsConstructor
public class AuditTransactionVO {	
	private String transactionType;		
	private String reference;
	private LocalDateTime transactionDate;	
	private Double amount;	
	private String sourceAccount;
	private String targetAccount;
}
