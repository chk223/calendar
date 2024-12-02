package com.example.calendar.domain;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
public class Schedule {
    private UUID id;
    private String todo;
    private String name;
    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime editedAt;

    public Schedule(String todo, String name, String password) {
        this.todo = todo;
        this.id = UUID.randomUUID();
        this.name = name;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.editedAt = LocalDateTime.now();
    }

    public void setTodo(String todo) {
        this.todo = todo;
    }

    public void setEditedAt(LocalDateTime editedAt) {
        this.editedAt = editedAt;
    }

    public void setName(String name) {
        this.name = name;
    }
}
