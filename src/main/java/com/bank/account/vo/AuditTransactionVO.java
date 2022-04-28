/**
 * 
 */
package com.bank.account.vo;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The Class AuditTransactionVO.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter

/**
 * Instantiates a new audit transaction VO.
 *
 * @param transactionType the transaction type
 * @param reference       the reference
 * @param transactionDate the transaction date
 * @param amount          the amount
 * @param sourceAccount   the source account
 * @param targetAccount   the target account
 */
@AllArgsConstructor
public class AuditTransactionVO {

	/** The transaction type. */
	private String transactionType;

	/** The reference. */
	private String reference;

	/** The transaction date. */
	private LocalDateTime transactionDate;

	/** The amount. */
	private Double amount;

	/** The source account. */
	private String sourceAccount;

	/** The target account. */
	private String targetAccount;
}
