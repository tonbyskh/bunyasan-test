package com.bunyasan.test.crm.service.external.customer;

import com.bunyasan.test.crm.model.customer.CustomerInfo;

public interface CustomerService {
    CustomerInfo getCustomer(String phone);
    CustomerInfo getCustomerById(Long customerId);
    void verifyCustomer(Long customerId);
}
