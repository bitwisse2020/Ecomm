package com.example.product_service.ControllerAdvice;


import com.example.common.Models.ErrorResponse;
import com.example.product_service.Exception.CategoryNotFoundException;
import com.example.product_service.Exception.ProductNotFoundException;
import com.example.product_service.Exception.ResourceConflictException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundExceptions(ProductNotFoundException exception) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), exception.getMessage(), "Product Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundExceptions(CategoryNotFoundException exception) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), exception.getMessage(), "Category Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ResourceConflictException.class)
    public ResponseEntity<?> handleResourceConflictExceptionExceptions(ResourceConflictException exception) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), exception.getMessage(), "Resource Conflict");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->
                errorMap.put(error.getField(), error.getDefaultMessage()));
        ErrorResponse<Map<String,String>> errorResponse = new ErrorResponse<>(LocalDateTime.now(), errorMap.toString(), "Invalid argument type.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
