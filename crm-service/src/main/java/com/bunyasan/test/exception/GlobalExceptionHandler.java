package com.bunyasan.test.exception;

import jakarta.servlet.http.HttpServletRequest;
import kong.unirest.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler({UnirestException.class})
    public ResponseEntity<ErrorResponse> handleUnirestException(UnirestException ex, HttpServletRequest request) {
        log.error("Request : {} {}", request.getMethod(), request.getRequestURI());
        log.error("UnirestException : {}", ex.getMessage());
        log.error("Stack trace :", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("500", ex.getMessage(), "", ex.getClass().getName()));
    }

    @ExceptionHandler({BindingErrorException.class})
    public ResponseEntity<ErrorResponse> handleBindingErrorException(BindingErrorException ex, HttpServletRequest request) {
        log.error("Request : {} {}", request.getMethod(), request.getRequestURI());
        log.error("BindingErrorException : {}", ex.getBody());
        log.error("Stack trace :", ex);
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(new ErrorResponse("422", ex.getBody().getDetail(), ex.getBody().getProperties().get("details"), BindingErrorException.class.getName()));
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        log.error("Request : {} {}", request.getMethod(), request.getRequestURI());
        log.error("RuntimeException : {}", ex.getMessage());
        log.error("Stack trace :", ex);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorResponse("500", ex.getMessage(), "", ex.getClass().getName()));
    }

    @ExceptionHandler({HandlerMethodValidationException.class})
    public ResponseEntity<ErrorResponse> handleHandlerMethodValidationException(RuntimeException ex, HttpServletRequest request) {
        log.error("Request : {} {}", request.getMethod(), request.getRequestURI());
        log.error("RuntimeException : {}", ex.getMessage());
        log.error("Stack trace :", ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("400", ex.getMessage(), "", ex.getClass().getName()));
    }

    public record ErrorResponse(String code, String message, Object detail, String exception) {
    }
}
