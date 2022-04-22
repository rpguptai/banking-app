/**
 * 
 */
package com.bank.account.vo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Ravi
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@Builder
public class WithdrawVO {

	@NotNull(message = "Please provide a vaild amount")
	@DecimalMin("1.00")
	private double amount;
	
	@NotNull(message = "Please provide a card No")
	private String cardNo;
	
	@NotNull(message = "Please provide cvv")
	private Integer cvv;

}
