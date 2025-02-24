package com.bunyasan.test.crm.service;

import com.bunyasan.test.crm.constants.RequestType;
import com.bunyasan.test.crm.model.account.*;
import com.bunyasan.test.crm.model.customer.CustomerInfo;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequest;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequestPatch;
import com.bunyasan.test.crm.service.external.account.BankAccountService;
import com.bunyasan.test.crm.service.external.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.bunyasan.test.crm.constants.Constants.STATUS_COMPLETED;
import static com.bunyasan.test.crm.constants.RequestType.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerBankAccountService {
    private final BankAccountService bankAccountService;
    private final CustomerRequestService customerRequestService;
    private final CustomerService customerService;

    public CustomerAccounts listCustomerAccount(Long customerId) {
        CustomerInfo customerInfo = customerService.getCustomerById(customerId);
        createCustomerRequest(customerInfo.id(), BANK_ACCOUNT_LIST);
        List<BankAccount> accounts = bankAccountService.listCustomerBankAccount(customerId);
        CustomerAccounts customerAccounts = new CustomerAccounts(customerId, customerInfo.firstname(), customerInfo.lastname(), accounts);
        updateCustomerRequest(customerInfo.id());
        return customerAccounts;
    }

    public BankAccount getCustomerAccountDetails(Long customerId, Long accountId) {
        CustomerInfo customerInfo = customerService.getCustomerById(customerId);
        createCustomerRequest(customerInfo.id(), BANK_ACCOUNT_DETAILS);
        BankAccount bankAccount = bankAccountService.getCustomerBankAccountDetails(accountId);
        updateCustomerRequest(customerInfo.id());
        return bankAccount;
    }

    public CustomerTransactions getCustomerAccountTransactions(Long customerId, Long accountId, TransactionRequest transactionRequest) {
        CustomerInfo customerInfo = customerService.getCustomerById(customerId);
        createCustomerRequest(customerInfo.id(), TRANSACTION_HISTORY);
        BankAccount bankAccount = bankAccountService.getCustomerBankAccountDetails(accountId);
        List<Transaction> transactions = bankAccountService.postCustomerBankAccountTransactions(accountId, transactionRequest);
        CustomerTransactions customerTransactions = new CustomerTransactions(customerId, customerInfo.firstname(), customerInfo.lastname(), bankAccount.accountNumber(), bankAccount.accountName(), transactions);
        updateCustomerRequest(customerInfo.id());
        return customerTransactions;
    }

    private void createCustomerRequest(Long customerId, RequestType type) {
        customerRequestService.createCustomerRequest(
                customerId,
                CustomerRequest.builder()
                        .typeCode(type.code)
                        .description(type.label)
                        .build()
        );
    }

    private void updateCustomerRequest(Long customerRequestId) {
        customerRequestService.patchCustomerRequest(
                customerRequestId,
                new CustomerRequestPatch().setStatus(STATUS_COMPLETED)
        );
    }
}
