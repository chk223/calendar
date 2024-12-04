package com.example.calendar.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiException extends RuntimeException {
    private final HttpStatus status;
    public ApiException(String message, HttpStatus statusCode) {
        super(message);
        this.status = statusCode;
    }
}
