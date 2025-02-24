package com.bunyasan.test.customer.service;

import com.bunyasan.test.customer.model.entity.Customer;
import com.bunyasan.test.customer.model.entity.Sales;
import com.bunyasan.test.customer.model.request.SalesPageRequest;
import com.bunyasan.test.customer.model.response.CustomerSalesRankResponse;
import com.bunyasan.test.customer.repository.CustomerRepository;
import com.bunyasan.test.customer.repository.SalesRepository;
import com.bunyasan.test.exception.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SalesService {
    private final SalesRepository salesRepository;
    private final CustomerRepository customerRepository;

    public Page<Sales> findByPageRequest(SalesPageRequest request) {
        return salesRepository.findAll(request.toPageRequest());
    }

    public Sales create(Long customerId, @Valid Sales sales) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        sales.setCustomer(customer);
        return salesRepository.save(sales);
    }

    public List<CustomerSalesRankResponse> getCustomerSalesRank() {
        return salesRepository.getCustomerSalesRank();
    }
}
