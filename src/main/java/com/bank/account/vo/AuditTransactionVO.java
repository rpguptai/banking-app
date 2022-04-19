/**
 * 
 */
package com.bank.account.vo;

import java.time.LocalDateTime;

import com.bank.account.model.Account;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ravi
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuditTransactionVO {	
	private String transactionType;		
	private String reference;
	private LocalDateTime transactionDate;	
	private Double amount;	
	private String sourceAccount;
	private String targetAccount;
}
