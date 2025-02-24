package com.bunyasan.test.crm.service;

import com.bunyasan.test.crm.model.customer.CustomerInfo;
import com.bunyasan.test.crm.service.external.customer.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class CustomerInfoServiceTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerInfoService customerInfoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCustomerInfo_ShouldReturnCustomerInfo() {
        // Arrange
        String phone = "123456789";
        CustomerInfo mockCustomerInfo = new CustomerInfo(1L, "John", "Doe", "000000000", new Date(), true, "001", new Date(), new Date());
        when(customerService.getCustomer(phone)).thenReturn(mockCustomerInfo);

        // Act
        CustomerInfo result = customerInfoService.getCustomerInfo(phone);

        // Assert
        assertNotNull(result);
        assertEquals(mockCustomerInfo, result);
        verify(customerService, times(1)).getCustomer(phone);
    }
}

