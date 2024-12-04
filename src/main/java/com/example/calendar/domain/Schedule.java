package com.example.calendar.domain;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Schedule {
    private final UUID id;
    @Setter
    private String todo;
    private final UUID writerId;
    private final String password;
    private final LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Schedule(String todo, UUID writerId, String password) {
        this.todo = todo;
        this.id = UUID.randomUUID();
        this.writerId = writerId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
