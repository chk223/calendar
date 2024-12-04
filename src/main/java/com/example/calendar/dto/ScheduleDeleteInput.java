package com.example.calendar.dto;

import com.example.calendar.validation.ValidMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ScheduleDeleteInput {
    @NotNull(message = ValidMessage.NOT_NULL)
    private UUID id;
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    private String password;

    public ScheduleDeleteInput(UUID id, String password) {
        this.id = id;
        this.password = password;
    }
}
