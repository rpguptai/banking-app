package com.bank.account.vo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class TransferVO {

	public enum TransferType {
		CARD("CARD"), ACCOUNT("ACCOUNT");

		private final String type;

		TransferType(final String type) {
			this.type = type;
		}

		public String getType() {
			return type;
		}
	}

	private String cardNumber;

	private int cvv;

	private String sourceAccountNo;

	@NotEmpty(message = "Target Account number is required")
	private String targetAccountNo;

	@NotNull(message = "Please provide a vaild amount")
	@DecimalMin("1.00")
	private double amount;

	@NotNull(message = "Please provide a vaild transferType")
	private TransferType transferType;

	@JsonIgnore
	@AssertTrue(message = "Please provide account or card details based on your transfer type")
	public boolean isCardOrAccountPresent() {
		return (transferType.toString().equals("CARD") && (cardNumber != null && cvv > 0))
				|| (transferType.toString().equals("ACCOUNT") && (sourceAccountNo != null));
	}
	
	@JsonIgnore
	@AssertTrue(message = "source and target account numbers can not be same")
	public boolean isSourceAndTargetSame() {
		return !sourceAccountNo.equals(targetAccountNo);
	}
}
