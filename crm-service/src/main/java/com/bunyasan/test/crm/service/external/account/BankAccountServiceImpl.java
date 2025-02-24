package com.bunyasan.test.crm.service.external.account;

import com.bunyasan.test.crm.model.account.BankAccount;
import com.bunyasan.test.crm.model.account.Transaction;
import com.bunyasan.test.crm.model.account.TransactionRequest;
import com.bunyasan.test.crm.service.external.ApiConfig;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("!local")
public class BankAccountServiceImpl implements BankAccountService {
    private final ApiConfig apiConfig;

    @Override
    public List<BankAccount> listCustomerBankAccount(Long customerId) {
        log.debug("Call GET {}", apiConfig.getAllCustomerBankAccountUrl());
        HttpResponse<BankAccount[]> response = Unirest.get(apiConfig.getAllCustomerBankAccountUrl())
                .routeParam("customerId", String.valueOf(customerId))
                .asObject(BankAccount[].class)
                .ifFailure(
                        res -> {
                            throw new UnirestException("Error to call GET " + apiConfig.getAllCustomerBankAccountUrl() + " with status code " + res.getStatus());
                        }
                );
        return Arrays.asList(response.getBody());
    }

    @Override
    public BankAccount getCustomerBankAccountDetails(Long accountId) {
        log.debug("Call GET {}", apiConfig.getBankAccountDetailUrl());
        HttpResponse<BankAccount> response = Unirest.get(apiConfig.getBankAccountDetailUrl())
                .routeParam("accountId", String.valueOf(accountId))
                .asObject(BankAccount.class)
                .ifFailure(
                        res -> {
                            throw new UnirestException("Error to call GET " + apiConfig.getBankAccountDetailUrl() + " with status code " + res.getStatus());
                        }
                );
        return response.getBody();
    }

    @Override
    public List<Transaction> postCustomerBankAccountTransactions(Long accountId, TransactionRequest transactionRequest) {
        log.debug("Call POST {}", apiConfig.postPostAccountTransactionUrl());
        HttpResponse<Transaction[]> response = Unirest.post(apiConfig.postPostAccountTransactionUrl())
                .body(transactionRequest)
                .contentType("application/json")
                .routeParam("accountId", String.valueOf(accountId))
                .asObject(Transaction[].class)
                .ifFailure(
                        res -> {
                            throw new UnirestException("Error to call GET " + apiConfig.postPostAccountTransactionUrl() + " with status code " + res.getStatus());
                        }
                );
        return Arrays.asList(response.getBody());
    }
}
