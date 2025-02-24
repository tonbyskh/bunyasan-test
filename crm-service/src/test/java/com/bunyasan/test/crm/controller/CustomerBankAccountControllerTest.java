package com.bunyasan.test.crm.controller;

import com.bunyasan.test.crm.model.account.*;
import com.bunyasan.test.crm.service.CustomerBankAccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class CustomerBankAccountControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CustomerBankAccountService customerBankAccountService;

    @InjectMocks
    private CustomerBankAccountController customerBankAccountController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerBankAccountController).build();
    }

    @Test
    void testListCustomerAccount() throws Exception {
        Long customerId = 1L;
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new BankAccount(1L, "1111111111", "Account 1", "Saving", "Branch 1", 100000.00));
        accounts.add(new BankAccount(2L, "2222222222", "Account 2", "Saving", "Branch 2", 200000.00));
        CustomerAccounts mockResponse = new CustomerAccounts(customerId, "John", "Doe", accounts);

        when(customerBankAccountService.listCustomerAccount(customerId)).thenReturn(mockResponse);

        mockMvc.perform(get("/customers/{customerId}/accounts", customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accounts[0].accountNumber").value("1111111111"))
                .andExpect(jsonPath("$.accounts[1].accountNumber").value("2222222222"))
                .andExpect(jsonPath("$.accounts[0].accountName").value("Account 1"));
    }

    @Test
    void testGetCustomerAccountDetails() throws Exception {
        Long customerId = 1L;
        Long accountId = 1123L;
        BankAccount mockResponse = new BankAccount(1123L, "2349873298", "Account 1", "Saving", "Branch 1", 100000.00);

        when(customerBankAccountService.getCustomerAccountDetails(customerId, accountId)).thenReturn(mockResponse);

        mockMvc.perform(get("/customers/{customerId}/accounts/{accountId}/details", customerId, accountId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1123L))
                .andExpect(jsonPath("$.accountNumber").value("2349873298"))
                .andExpect(jsonPath("$.accountType").value("Saving"));
    }

    @Test
    void testGetCustomerAccountTransactions() throws Exception {
        Long customerId = 1L;
        Long accountId = 100L;
        TransactionRequest transactionRequest = new TransactionRequest(new Date(), new Date());
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
        CustomerTransactions mockResponse = new CustomerTransactions(customerId, "John", "Doe", "1111111111", "Account 1", transactions);

        when(customerBankAccountService.getCustomerAccountTransactions(customerId, accountId, transactionRequest))
                .thenReturn(mockResponse);

        mockMvc.perform(post("/customers/{customerId}/accounts/{accountId}/transactions-history", customerId, accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.transactions[0].id").value(1L))
                .andExpect(jsonPath("$.transactions[6].receiverAccountNumber").value("5555555555"))
                .andExpect(jsonPath("$.transactions[9].amount").value(2500.00))
                .andExpect(jsonPath("$.transactions[2].transactionType").value("Transfer"));
    }
}