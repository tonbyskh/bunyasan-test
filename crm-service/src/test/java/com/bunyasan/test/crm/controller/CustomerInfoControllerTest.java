package com.bunyasan.test.crm.controller;

import com.bunyasan.test.crm.model.account.*;
import com.bunyasan.test.crm.model.customer.CustomerInfo;
import com.bunyasan.test.crm.service.CustomerBankAccountService;
import com.bunyasan.test.crm.service.CustomerInfoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerInfoControllerTest {
    private MockMvc mockMvc;

    @Mock
    private CustomerInfoService customerInfoService;

    @InjectMocks
    private CustomerInfoController customerInfoController;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerInfoController).build();
    }

    @Test
    void testGetCustomerInfo_Success() throws Exception {
        String phone = "123456789";
        CustomerInfo customerInfo = new CustomerInfo(1L, "John", "Doe", phone, new Date(), true, "001", new Date(), new Date());
        // Mocking the service method to return a customer info object
        when(customerInfoService.getCustomerInfo(phone)).thenReturn(customerInfo);

        // Perform the GET request and verify the response
        mockMvc.perform(get("/customers/inquiry")
                        .param("phone", phone))
                .andExpect(status().isOk())  // Expecting 200 OK status
                .andExpect(jsonPath("$.phone").value(phone))
                .andExpect(jsonPath("$.firstname").value("John"))
                .andExpect(jsonPath("$.lastname").value("Doe"));
    }
}