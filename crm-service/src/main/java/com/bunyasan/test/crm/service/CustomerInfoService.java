package com.bunyasan.test.crm.service;

import com.bunyasan.test.crm.model.customer.CustomerInfo;
import com.bunyasan.test.crm.service.external.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerInfoService {
    private final CustomerService customerService;

    public CustomerInfo getCustomerInfo(String phone) {
        return customerService.getCustomer(phone);
    }
}
