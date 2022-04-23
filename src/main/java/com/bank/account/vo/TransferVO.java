package com.bank.account.vo;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
		BANK("BANK"), ONLINE("ONLINE");
		private final String type;
		TransferType(final String type) {
			this.type = type;
		}
		public String getType() {
			return type;
		}
	}

	@NotEmpty(message = "Source Account number is required")
	private String sourceAccountNo;

	@NotEmpty(message = "Target Account number is required")
	private String targetAccountNo;

	@NotNull(message = "Please provide a vaild amount")
	@DecimalMin("1.00")
	private double amount;

	@NotNull(message = "Please provide a vaild transferType")
	private TransferType transferType;

}
