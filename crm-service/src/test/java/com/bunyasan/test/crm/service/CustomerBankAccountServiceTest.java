package com.bunyasan.test.crm.service;
import com.bunyasan.test.crm.model.account.*;
import com.bunyasan.test.crm.model.customer.CustomerInfo;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequest;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequestPatch;
import com.bunyasan.test.crm.service.external.account.BankAccountService;
import com.bunyasan.test.crm.service.external.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class CustomerBankAccountServiceTest {

    @Mock
    private BankAccountService bankAccountService;

    @Mock
    private CustomerRequestService customerRequestService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerBankAccountService customerBankAccountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void listCustomerAccount_ShouldReturnCustomerAccounts() {
        Long customerId = 1L;
        CustomerInfo customerInfo = new CustomerInfo(1L, "John", "Doe", "000000000", new Date(), true, "001", new Date(), new Date());
        List<BankAccount> accounts = new ArrayList<>();
        accounts.add(new BankAccount(1L, "1111111111", "Account 1", "Saving", "Branch 1", 100000.00));
        accounts.add(new BankAccount(2L, "2222222222", "Account 2", "Saving", "Branch 2", 200000.00));

        when(customerService.getCustomerById(customerId)).thenReturn(customerInfo);
        when(bankAccountService.listCustomerBankAccount(customerId)).thenReturn(accounts);

        CustomerAccounts result = customerBankAccountService.listCustomerAccount(customerId);

        assertThat(result).isNotNull();
        assertThat(result.accounts()).isEqualTo(accounts);
        assertThat(result.firstname()).isEqualTo("John");
        assertThat(result.lastname()).isEqualTo("Doe");
        assertThat(result.accounts().get(0).accountBalance()).isEqualTo(100000.00);

        verify(customerService).getCustomerById(customerId);
        verify(bankAccountService).listCustomerBankAccount(customerId);
        verify(customerRequestService).createCustomerRequest(eq(customerId), any(CustomerRequest.class));
        verify(customerRequestService).patchCustomerRequest(eq(customerId), any(CustomerRequestPatch.class));
    }

    @Test
    void getCustomerAccountDetails_ShouldReturnBankAccount() {
        Long customerId = 1L;
        Long accountId = 101L;
        CustomerInfo customerInfo = new CustomerInfo(1L, "John", "Doe", "000000000", new Date(), true, "001", new Date(), new Date());
        BankAccount bankAccount = new BankAccount(1L, "1111111111", "Account 1", "Saving", "Branch 1", 100000.00);

        when(customerService.getCustomerById(customerId)).thenReturn(customerInfo);
        when(bankAccountService.getCustomerBankAccountDetails(accountId)).thenReturn(bankAccount);

        BankAccount result = customerBankAccountService.getCustomerAccountDetails(customerId, accountId);

        assertThat(result).isEqualTo(bankAccount);

        verify(customerService).getCustomerById(customerId);
        verify(bankAccountService).getCustomerBankAccountDetails(accountId);
        verify(customerRequestService).createCustomerRequest(eq(customerId), any(CustomerRequest.class));
        verify(customerRequestService).patchCustomerRequest(eq(customerId), any(CustomerRequestPatch.class));
    }

    @Test
    void getCustomerAccountTransactions_ShouldReturnCustomerTransactions() {
        Long customerId = 1L;
        Long accountId = 101L;
        TransactionRequest transactionRequest = new TransactionRequest(new Date(), new Date());
        CustomerInfo customerInfo = new CustomerInfo(1L, "John", "Doe", "000000000", new Date(), true, "001", new Date(), new Date());
        BankAccount bankAccount = new BankAccount(1L, "1111111111", "Account 1", "Saving", "Branch 1", 100000.00);
        List<Transaction> transactions = getTransactions(customerId);

        when(customerService.getCustomerById(customerId)).thenReturn(customerInfo);
        when(bankAccountService.getCustomerBankAccountDetails(accountId)).thenReturn(bankAccount);
        when(bankAccountService.postCustomerBankAccountTransactions(accountId, transactionRequest)).thenReturn(transactions);

        CustomerTransactions result = customerBankAccountService.getCustomerAccountTransactions(customerId, accountId, transactionRequest);

        assertThat(result).isNotNull();
        assertThat(result.customerId()).isEqualTo(customerId);
        assertThat(result.firstname()).isEqualTo("John");
        assertThat(result.lastname()).isEqualTo("Doe");
        assertThat(result.accountNumber()).isEqualTo("1111111111");
        assertThat(result.transactions()).hasSize(10);

        verify(customerService).getCustomerById(customerId);
        verify(bankAccountService).getCustomerBankAccountDetails(accountId);
        verify(bankAccountService).postCustomerBankAccountTransactions(accountId, transactionRequest);
        verify(customerRequestService).createCustomerRequest(eq(customerId), any(CustomerRequest.class));
        verify(customerRequestService).patchCustomerRequest(eq(customerId), any(CustomerRequestPatch.class));
    }

    private static List<Transaction> getTransactions(Long customerId) {
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

