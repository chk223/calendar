package com.example.calendar.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
public class Schedule {
    private final UUID id;
    @Setter
    private String todo;
    private final UUID writerId;
    private final String password;
    private final ZonedDateTime createdAt;
    @Setter
    private ZonedDateTime updatedAt;

    public Schedule(UUID id, String todo, UUID writerId, String password, ZonedDateTime createdAt, ZonedDateTime updatedAt) {
        this.id = id;
        this.todo = todo;
        this.writerId = writerId;
        this.password = password;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Schedule(String todo, UUID writerId, String password) {
        this.todo = todo;
        this.id = UUID.randomUUID();
        this.writerId = writerId;
        this.password = password;
        this.createdAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
        this.updatedAt = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
    }

}
