package com.example.calendar.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ScheduleInput {
    private String todo;
    private UUID writerId;
    private String password;

    public ScheduleInput(String todo, UUID writerId, String password) {
        this.todo = todo;
        this.writerId = writerId;
        this.password = password;
    }

}
