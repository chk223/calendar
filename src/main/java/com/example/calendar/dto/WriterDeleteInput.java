package com.example.calendar.dto;

import lombok.Getter;

import java.util.UUID;
@Getter
public class WriterDeleteInput {
    private UUID id;

    public WriterDeleteInput(UUID id) {
        this.id = id;
    }
}
