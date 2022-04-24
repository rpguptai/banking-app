/**
 * 
 */
package com.bank.account.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.account.exception.ClientRequestException;
import com.bank.account.exception.DataNotFoundException;
import com.bank.account.model.Account;
import com.bank.account.model.Transaction;
import com.bank.account.repositories.AccountRepository;
import com.bank.account.repositories.CardRepository;
import com.bank.account.repositories.CustomerRepository;
import com.bank.account.repositories.TransactionRepository;
import com.bank.account.vo.AuditTransactionVO;

/**
 * service class for transaction operations
 *
 */
@Service
public class TransactionService {

	@Autowired
	TransactionRepository transactionRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CardRepository cardRepository;

	@Autowired
	CustomerRepository customerRepository;

	public List<AuditTransactionVO> getAllTransactionForAccount(String accountNo) {
		Optional<Account> account = accountRepository.findByAccountNo(accountNo);
		if (!account.isPresent()) {
			throw new ClientRequestException("Account number " + accountNo + " does not exist");
		}

		List<AuditTransactionVO> auditTransactionVOList = new ArrayList<AuditTransactionVO>();
		Optional<List<Transaction>> transactionSourceList = transactionRepository.findBySourceAccount(account.get());
		if (transactionSourceList.isPresent() && !transactionSourceList.get().isEmpty()) {
			auditTransactionVOList.addAll(transactionSourceList.get().stream()
					.map(x -> new AuditTransactionVO(x.getTransactionType().toString(), x.getReference(),
							x.getTransactionDate(), x.getAmount(), x.getSourceAccount().getAccountNo(),
							x.getTargetAccount()==null ? null:x.getTargetAccount().getAccountNo()))
					.collect(Collectors.toList()));
		}

		Optional<List<Transaction>> transactionTargetList = transactionRepository.findByTargetAccount(account.get());
		if (transactionTargetList.isPresent() && !transactionTargetList.get().isEmpty()) {
			auditTransactionVOList.addAll(transactionTargetList.get().stream()
					.map(x -> new AuditTransactionVO(x.getTransactionType().toString(), x.getReference(),
							x.getTransactionDate(), x.getAmount(), x.getSourceAccount().getAccountNo(),
							x.getTargetAccount().getAccountNo()))
					.collect(Collectors.toList()));
		}

		if (!auditTransactionVOList.isEmpty()) {
			return auditTransactionVOList;
		} else {
			throw new DataNotFoundException("NO transaction found for the account number " + accountNo);
		}
	}
}
