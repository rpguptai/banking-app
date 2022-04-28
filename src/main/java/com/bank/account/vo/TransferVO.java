package com.bank.account.vo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * The Class TransferVO.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class TransferVO {

	/**
	 * The Enum TransferType.
	 */
	public enum TransferType {

		/** The card. */
		CARD("CARD"),
		/** The account. */
		ACCOUNT("ACCOUNT");

		/** The type. */
		private final String type;

		/**
		 * Instantiates a new transfer type.
		 *
		 * @param type the type
		 */
		TransferType(final String type) {
			this.type = type;
		}

		/**
		 * Gets the type.
		 *
		 * @return the type
		 */
		public String getType() {
			return type;
		}
	}

	@ApiModelProperty(name = "cardNumber", value = "The complete card number")
	/** The card number. */
	private String cardNumber;

	/** The cvv. */
	@ApiModelProperty(name = "cvv", value = "cvv for the card 3 digits")
	private int cvv;

	/** The source account no. */
	@ApiModelProperty(name = "sourceAccountNo", value = "Account number of source account")
	private String sourceAccountNo;

	/** The target account no. */
	@NotEmpty(message = "Target Account number is required")
	@ApiModelProperty(name = "targetAccountNo", value = "Account number of target account")
	private String targetAccountNo;

	/** The amount. */
	@NotNull(message = "Please provide a vaild amount")
	@DecimalMin("1.00")
	@ApiModelProperty(name = "amount", value = "minimum amount is 1.00")
	private double amount;

	/** The transfer type. */
	@NotNull(message = "Please provide a vaild transferType")
	@ApiModelProperty(name = "transferType", value = "valid transfer types are CARD or ACCOUNT")
	private TransferType transferType;

	/**
	 * Checks if is card or account present.
	 *
	 * @return true, if is card or account present
	 */
	@JsonIgnore
	@AssertTrue(message = "Please provide account or card details based on your transfer type")
	public boolean isCardOrAccountPresent() {

		return (transferType != null && transferType.toString().equals("CARD") && (cardNumber != null && cvv > 0))
				|| (transferType != null && transferType.toString().equals("ACCOUNT") && (sourceAccountNo != null));
	}

	/**
	 * Checks if is source and target same.
	 *
	 * @return true, if is source and target same
	 */
	@JsonIgnore
	@AssertTrue(message = "source and target account numbers can not be same")
	public boolean isSourceAndTargetSame() {
		return !(sourceAccountNo != null && sourceAccountNo.equals(targetAccountNo));
	}
}
