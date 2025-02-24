package com.bunyasan.test.crm.controller;

import com.bunyasan.test.crm.model.customer.CustomerInfo;
import com.bunyasan.test.crm.service.CustomerInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerInfoController {
    private final CustomerInfoService customerInfoService;

    @GetMapping("/customers/inquiry")
    @Operation(summary = "get customer info")
    public ResponseEntity<CustomerInfo> getCustomerInfo(
            @Valid @RequestParam String phone
    ) {
        CustomerInfo customerInfo = customerInfoService.getCustomerInfo(phone);
        return ResponseEntity.status(HttpStatus.OK).body(customerInfo);
    }
}
