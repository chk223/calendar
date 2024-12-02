package com.example.calendar.dto;

import lombok.Getter;

@Getter
public class ScheduleInput {
    private String todo;
    private String name;
    private String password;

    public ScheduleInput(String todo, String name, String password) {
        this.todo = todo;
        this.name = name;
        this.password = password;
    }

}
