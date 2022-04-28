/**
 * 
 */
package com.bank.account.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.exception.ClientRequestException;
import com.bank.account.model.Account;
import com.bank.account.model.Customer;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CardRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.vo.AccountVO;
import com.bank.account.vo.Accounts;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;


/**
 * service class for account operations.
 */
@Service
public class AccountService {

	/** The account payment service. */
	@Autowired
	AccountPaymentService accountPaymentService;

	/** The card payment service. */
	@Autowired
	CardPaymentService cardPaymentService;

	/** The account repository. */
	@Autowired
	AccountRepository accountRepository;

	/** The customer repository. */
	@Autowired
	CustomerRepository customerRepository;

	/**
	 * Transfer money.
	 *
	 * @param transferVO the transfer VO
	 * @return the string
	 */
	@Transactional
	public String transferMoney(TransferVO transferVO) {

		if (transferVO.getTransferType().toString().equals("CARD")) {
			return cardPaymentService.transferMoney(transferVO);
		} else if (transferVO.getTransferType().toString().equals("ACCOUNT")) {
			return accountPaymentService.transferMoney(transferVO);
		} else {
			throw new ClientRequestException("Transfer type is not valid");
		}

	}

	/**
	 * Withdraw money.
	 *
	 * @param withdrawVO the withdraw VO
	 * @return the string
	 */
	@Transactional
	public String withdrawMoney(WithdrawVO withdrawVO) {

		if (withdrawVO.getWithdrawType().toString().equals("CARD")) {
			return cardPaymentService.withdrawMoney(withdrawVO);
		} else if (withdrawVO.getWithdrawType().toString().equals("ACCOUNT")) {
			return accountPaymentService.withdrawMoney(withdrawVO);
		} else {
			throw new ClientRequestException("WithdrawType type is not valid");
		}
	}

	/**
	 * Gets the all account balance for customer.
	 *
	 * @param customerNo the customer no
	 * @return the all account balance for customer
	 */
	public AccountVO getAllAccountBalanceForCustomer(String customerNo) {
		Optional<Customer> customer = customerRepository.findByCustomerNo(customerNo);
		if (!customer.isPresent()) {
			throw new ClientRequestException("Customer number " + customerNo + " does not exist");
		}
		Optional<List<Account>> accounts = accountRepository.findByCustomer(customer.get());
		if (accounts.isPresent() && !accounts.get().isEmpty()) {
			return new AccountVO(customerNo, accounts.get().stream()
					.map(x -> new Accounts(x.getAccountNo(), x.getCurrentBalance())).collect(Collectors.toList()));
		} else {
			throw new ClientRequestException("There is No Account with customer number " + customerNo);
		}

	}
}
