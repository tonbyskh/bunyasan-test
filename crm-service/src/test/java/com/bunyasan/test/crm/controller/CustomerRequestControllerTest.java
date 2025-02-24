package com.bunyasan.test.crm.controller;

import com.bunyasan.test.crm.model.customerrequest.CustomerRequest;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequestPatch;
import com.bunyasan.test.crm.service.CustomerRequestService;
import com.bunyasan.test.exception.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Date;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerRequestControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CustomerRequestService customerRequestService;

    @InjectMocks
    private CustomerRequestController customerRequestController;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private CustomerRequest customerRequest;
    private CustomerRequestPatch customerRequestPatch;

    @BeforeEach
    void setUp() {
        customerRequest = new CustomerRequest(1L, 1L, "000", "OTHER", "In-progress", new Date(), new Date());
        customerRequestPatch = new CustomerRequestPatch(null, "Completed");
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerRequestController)
                .setValidator(new LocalValidatorFactoryBean()).build();
    }

    @Test
    void testCreateCustomerRequest() throws Exception {
        Long customerId = 1L;
        CustomerRequest request = customerRequest;
        CustomerRequest response = customerRequest.setId(1L).setStatus("Completed");

        when(customerRequestService.createCustomerRequest(customerId, request)).thenReturn(response);

        mockMvc.perform(post("/customers/{customerId}/requests", customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void testGetCustomerRequest() throws Exception {
        Long customerRequestId = 1L;
        CustomerRequest mockResponse = customerRequest;

        when(customerRequestService.getCustomerRequest(customerRequestId)).thenReturn(mockResponse);

        mockMvc.perform(get("/customers/requests/{customerRequestId}", customerRequestId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.description").value("OTHER"))
                .andExpect(jsonPath("$.status").value("In-progress"));
    }

    @Test
    void testPatchCustomerRequest() throws Exception {
        Long customerRequestId = 1L;
        CustomerRequestPatch patch = customerRequestPatch;
        CustomerRequest mockResponse = customerRequest.setStatus("Completed");

        when(customerRequestService.patchCustomerRequest(customerRequestId, patch)).thenReturn(mockResponse);

        mockMvc.perform(patch("/customers/requests/{customerRequestId}", customerRequestId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk());
    }
}