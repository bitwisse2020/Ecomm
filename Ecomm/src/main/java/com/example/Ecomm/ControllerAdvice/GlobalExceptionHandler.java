package com.example.Ecomm.ControllerAdvice;

import com.example.Ecomm.Exception.ProductNotFoundException;
import com.example.Ecomm.Models.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundExceptions(ProductNotFoundException exception){
        com.example.Ecomm.Models.ErrorResponse errorResponse= new ErrorResponse(LocalDateTime.now(), exception.getMessage(),"Product Not Found");
        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
