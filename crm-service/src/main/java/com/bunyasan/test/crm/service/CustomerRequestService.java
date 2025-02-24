package com.bunyasan.test.crm.service;

import com.bunyasan.test.crm.model.customerrequest.CustomerRequest;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequestPatch;
import com.bunyasan.test.crm.repository.CustomerRequestRepository;
import com.bunyasan.test.crm.service.external.backendoffice.BackendOfficeService;
import com.bunyasan.test.crm.service.external.customer.CustomerService;
import com.bunyasan.test.exception.CustomerRequestNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bunyasan.test.crm.constants.Constants.STATUS_IN_PROGRESS;
import static com.bunyasan.test.crm.constants.RequestType.OTHER;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomerRequestService {
    private final CustomerRequestRepository customerRequestRepository;
    private final BackendOfficeService backendOfficeService;
    private final CustomerService customerService;

    public CustomerRequest createCustomerRequest(Long customerId, CustomerRequest customerRequest) {
        String type = customerRequest.getTypeCode() == null ? OTHER.code : customerRequest.getTypeCode();
        customerService.verifyCustomer(customerId);
        customerRequest.setCustomerId(customerId);
        customerRequest.setStatus(STATUS_IN_PROGRESS);
        customerRequest.setTypeCode(type);
        customerRequestRepository.save(customerRequest);
        if (type.equals(OTHER.code)) {
            backendOfficeService.forwardToBackendOffice(customerRequest.getCustomerId());
        }
        return customerRequest;
    }

    public CustomerRequest getCustomerRequest(Long customerRequestId) {
        return customerRequestRepository.findById(customerRequestId).orElseThrow(
                () -> new CustomerRequestNotFoundException("Customer request not found")
        );
    }

    public CustomerRequest patchCustomerRequest(Long customerRequestId, CustomerRequestPatch customerRequestPatch) {
        CustomerRequest customerRequest = customerRequestRepository.findById(customerRequestId).orElseThrow(
                () -> new CustomerRequestNotFoundException("Customer request not found")
        );
        if (customerRequestPatch.getDescription() != null)
            customerRequest.setDescription(customerRequestPatch.getDescription());
        if (customerRequestPatch.getStatus() != null) customerRequest.setStatus(customerRequestPatch.getStatus());
        return customerRequestRepository.save(customerRequest);
    }
}
