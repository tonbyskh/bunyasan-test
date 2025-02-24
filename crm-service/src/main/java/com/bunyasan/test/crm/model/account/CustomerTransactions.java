package com.bunyasan.test.crm.model.account;

import java.util.List;

public record CustomerTransactions(
        Long customerId,
        String firstname,
        String lastname,
        String accountNumber,
        String accountName,
//        @JsonIgnoreProperties({"senderAccountNumber", "receiverAccountNumber", "destinationAccountName", "destinationBankName", "reference"})
        List<Transaction> transactions
) {
}
