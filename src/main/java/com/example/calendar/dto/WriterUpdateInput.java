package com.example.calendar.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class WriterUpdateInput {
    private UUID id;
    private String name;
    private String email;

    public WriterUpdateInput(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
