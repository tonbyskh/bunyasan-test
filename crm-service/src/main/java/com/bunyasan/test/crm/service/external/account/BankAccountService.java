package com.bunyasan.test.crm.service.external.account;

import com.bunyasan.test.crm.model.account.BankAccount;
import com.bunyasan.test.crm.model.account.Transaction;
import com.bunyasan.test.crm.model.account.TransactionRequest;

import java.util.List;

public interface BankAccountService {

    List<BankAccount> listCustomerBankAccount(Long customerId);

    BankAccount getCustomerBankAccountDetails(Long accountId);

    List<Transaction> postCustomerBankAccountTransactions(Long accountId, TransactionRequest transactionRequest);
}
