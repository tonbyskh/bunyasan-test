package com.bunyasan.test.customer.service;

import com.bunyasan.test.customer.model.entity.Customer;
import com.bunyasan.test.customer.model.request.CustomerPatch;
import com.bunyasan.test.customer.repository.CustomerRepository;
import com.bunyasan.test.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Customer get(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(
                () -> new CustomerNotFoundException("Customer not found")
        );
    }

    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer put(Long customerId, Customer customer) {
        Customer existingCustomer = get(customerId);
        existingCustomer.setFirstname(customer.getFirstname());
        existingCustomer.setLastname(customer.getLastname());
        existingCustomer.setCustomerDate(customer.getCustomerDate());
        existingCustomer.setIsVip(customer.getIsVip());
        existingCustomer.setStatusCode(customer.getStatusCode());
        return customerRepository.save(existingCustomer);
    }

    public Customer patch(Long customerId, CustomerPatch customer) {
        Customer existingCustomer = get(customerId);
        if (customer.getFirstname() != null) existingCustomer.setFirstname(customer.getFirstname());
        if (customer.getLastname() != null) existingCustomer.setLastname(customer.getLastname());
        if (customer.getCustomerDate() != null) existingCustomer.setCustomerDate(customer.getCustomerDate());
        if (customer.getIsVip() != null) existingCustomer.setIsVip(customer.getIsVip());
        if (customer.getStatusCode() != null) existingCustomer.setStatusCode(customer.getStatusCode());
        return customerRepository.save(existingCustomer);
    }

    public void delete(Long customerId) {
        customerRepository.deleteById(customerId);
    }
}
