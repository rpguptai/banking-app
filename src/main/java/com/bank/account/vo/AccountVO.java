/**
 * 
 */
package com.bank.account.vo;

import java.util.UUID;

import com.bank.account.vo.TransferVO.TransferType;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Ravi
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountVO {	
	private String accountNo;
	private double currentBalance;
}
