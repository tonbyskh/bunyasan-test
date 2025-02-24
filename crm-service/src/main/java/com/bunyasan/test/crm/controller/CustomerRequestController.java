package com.bunyasan.test.crm.controller;

import com.bunyasan.test.exception.BindingErrorException;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequest;
import com.bunyasan.test.crm.model.customerrequest.CustomerRequestPatch;
import com.bunyasan.test.crm.service.CustomerRequestService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CustomerRequestController {
    private final CustomerRequestService customerRequestService;

    @PostMapping("/customers/{customerId}/requests")
    @Operation(summary = "create customer request")
    public ResponseEntity<CustomerRequest> createCustomerRequest(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest customerRequest,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorException(bindingResult);
        }
        CustomerRequest response = customerRequestService.createCustomerRequest(customerId, customerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/customers/requests/{customerRequestId}")
    @Operation(summary = "get customer request")
    public ResponseEntity<CustomerRequest> getCustomerRequest(
            @PathVariable Long customerRequestId
    ) {
        CustomerRequest response = customerRequestService.getCustomerRequest(customerRequestId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping("/customers/requests/{customerRequestId}")
    @Operation(summary = "patch customer request")
    public ResponseEntity<CustomerRequest> patchCustomerRequest(
            @PathVariable Long customerRequestId,
            @Valid @RequestBody CustomerRequestPatch customerRequestPatch,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorException(bindingResult);
        }
        CustomerRequest response = customerRequestService.patchCustomerRequest(customerRequestId, customerRequestPatch);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
