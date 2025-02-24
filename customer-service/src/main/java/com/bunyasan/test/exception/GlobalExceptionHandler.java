package com.bunyasan.test.exception;

import jakarta.servlet.http.HttpServletRequest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({UnirestException.class})
    public ResponseEntity<ErrorResponse> handleUnirestException(UnirestException ex, HttpServletRequest request) {
        log.error("Request : {} {}", request.getMethod(), request.getRequestURI());
        log.error("UnirestException :{}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse("500", ex.getMessage(), "", ex.getClass().getName()));
    }

    @ExceptionHandler({BindingErrorException.class})
    public ResponseEntity<ErrorResponse> handleBindingErrorException(BindingErrorException ex, HttpServletRequest request) {
        log.error("Request : {} {}", request.getMethod(), request.getRequestURI());
        log.error("BindingErrorException :{}", ex.getBody());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse("422", ex.getBody().getDetail(), ex.getBody().getProperties().get("details"), ex.getClass().getName()));
    }

    @ExceptionHandler({CustomerNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex, HttpServletRequest request) {
        log.error("Request : {} {}", request.getMethod(), request.getRequestURI());
        log.error("CustomerNotFoundException :{}", ex.getDescription());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("404", ex.getDescription(), "", ex.getClass().getName()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("Request : {} {}", request.getMethod(), request.getRequestURI());
        log.error("RuntimeException :{}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("500", ex.getMessage(), "", ex.getClass().getName()));
    }

    public record ErrorResponse(String code, String message, Object detail, String exception) {
    }
}
