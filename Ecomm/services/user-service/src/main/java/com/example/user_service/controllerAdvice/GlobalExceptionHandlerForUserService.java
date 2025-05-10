package com.example.user_service.controllerAdvice;

import com.example.common.Models.ErrorResponse;
import com.example.user_service.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandlerForUserService {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleProductNotFoundExceptions(UserNotFoundException exception) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>(LocalDateTime.now(), exception.getMessage(), "User Not Found");
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
