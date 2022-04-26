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
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bank.account.exception.FunctionalException;
import com.bank.account.model.Account;
import com.bank.account.model.Card;
import com.bank.account.model.Card.CardType;
import com.bank.account.model.Transaction;
import com.bank.account.model.Account.AccountType;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CardRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.services.CardPaymentService;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;

@ExtendWith(SpringExtension.class)
public class CardPaymentServiceTest {

	@Mock
	TransactionRepository transactionRepository;

	@Mock
	AccountRepository accountRepository;

	@Mock
	CardRepository cardRepository;

	@Mock
	CustomerRepository customerRepository;

	
	@InjectMocks
	CardPaymentService  cardPaymentService;

	@Test
	void givenCard_withdrawMoney_no_balance() {
		FunctionalException thrown = Assertions.assertThrows(FunctionalException.class, () -> {
			WithdrawVO withdrawVO = WithdrawVO.builder().amount(500).cardNumber("NL12345678").cvv(234).withdrawType(WithdrawVO.WithdrawType.CARD).build();
			Card card = Card.builder().cardNumber("NL12345678").cvv(234).cardType(CardType.DEBIT).build();
			when(cardRepository.findByCardNumber("NL12345678")).thenReturn(Optional.of(card));
			when(accountRepository.findByCard(card)).thenReturn(
					Optional.of(Account.builder().accountNo("NLBANK11223388").currentBalance(50.00).build()));
			when(accountRepository.save(any())).thenReturn(Account.builder().build());
			when(transactionRepository.save(any())).thenReturn(Transaction.builder().build());
			cardPaymentService.withdrawMoney(withdrawVO);
		});

		assertEquals("No Sufficient money in card NL12345678", thrown.getMessage());
	}
	
	@Test
	void givenCars_TransferMoney_Successful() {		
		TransferVO transferVO = TransferVO.builder().amount(50.0).cardNumber("NL12345678").cvv(234)
				.targetAccountNo("NLBANK11223388").transferType(TransferVO.TransferType.ACCOUNT).build();
		Card card = Card.builder().cardNumber("NL12345678").cvv(234).cardType(CardType.DEBIT).build();
		when(cardRepository.findByCardNumber("NL12345678")).thenReturn(Optional.of(card));
		when(accountRepository.findByCard(card))
				.thenReturn(Optional.of(Account.builder().accountNo("NLBANK11223344").currentBalance(70000.00)
						.accountType(AccountType.SAVING).build()));
		when(accountRepository.findByAccountNo(transferVO.getTargetAccountNo())).thenReturn(Optional.of(Account
				.builder().accountNo("NLBANK11223388").currentBalance(50.00).accountType(AccountType.SAVING).build()));
		when(accountRepository.save(any())).thenReturn(Account.builder().build());
		when(transactionRepository.save(any())).thenReturn(Transaction.builder().build());
		assertEquals("Money Transfer Successful!!", cardPaymentService.transferMoney(transferVO));

	}
	
	@Test
	void givenCard_withdrawMoney_Successful() {

		WithdrawVO withdrawVO = WithdrawVO.builder().amount(500).cardNumber("NL12345678").cvv(234).withdrawType(WithdrawVO.WithdrawType.CARD).build();
		Card card = Card.builder().cardNumber("NL12345678").cvv(234).cardType(CardType.DEBIT).build();
		when(cardRepository.findByCardNumber("NL12345678")).thenReturn(Optional.of(card));
		when(accountRepository.findByCard(card))
				.thenReturn(Optional.of(Account.builder().accountNo("NLBANK11223388").currentBalance(5000.00).build()));
		when(accountRepository.save(any())).thenReturn(Account.builder().build());
		when(transactionRepository.save(any())).thenReturn(Transaction.builder().build());
		assertEquals("Money Withdral Successful!!", cardPaymentService.withdrawMoney(withdrawVO));
	}

}
