package com.bank.account.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.bank.account.vo.AccountVO;
import com.bank.account.vo.AuditTransactionVO;
import com.bank.account.vo.TransferVO;
import com.bank.account.vo.WithdrawVO;

@TestMethodOrder(OrderAnnotation.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AccountControllerIntegrationTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void givenCustomer_retrive_accounts_when_valid_Test() {
		ResponseEntity<AccountVO> response = restTemplate.withBasicAuth("admin", "password")
				.getForEntity("http://localhost:" + port + "/api/accounts/BANK22222", AccountVO.class);
		AccountVO accountVO = response.getBody();
		assertEquals(3, accountVO.getAccounts().size());
	}

	@Test
	public void givenCustomer_retrive_accounts_when_invalid_Test() {
		ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password")
				.getForEntity("http://localhost:" + port + "/api/accounts/BANK9699", String.class);
		String responseString = response.getBody();
		assertEquals("{\"message\":\"Customer number BANK9699 does not exist\"}", responseString);
	}

	@Test
	@Order(1)
	public void givenAccounts_performTranfer_when_valid() {
		TransferVO transferVO = TransferVO.builder().amount(50.0).sourceAccountNo("NLBANK11223344")
				.targetAccountNo("NLBANK11223388").transferType(TransferVO.TransferType.ACCOUNT).build();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<TransferVO> requestEntity = new HttpEntity<TransferVO>(transferVO, headers);
		ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password").exchange(
				"http://localhost:" + port + "/api/accounts/transfer", HttpMethod.POST, requestEntity, String.class);
		String responseString = response.getBody();
		assertEquals("Money Transfer Successful!!", responseString);
	}

	@Test
	public void givenAccounts_performTranfer_when_NoBalance() {
		TransferVO transferVO = TransferVO.builder().amount(5000000.0).sourceAccountNo("NLBANK11223344")
				.targetAccountNo("NLBANK11223388").transferType(TransferVO.TransferType.ACCOUNT).build();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<TransferVO> requestEntity = new HttpEntity<TransferVO>(transferVO, headers);
		ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password").exchange(
				"http://localhost:" + port + "/api/accounts/transfer", HttpMethod.POST, requestEntity, String.class);
		String responseString = response.getBody();
		assertEquals("{\"message\":\"No Sufficient balance in account NLBANK11223344\"}", responseString);
	}

	@Test
	@Order(2)
	public void givenAccounts_performWithdraw_when_valid() {
		WithdrawVO withdrawVO = WithdrawVO.builder().amount(500).cardNumber("NL12345678").cvv(234)
				.withdrawType(WithdrawVO.WithdrawType.CARD).build();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<WithdrawVO> requestEntity = new HttpEntity<WithdrawVO>(withdrawVO, headers);
		ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password").exchange(
				"http://localhost:" + port + "/api/accounts/withdraw", HttpMethod.POST, requestEntity, String.class);
		String responseString = response.getBody();
		assertEquals("Money Withdral Successful!!", responseString);
	}

	@Test
	public void givenAccounts_performWithdraw_when_no_balance() {
		WithdrawVO withdrawVO = WithdrawVO.builder().amount(50000000).cardNumber("NL12345678").cvv(234)
				.withdrawType(WithdrawVO.WithdrawType.CARD).build();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<WithdrawVO> requestEntity = new HttpEntity<WithdrawVO>(withdrawVO, headers);
		ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password").exchange(
				"http://localhost:" + port + "/api/accounts/withdraw", HttpMethod.POST, requestEntity, String.class);
		String responseString = response.getBody();
		assertEquals("{\"message\":\"No Sufficient money in card NL12345678\"}", responseString);
	}

	@Test
	@Order(3)
	public void givenTransactions_retriveAll_when_old_data() {
		ResponseEntity<AuditTransactionVO[]> response = restTemplate.withBasicAuth("admin", "password").getForEntity(
				"http://localhost:" + port + "/api/transactions/NLBANK11223344", AuditTransactionVO[].class);
		AuditTransactionVO[] AuditTransactionVOs = response.getBody();
		assertEquals(2, AuditTransactionVOs.length);
	}

	@Test
	public void givenData_callAPI_when_invalidData() {
		WithdrawVO withdrawVO = WithdrawVO.builder().amount(500).cardNumber("NL12345678")
				.withdrawType(WithdrawVO.WithdrawType.CARD).build();
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<WithdrawVO> requestEntity = new HttpEntity<WithdrawVO>(withdrawVO, headers);
		ResponseEntity<String> response = restTemplate.withBasicAuth("admin", "password").exchange(
				"http://localhost:" + port + "/api/accounts/withdraw", HttpMethod.POST, requestEntity, String.class);
		String responseString = response.getBody();
		assertEquals("{\"cardOrAccountPresent\":\"Please provide account or card details based on your withdraw type\"}", responseString);
	}
}
