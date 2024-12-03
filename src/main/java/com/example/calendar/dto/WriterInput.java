package com.example.calendar.dto;

import lombok.Getter;

@Getter
public class WriterInput {
    private String name;
    private String email;

    public WriterInput(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
