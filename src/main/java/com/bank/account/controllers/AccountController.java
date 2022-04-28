/**
 * 
 */
package com.bank.account.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bank.account.services.AccountService;
import com.bank.account.services.TransactionService;
import com.bank.account.vo.AccountVO;
import com.bank.account.vo.AuditTransactionVO;
import com.bank.account.vo.ResponseVO;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;



// TODO: Auto-generated Javadoc
/** The Constant log. */
@Slf4j
@RestController
@Api(tags = "Rest API for Accounts")
public class AccountController {

	/** The account service. */
	@Autowired
	AccountService accountService;

	/** The transaction service. */
	@Autowired
	TransactionService transactionService;



	/**
	 * Withdraw money.
	 *
	 * @param withdrawVO the withdraw VO
	 * @return the response VO
	 */
	@PostMapping(value = "/api/v1/accounts/withdraw")
	@ApiOperation(value = "End point to send withdraw money request")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully done"),
			@ApiResponse(code = 401, message = "You are not authorized to perform then operation"),
			@ApiResponse(code = 400, message = "There is issue in the request"),
			@ApiResponse(code = 406, message = "The request is not acceptable"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseVO withdrawMoney(@RequestBody @Valid WithdrawVO withdrawVO) {
		return new ResponseVO(accountService.withdrawMoney(withdrawVO),null,null,null);
	}


	/**
	 * Transfer money.
	 *
	 * @param transferVO the transfer VO
	 * @return the response VO
	 */
	@PostMapping(value = "/api/v1/accounts/transfer")
	@ApiOperation(value = "End point to transfer money from one account to another account")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully done"),
			@ApiResponse(code = 401, message = "You are not authorized to perform then operation"),
			@ApiResponse(code = 400, message = "There is issue in the request"),
			@ApiResponse(code = 406, message = "The request is not acceptable"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public ResponseVO transferMoney(@RequestBody @Valid TransferVO transferVO) {
		return new ResponseVO(accountService.transferMoney(transferVO),null,null,null);
	}

	/**
	 * Gets the all account for customer.
	 *
	 * @param customerId the customer id
	 * @return the all account for customer
	 */
	@GetMapping(value = "/api/v1/accounts/{customerId}")
	@ApiOperation(value = "End Point to get all account with balance for one customer")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully done"),
			@ApiResponse(code = 401, message = "You are not authorized to perform then operation"),
			@ApiResponse(code = 400, message = "There is issue in the request"),
			@ApiResponse(code = 406, message = "The request is not acceptable"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	public AccountVO getAllAccountForCustomer(@PathVariable String customerId) {
		return accountService.getAllAccountBalanceForCustomer(customerId);
	}

	/**
	 * Gets the all transaction for account.
	 *
	 * @param accountNo the account no
	 * @return the all transaction for account
	 */
	@GetMapping(value = "/api/v1/transactions/{accountNo}")
	@ApiOperation(value = "End Point to get all transactions for one account")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successfully done"),
			@ApiResponse(code = 401, message = "You are not authorized to perform then operation"),
			@ApiResponse(code = 400, message = "There is issue in the request"),
			@ApiResponse(code = 406, message = "The request is not acceptable"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found") })
	
	public List<AuditTransactionVO> getAllTransactionForAccount(@PathVariable String accountNo) {
		return transactionService.getAllTransactionForAccount(accountNo);
	}


	/**
	 * Handle validation exceptions.
	 *
	 * @param MethodArgumentNotValidException the ex
	 * @return the error response VO
	 */
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseVO handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<String> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String errorMessage = error.getDefaultMessage();
			errors.add(errorMessage);
		});
		return new ResponseVO(null,"ERROR105","Validation Error",errors);
	}
}
