package com.example.calendar.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ScheduleDeleteInput {
    private UUID id;
    private String password;

    public ScheduleDeleteInput(UUID id, String password) {
        this.id = id;
        this.password = password;
    }
}
