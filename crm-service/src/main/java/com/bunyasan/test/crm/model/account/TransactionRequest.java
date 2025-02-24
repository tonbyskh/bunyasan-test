package com.bunyasan.test.crm.model.account;

import java.util.Date;

public record TransactionRequest(
        Date from,
        Date to
) {
}
