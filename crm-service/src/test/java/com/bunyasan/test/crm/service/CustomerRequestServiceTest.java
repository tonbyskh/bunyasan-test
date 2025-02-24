package com.bunyasan.test.crm.service;

import com.bunyasan.test.crm.model.customerrequest.CustomerRequest;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequestPatch;
import com.bunyasan.test.crm.repository.CustomerRequestRepository;
import com.bunyasan.test.crm.service.external.backendoffice.BackendOfficeService;
import com.bunyasan.test.crm.service.external.customer.CustomerService;
import com.bunyasan.test.exception.CustomerRequestNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static com.bunyasan.test.crm.constants.Constants.STATUS_IN_PROGRESS;
import static com.bunyasan.test.crm.constants.RequestType.OTHER;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerRequestServiceTest {
    @Mock
    private CustomerRequestRepository customerRequestRepository;

    @Mock
    private BackendOfficeService backendOfficeService;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerRequestService customerRequestService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createCustomerRequest_ShouldSaveRequest() {
        Long customerId = 1L;
        CustomerRequest customerRequest = new CustomerRequest();
        customerRequest.setTypeCode(null); // Will be replaced with OTHER.code

        when(customerRequestRepository.save(any(CustomerRequest.class))).thenReturn(customerRequest);

        CustomerRequest result = customerRequestService.createCustomerRequest(customerId, customerRequest);

        assertNotNull(result);
        assertEquals(customerId, result.getCustomerId());
        assertEquals(STATUS_IN_PROGRESS, result.getStatus());
        assertEquals(OTHER.code, result.getTypeCode()); // Ensure the default type is applied

        verify(customerService).verifyCustomer(customerId);
        verify(customerRequestRepository).save(any(CustomerRequest.class));
        verify(backendOfficeService).forwardToBackendOffice(customerId); // Ensure forwarding happens for OTHER
    }

    @Test
    void getCustomerRequest_ShouldReturnCustomerRequest_WhenExists() {
        Long requestId = 1L;
        CustomerRequest expectedRequest = new CustomerRequest();
        expectedRequest.setId(requestId);

        when(customerRequestRepository.findById(requestId)).thenReturn(Optional.of(expectedRequest));

        CustomerRequest result = customerRequestService.getCustomerRequest(requestId);

        assertNotNull(result);
        assertEquals(requestId, result.getId());
    }

    @Test
    void getCustomerRequest_ShouldThrowException_WhenNotFound() {
        Long requestId = 1L;

        when(customerRequestRepository.findById(requestId)).thenReturn(Optional.empty());

        assertThrows(CustomerRequestNotFoundException.class, () -> {
            customerRequestService.getCustomerRequest(requestId);
        });
    }

    @Test
    void patchCustomerRequest_ShouldUpdateFields() {
        Long requestId = 1L;
        CustomerRequest existingRequest = new CustomerRequest();
        existingRequest.setId(requestId);
        existingRequest.setDescription("Old description");
        existingRequest.setStatus("IN_PROGRESS");

        CustomerRequestPatch patch = new CustomerRequestPatch();
        patch.setDescription("New description");
        patch.setStatus("COMPLETED");

        when(customerRequestRepository.findById(requestId)).thenReturn(Optional.of(existingRequest));
        when(customerRequestRepository.save(any(CustomerRequest.class))).thenReturn(existingRequest);

        CustomerRequest result = customerRequestService.patchCustomerRequest(requestId, patch);

        assertEquals("New description", result.getDescription());
        assertEquals("COMPLETED", result.getStatus());

        verify(customerRequestRepository).save(existingRequest);
    }
}
