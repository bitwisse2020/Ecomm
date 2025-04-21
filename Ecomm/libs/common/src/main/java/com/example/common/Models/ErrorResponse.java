package com.example.common.Models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse<T> {
    private LocalDateTime dateTime;
    private T exceptionMessage;
    private String message;

    public ErrorResponse(LocalDateTime dateTime, String exceptionMessage, String message) {
        this.dateTime = dateTime;
        this.exceptionMessage = (T) exceptionMessage;
        this.message = message;
    }


}
