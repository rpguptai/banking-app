/**
 * 
 */
package com.bank.account.vo;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Ravi
 *
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class WithdrawVO {
	
	public enum WithdrawType {
		BANK("BANK"), ATM("ATM");
		private final String type;
		WithdrawType(final String type) {
			this.type = type;
		}
		public String getType() {
			return type;
		}
	}

	@NotNull(message = "Please provide a vaild amount")
	@DecimalMin("1.00")
	private double amount;

	@NotNull(message = "Please provide a vaild withdrawType")
	private WithdrawType withdrawType;
	
	private String accountNo;
	
	private String cardNo;
	
	private Integer cvv;
	
	@AssertTrue(message="Please provide either Account no or Card details")
    public boolean isValid(){
        return (accountNo!=null || (cardNo!=null && cvv!=null ));
    }

}
