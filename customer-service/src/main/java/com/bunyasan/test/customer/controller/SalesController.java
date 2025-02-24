package com.bunyasan.test.customer.controller;

import com.bunyasan.test.customer.model.entity.Sales;
import com.bunyasan.test.customer.model.request.SalesPageRequest;
import com.bunyasan.test.customer.model.response.CustomerSalesRankResponse;
import com.bunyasan.test.customer.service.SalesService;
import com.bunyasan.test.exception.BindingErrorException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customers")
public class SalesController {

    private final SalesService salesService;

    @Operation(summary = "Get sales by page request")
    @PostMapping("/sales/pages")
    public ResponseEntity<Page<Sales>> findByPageRequest(
            @RequestBody SalesPageRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorException(bindingResult);
        }
        Page<Sales> salesPage = salesService.findByPageRequest(request);
        return ResponseEntity.status(HttpStatus.OK).body(salesPage);
    }

    @Operation(summary = "Create sales")
    @PostMapping("/{customerId}/sales/pages")
    public ResponseEntity<Sales> create(
            @PathVariable Long customerId,
            @Valid @RequestBody Sales sales,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            throw new BindingErrorException(bindingResult);
        }
        Sales response = salesService.create(customerId, sales);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get customer sales rank")
    @GetMapping("/sales/rank")
    public ResponseEntity<List<CustomerSalesRankResponse>> getCustomerSalesRank() {
        List<CustomerSalesRankResponse> response = salesService.getCustomerSalesRank();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
