package com.bunyasan.test.crm.controller;

import com.bunyasan.test.crm.model.account.BankAccount;
import com.bunyasan.test.crm.model.account.CustomerAccounts;
import com.bunyasan.test.crm.model.account.CustomerTransactions;
import com.bunyasan.test.crm.model.account.TransactionRequest;
import com.bunyasan.test.crm.service.CustomerBankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerBankAccountController {
    private final CustomerBankAccountService customerBankAccountService;

    @GetMapping("/customers/{customerId}/accounts")
    @Operation(summary = "list customer account")
    public ResponseEntity<CustomerAccounts> listCustomerAccount(
            @PathVariable Long customerId
    ) {
        CustomerAccounts response = customerBankAccountService.listCustomerAccount(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/customers/{customerId}/accounts/{accountId}/details")
    @Operation(summary = "get customer account details")
    public ResponseEntity<BankAccount> getCustomerAccountDetails(
            @PathVariable Long customerId,
            @PathVariable Long accountId
    ) {
        BankAccount response = customerBankAccountService.getCustomerAccountDetails(customerId, accountId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("/customers/{customerId}/accounts/{accountId}/transactions-history")
    @Operation(summary = "get customer account details")
    public ResponseEntity<CustomerTransactions> getCustomerAccountTransactions(
            @RequestBody TransactionRequest transactionRequest,
            @PathVariable Long customerId,
            @PathVariable Long accountId
    ) {
        CustomerTransactions response = customerBankAccountService.getCustomerAccountTransactions(customerId, accountId, transactionRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
