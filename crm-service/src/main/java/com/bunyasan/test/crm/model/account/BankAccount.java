package com.bunyasan.test.crm.model.account;

public record BankAccount (
        Long id,
        String accountNumber,
        String accountName,
        String accountType,
        String accountBranch,
        Double accountBalance
) {
}
