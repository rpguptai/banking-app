/**
 * 
 */
package com.bank.account.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class AccountVO.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter

/**
 * Instantiates a new account VO.
 *
 * @param customerNumber the customer number
 * @param accounts       the accounts
 */
@AllArgsConstructor

/**
 * Instantiates a new account VO.
 */
@NoArgsConstructor
public class AccountVO {

	/** The customer number. */
	private String customerNumber;

	/** The accounts. */
	private List<Accounts> accounts;
}
