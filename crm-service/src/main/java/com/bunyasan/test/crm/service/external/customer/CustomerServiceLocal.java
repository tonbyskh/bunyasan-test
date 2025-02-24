package com.bunyasan.test.crm.service.external.customer;

import com.bunyasan.test.crm.model.customer.CustomerInfo;
import com.bunyasan.test.crm.service.external.ApiConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
@RequiredArgsConstructor
@Profile("local")
public class CustomerServiceLocal implements CustomerService {
    private final ApiConfig apiConfig;

    @Override
    public CustomerInfo getCustomer(String phone) {
        log.debug("Call GET {}", apiConfig.getCustomerByPhoneUrl());
        return new CustomerInfo(
                1L,
                "John",
                "Doe",
                "1234567890",
                new Date(),
                true,
                "ACTIVE",
                new Date(),
                new Date()
        );
    }

    @Override
    public CustomerInfo getCustomerById(Long customerId) {
        log.debug("call {}", apiConfig.getCustomerByIdUrl());
        return new CustomerInfo(
                1L,
                "John",
                "Doe",
                "1234567890",
                new Date(),
                true,
                "ACTIVE",
                new Date(),
                new Date()
        );
    }

    @Override
    public void verifyCustomer(Long customerId) {
        log.debug("call {}", apiConfig.getVerifyCustomerUrl());
    }
}
