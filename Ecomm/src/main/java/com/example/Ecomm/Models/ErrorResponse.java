package com.example.Ecomm.Models;



import java.time.LocalDateTime;

public class ErrorResponse {
    private LocalDateTime dateTime;
    private String exceptionMessage;
    private String message;

    public ErrorResponse(LocalDateTime dateTime, String exceptionMessage, String message) {
        this.dateTime = dateTime;
        this.exceptionMessage = exceptionMessage;
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getExceptionMessage() {
        return exceptionMessage;
    }

    public void setExceptionMessage(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
