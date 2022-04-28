package com.bank.account.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class Accounts.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter

/**
 * Instantiates a new accounts.
 *
 * @param accountNo      the account no
 * @param currentBalance the current balance
 */
@AllArgsConstructor

/**
 * Instantiates a new accounts.
 */
@NoArgsConstructor
public class Accounts {

	/** The account no. */
	private String accountNo;

	/** The current balance. */
	private double currentBalance;
}
