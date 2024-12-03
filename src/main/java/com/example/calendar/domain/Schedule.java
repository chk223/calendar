package com.example.calendar.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Schedule {
    private UUID id;
    private String todo;
    private UUID writerId;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Schedule(String todo, UUID writerId, String password) {
        this.todo = todo;
        this.id = UUID.randomUUID();
        this.writerId = writerId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
