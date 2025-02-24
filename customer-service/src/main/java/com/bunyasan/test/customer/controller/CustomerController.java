package com.bunyasan.test.customer.controller;

import com.bunyasan.test.customer.model.entity.Customer;
import com.bunyasan.test.customer.model.request.CustomerPatch;
import com.bunyasan.test.customer.service.CustomerService;
import com.bunyasan.test.exception.BindingErrorException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Get all customers")
    @GetMapping
    public ResponseEntity<List<Customer>> findAll() {
        List<Customer> customers = customerService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(customers);
    }

    @Operation(summary = "Get customer by id")
    @GetMapping("/{customerId}")
    public ResponseEntity<Customer> get(
            @PathVariable Long customerId
    ) {
        Customer response = customerService.get(customerId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Create customer")
    @PostMapping
    public ResponseEntity<Customer> create(
            @Valid @RequestBody Customer customer,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorException(bindingResult);
        }
        Customer response = customerService.create(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Update customer")
    @PutMapping("/{customerId}")
    public ResponseEntity<Customer> put(
            @PathVariable Long customerId,
            @Valid @RequestBody Customer customer,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorException(bindingResult);
        }
        Customer response = customerService.put(customerId, customer);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Patch customer")
    @PatchMapping("/{customerId}")
    public ResponseEntity<Customer> patch(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerPatch customerPatch,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorException(bindingResult);
        }
        Customer response = customerService.patch(customerId, customerPatch);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(summary = "Delete customer and all sales history")
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Customer> delete(
            @PathVariable Long customerId
    ) {
        customerService.delete(customerId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
