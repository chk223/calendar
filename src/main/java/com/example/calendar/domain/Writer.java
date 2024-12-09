package com.example.calendar.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class Writer {
    private final UUID id;
    @Setter
    private String name;
    @Setter
    private String email;
    private final ZonedDateTime joinedAt;
    @Setter
    private ZonedDateTime updatedAt;

    public Writer(UUID id, String name, String email, ZonedDateTime joinedAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.joinedAt = joinedAt;
        this.updatedAt = updatedAt;
    }

    public Writer(String name, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.email = email;
        this.joinedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}
