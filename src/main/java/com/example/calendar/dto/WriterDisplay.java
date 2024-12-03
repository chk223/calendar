package com.example.calendar.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class WriterDisplay {
    private String name;
    private String email;
    private String joinedAt;
    private String updatedAt;

    public WriterDisplay(String name, String email, LocalDateTime joinedAt, LocalDateTime updatedAt) {
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt.format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
        this.updatedAt = updatedAt.format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
    }
}
