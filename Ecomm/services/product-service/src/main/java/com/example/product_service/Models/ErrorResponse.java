package com.example.product_service.Models;



import java.time.LocalDateTime;

public class ErrorResponse<T> {
    private LocalDateTime dateTime;
    private T exceptionMessage;
    private String message;

    public ErrorResponse(LocalDateTime dateTime, String exceptionMessage, String message) {
        this.dateTime = dateTime;
        this.exceptionMessage = (T) exceptionMessage;
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public T getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(T exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
