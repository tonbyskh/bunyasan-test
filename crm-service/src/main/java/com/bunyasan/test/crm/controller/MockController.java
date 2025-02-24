package com.bunyasan.test.crm.controller;

import com.bunyasan.test.crm.model.account.BankAccount;
import com.bunyasan.test.crm.model.account.Transaction;
import com.bunyasan.test.crm.model.account.TransactionRequest;
import com.bunyasan.test.crm.model.customer.CustomerInfo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1")
@Profile("mock-service")
public class MockController {

    @GetMapping("/customers/query")
    public ResponseEntity<CustomerInfo> getCustomerQuery(
            HttpServletRequest request,
            @RequestParam String phone
    ) {
        log.debug("Test service was call with url : {}", request.getRequestURL().toString());
        CustomerInfo customerInfo = new CustomerInfo(1L, "John", "Doe", phone, new Date(), true, "001", new Date(), new Date());
        return ResponseEntity.status(HttpStatus.OK).body(customerInfo);
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<CustomerInfo> getCustomerInfo(
            HttpServletRequest request,
            @PathVariable Long customerId
    ) {
        log.debug("Test service was call with url : {}", request.getRequestURL().toString());
        CustomerInfo customerInfo = new CustomerInfo(customerId, "John", "Doe", "xxx-xxx-xxxx", new Date(), true, "001", new Date(), new Date());
        return ResponseEntity.status(HttpStatus.OK).body(customerInfo);
    }

    @GetMapping("/customers/{customerId}/verify")
    public ResponseEntity<Void> getVerify(
            HttpServletRequest request,
            @PathVariable Long customerId
    ) {
        log.debug("Test service was call with url : {}", request.getRequestURL().toString());
        log.debug("verify customerId : {}", customerId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/customer-requests/{customerRequestId}/forward")
    public ResponseEntity<Void> getForwardCustomerRequest(
            HttpServletRequest request,
            @PathVariable Long customerRequestId
    ) {
        log.debug("Test service was call with url : {}", request.getRequestURL().toString());
        log.debug("forward customerRequestId : {}", customerRequestId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/customers/{customerId}/bank-accounts")
    public ResponseEntity<List<BankAccount>> getBankAccounts(
            HttpServletRequest request,
            @PathVariable Long customerId
    ) {
        log.debug("Test service was call with url : {}", request.getRequestURL().toString());
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new BankAccount(1L, "1111111111", "Account 1", "Saving", "Branch 1", 100000.00));
        accounts.add(new BankAccount(2L, "2222222222", "Account 2", "Saving", "Branch 2", 200000.00));
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @GetMapping("/bank-accounts/{accountId}")
    public ResponseEntity<BankAccount> getBankAccountDetail(
            HttpServletRequest request,
            @PathVariable Long accountId
    ) {
        log.debug("Test service was call with url : {}", request.getRequestURL().toString());
        BankAccount account = new BankAccount(accountId, "1111111111", "Account 1", "Saving", "Branch 1", 100000.00);
        return ResponseEntity.status(HttpStatus.OK).body(account);
    }

    @PostMapping("/bank-accounts/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> postTransactionHistory(
            HttpServletRequest request,
            @RequestBody TransactionRequest transactionRequest,
            @PathVariable Long accountId
    ) {
        log.debug("Test service was call with url : {}", request.getRequestURL().toString());
        log.debug("transactionRequest : {}", transactionRequest);
        List<Transaction> transactions = new ArrayList<>();
        double runningBalance = 10000.00;
        long i = 1L;
        transactions.add(new Transaction(i++, null, "2222222222", "John Smith", "SCB", new Date(), "Transfer", -1000.00, runningBalance += -1000.00, "Transfer to John Smith"));
        transactions.add(new Transaction(i++, "7777777777", null, "Dana White", "TMB", new Date(), "Deposit", 2000.00, runningBalance += 2000.00, "Freelance Payment"));
        transactions.add(new Transaction(i++, null, "3333333333", "Alice Johnson", "KTB", new Date(), "Transfer", -1200.00, runningBalance += -1200.00, "Bill Payment"));
        transactions.add(new Transaction(i++, "8888888888", null, "Frank Thomas", "BBL", new Date(), "Deposit", 3000.00, runningBalance += 3000.00, "Loan Disbursement"));
        transactions.add(new Transaction(i++, null, "4444444444", "Bob Brown", "BBL", new Date(), "Withdrawal", -500.00, runningBalance += -500.00, "ATM Withdrawal"));
        transactions.add(new Transaction(i++, "9999999999", null, "Grace Hall", "KTB", new Date(), "Deposit", 1800.00, runningBalance += 1800.00, "Refund"));
        transactions.add(new Transaction(i++, null, "5555555555", "Charlie Davis", "KBank", new Date(), "Transfer", -1500.00, runningBalance += -1500.00, "Rent Payment"));
        transactions.add(new Transaction(i++, "1234567890", null, "Hannah Lee", "KBank", new Date(), "Deposit", 1500.00, runningBalance += 1500.00, "Salary"));
        transactions.add(new Transaction(i++, null, "6666666666", "Ella Wilson", "SCB", new Date(), "Transfer", -800.00, runningBalance += -800.00, "Online Shopping"));
        transactions.add(new Transaction(i, "0987654321", null, "Ian Moore", "TMB", new Date(), "Deposit", 2500.00, runningBalance += 2500.00, "Bonus Payment"));

        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }
}
