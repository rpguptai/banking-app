/**
 * 
 */
package com.bank.account.vo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class WithdrawVO {

	@AllArgsConstructor
	@Getter
	public enum WithdrawType {
		CARD("CARD"), ACCOUNT("ACCOUNT");
		private final String type;
	}

	@NotNull(message = "Please provide a vaild amount")
	@DecimalMin("1.00")
	private double amount;

	private String cardNumber;

	private int cvv;

	private String accountNo;
	
	@NotNull(message = "Please provide a vaild withdrawType")
	private WithdrawType withdrawType;

	@JsonIgnore
	@AssertTrue(message = "Please provide account or card details based on your withdraw type")
	public boolean isCardOrAccountPresent() {
		return (withdrawType!=null && withdrawType.toString().equals("CARD") && (cardNumber != null && cvv > 0))
				|| (withdrawType!=null && withdrawType.toString().equals("ACCOUNT") && (accountNo != null));
	}
}
