package com.example.calendar.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorResponse {
    private int statusCode;
    private String message;
    private List<String> messages;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    public ErrorResponse(int statusCode, String message, List<String> messages) {
        this.statusCode = statusCode;
        this.message = message;
        this.messages = messages;
    }
}
