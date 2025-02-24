package com.bunyasan.test.crm.model.account;

import java.util.Date;

public record Transaction(
        Long id,
        String senderAccountNumber,
        String receiverAccountNumber,
        String destinationAccountName,
        String destinationBankName,
        Date transactionDateTime,
        String transactionType,
        Double amount,
        Double balance,
        String reference
) {
}
