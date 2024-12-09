package com.example.calendar.dto;

import lombok.Getter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class WriterDisplay {
    private final String name;
    private final String email;
    private final String joinedAt;
    private final String updatedAt;

    public WriterDisplay(String name, String email, ZonedDateTime joinedAt, ZonedDateTime updatedAt) {
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
