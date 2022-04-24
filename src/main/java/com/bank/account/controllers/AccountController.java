/**
 * 
 */
package com.bank.account.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.services.AccountService;
import com.bank.account.services.TransactionService;
import com.bank.account.vo.AccountVO;
import com.bank.account.vo.AuditTransactionVO;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@Api(tags = "Rest API for Accounts")
public class AccountController {
	
	@Autowired
	AccountService accountService ;
	
	@Autowired
	TransactionService transactionService;
	
	@PostMapping(value = "/api/accounts/withdraw")
	@ApiOperation(value = "End point to send withdraw money request")
	public String withdrawMoney(@RequestBody @Valid WithdrawVO withdrawVO) {
		return accountService.withdrawMoney(withdrawVO);
	}

	
	@PostMapping(value = "/api/accounts/transfer")
	@ApiOperation(value = "End point to transfer money from one account to another account")
	public String transferMoney(@RequestBody @Valid TransferVO transferVO) {		
		return accountService.transferMoney(transferVO);
	}

	@GetMapping(value = "/api/accounts/{customerId}")
	@ApiOperation(value = "End Point to get all account with balance for one customer")
	public AccountVO getAllAccountForCustomer(@PathVariable String customerId) {
		return accountService.getAllAccountBalanceForCustomer(customerId);
	}
	
	@GetMapping(value = "/api/transactions/{accountNo}")
	@ApiOperation(value = "End Point to get all transactions for one account")
	public List<AuditTransactionVO> getAllTransactionForAccount(@PathVariable String accountNo) {
		return transactionService.getAllTransactionForAccount(accountNo);
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(	  MethodArgumentNotValidException ex) {
	    Map<String, String> errors = new HashMap<>();
	    ex.getBindingResult().getAllErrors().forEach(error -> {
	        String fieldName = ((FieldError) error).getField();
	        String errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
}
