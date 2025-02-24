package com.bunyasan.test.crm.service.external.account;

import com.bunyasan.test.crm.model.account.BankAccount;
import com.bunyasan.test.crm.model.account.Transaction;
import com.bunyasan.test.crm.model.account.TransactionRequest;
import com.bunyasan.test.crm.service.external.ApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("local")
public class BankAccountServiceLocal implements BankAccountService {
    private final ApiConfig apiConfig;
    @Override
    public List<BankAccount> listCustomerBankAccount(Long customerId) {
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new BankAccount(1L, "1111111111", "Account 1", "Saving", "Branch 1", 100000.00));
        accounts.add(new BankAccount(2L, "2222222222", "Account 2", "Saving", "Branch 2", 200000.00));
        return accounts;
    }

    @Override
    public BankAccount getCustomerBankAccountDetails(Long accountId) {
        return new BankAccount(accountId, "1111111111", "Account 1", "Saving", "Branch 1", 100000.00);
    }

    @Override
    public List<Transaction> postCustomerBankAccountTransactions(Long accountId, TransactionRequest transactionRequest) {
        List<Transaction> transactions = new ArrayList<>();
        double runningBalance = 10000.00;
        transactions.add(new Transaction(1L, null, "2222222222", "John Smith", "SCB", new Date(), "Transfer", -1000.00, runningBalance += -1000.00, "Transfer to John Smith"));
        transactions.add(new Transaction(2L, "7777777777", null, "Dana White", "TMB", new Date(), "Deposit", 2000.00, runningBalance += 2000.00, "Freelance Payment"));
        transactions.add(new Transaction(3L, null, "3333333333", "Alice Johnson", "KTB", new Date(), "Transfer", -1200.00, runningBalance += -1200.00, "Bill Payment"));
        transactions.add(new Transaction(4L, "8888888888", null, "Frank Thomas", "BBL", new Date(), "Deposit", 3000.00, runningBalance += 3000.00, "Loan Disbursement"));
        transactions.add(new Transaction(5L, null, "4444444444", "Bob Brown", "BBL", new Date(), "Withdrawal", -500.00, runningBalance += -500.00, "ATM Withdrawal"));
        transactions.add(new Transaction(6L, "9999999999", null, "Grace Hall", "KTB", new Date(), "Deposit", 1800.00, runningBalance += 1800.00, "Refund"));
        transactions.add(new Transaction(7L, null, "5555555555", "Charlie Davis", "KBank", new Date(), "Transfer", -1500.00, runningBalance += -1500.00, "Rent Payment"));
        transactions.add(new Transaction(8L, "1234567890", null, "Hannah Lee", "KBank", new Date(), "Deposit", 1500.00, runningBalance += 1500.00, "Salary"));
        transactions.add(new Transaction(9L, null, "6666666666", "Ella Wilson", "SCB", new Date(), "Transfer", -800.00, runningBalance += -800.00, "Online Shopping"));
        transactions.add(new Transaction(10L, "0987654321", null, "Ian Moore", "TMB", new Date(), "Deposit", 2500.00, runningBalance += 2500.00, "Bonus Payment"));
        return transactions;
    }
}
