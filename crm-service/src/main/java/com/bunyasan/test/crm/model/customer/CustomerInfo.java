package com.bunyasan.test.crm.model.customer;

import java.util.Date;

public record CustomerInfo(
        Long id,
        String firstname,
        String lastname,
        String phone,
        Date customerDate,
        Boolean isVip,
        String statusCode,
        Date createdOn,
        Date modifiedOn
) {
}
