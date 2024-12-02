package com.example.calendar.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ScheduleDisplay {
    private String todo;
    private String name;
    private String createdAt;
    private String editedAt;

    public ScheduleDisplay(String todo, String name, LocalDateTime createdAt, LocalDateTime editedAt) {
        this.todo = todo;
        this.name = name;
        this.createdAt = createdAt.format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
        this.editedAt = editedAt.format(DateTimeFormatter.ofPattern("yyyy-dd-MM"));
    }

}
