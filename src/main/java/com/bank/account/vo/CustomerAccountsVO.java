/**
 * 
 */
package com.bank.account.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.Setter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class CustomerAccountsVO {
	private String customerId;	
	private String customerName;	
	private List<AccountVO> accountList;
}
