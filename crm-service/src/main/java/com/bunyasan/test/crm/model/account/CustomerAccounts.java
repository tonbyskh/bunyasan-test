package com.bunyasan.test.crm.model.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public record CustomerAccounts(
        Long customerId,
        String firstname,
        String lastname,
        @JsonIgnoreProperties({"accountType", "accountBranch", "accountBalance"})
        List<BankAccount> accounts
) {
}
