/**
 * 
 */
package com.bank.account.vo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class WithdrawVO.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class WithdrawVO {

	/**
	 * Instantiates a new withdraw type.
	 *
	 * @param type the type
	 */
	@AllArgsConstructor

	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	@Getter
	public enum WithdrawType {

		/** The card. */
		CARD("CARD"),
		/** The account. */
		ACCOUNT("ACCOUNT");

		/** The type. */
		private final String type;
	}

	/** The amount. */
	@ApiModelProperty(name = "amount", value = "minimum amount is 1.00r")
	@NotNull(message = "Please provide a vaild amount")
	@DecimalMin("1.00")
	private double amount;

	@ApiModelProperty(name = "cardNumber", value = "The complete card number")
	/** The card number. */
	private String cardNumber;

	/** The cvv. */
	@ApiModelProperty(name = "cvv", value = "cvv for the card 3 digits")
	private int cvv;

	/** The account no. */
	@ApiModelProperty(name = "accountNo", value = "Account number to withdraw money")
	private String accountNo;

	/** The withdraw type. */
	@ApiModelProperty(name = "withdrawType", value = "valid transfer types are CARD or ACCOUNT")
	@NotNull(message = "Please provide a vaild withdrawType")
	private WithdrawType withdrawType;

	/**
	 * Checks if is card or account present.
	 *
	 * @return true, if is card or account present
	 */
	@JsonIgnore
	@AssertTrue(message = "Please provide account or card details based on your withdraw type")
	public boolean isCardOrAccountPresent() {
		return (withdrawType != null && withdrawType.toString().equals("CARD") && (cardNumber != null && cvv > 0))
				|| (withdrawType != null && withdrawType.toString().equals("ACCOUNT") && (accountNo != null));
	}
}
