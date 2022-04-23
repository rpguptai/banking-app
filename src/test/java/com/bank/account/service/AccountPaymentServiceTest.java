package com.bank.account.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bank.account.exception.FunctionalException;
import com.bank.account.model.Account;
import com.bank.account.model.Account.AccountType;
import com.bank.account.model.Card;
import com.bank.account.model.Transaction;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CardRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.services.AccountPaymentService;
import com.bank.account.services.AccountService;
import com.bank.account.services.CardPaymentService;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;

@ExtendWith(SpringExtension.class)
public class AccountPaymentServiceTest {

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	AccountRepository accountRepository;

	@Mock
	CardRepository cardRepository;

	@Mock
	CustomerRepository customerRepository;

	@InjectMocks
	AccountPaymentService accountPaymentService;
	
	@Spy
	CardPaymentService cardPaymentService;

	@Test
	void givenAccounts_transferMoney_successfull() {
		TransferVO transferVO = TransferVO.builder().amount(50.0).sourceAccountNo("NLBANK11223344")
				.targetAccountNo("NLBANK11223388").transferType(TransferVO.TransferType.ACCOUNT).build();
		when(accountRepository.findByAccountNo(transferVO.getSourceAccountNo())).thenReturn(
				Optional.of(Account.builder().accountNo("NLBANK11223344").currentBalance(70000.00).accountType(AccountType.SAVING).build()));
		when(accountRepository.findByAccountNo(transferVO.getTargetAccountNo()))
				.thenReturn(Optional.of(Account.builder().accountNo("NLBANK11223388").currentBalance(50.00).accountType(AccountType.SAVING).build()));
		when(accountRepository.save(any())).thenReturn(Account.builder().build());
		when(transactionRepository.save(any())).thenReturn(Transaction.builder().build());
		assertEquals("Money Transfer Successful!!", accountPaymentService.transferMoney(transferVO));
	}

	@Test
	void givenAccounts_transferMoney_no_balance() {
		TransferVO transferVO = TransferVO.builder().amount(5000000.0).sourceAccountNo("NLBANK11223344")
				.targetAccountNo("NLBANK11223388").transferType(TransferVO.TransferType.ACCOUNT).build();
		when(accountRepository.findByAccountNo(transferVO.getSourceAccountNo())).thenReturn(
				Optional.of(Account.builder().accountNo("NLBANK11223344").currentBalance(70000.00).accountType(AccountType.SAVING).build()));
		when(accountRepository.findByAccountNo(transferVO.getTargetAccountNo()))
				.thenReturn(Optional.of(Account.builder().accountNo("NLBANK11223388").currentBalance(50.00).accountType(AccountType.SAVING).build()));
		when(accountRepository.save(any())).thenReturn(Account.builder().build());
		when(transactionRepository.save(any())).thenReturn(Transaction.builder().build());
		assertEquals("No Sufficient balance in account NLBANK11223344", accountPaymentService.transferMoney(transferVO));
	}

}
