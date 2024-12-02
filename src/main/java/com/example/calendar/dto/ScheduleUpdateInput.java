package com.example.calendar.dto;

import lombok.Getter;

import java.util.UUID;
@Getter
public class ScheduleUpdateInput {
    private UUID id;
    private String todo;
    private String name;
    private String password;

    public ScheduleUpdateInput(UUID id, String todo, String name, String password) {
        this.id = id;
        this.todo = todo;
        this.name = name;
        this.password = password;
    }
}
