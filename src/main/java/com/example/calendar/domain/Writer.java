package com.example.calendar.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Writer {
    private UUID id;
    @Setter
    private String name;
    @Setter
    private String email;
    private LocalDateTime joinedAt;
    @Setter
    private LocalDateTime updatedAt;

    public Writer(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.joinedAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}