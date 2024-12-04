package com.example.calendar.dto;

import com.example.calendar.validation.ValidMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;
@Getter
public class ScheduleUpdateInput {
    @NotNull(message = ValidMessage.NOT_NULL)
    private final UUID id;
    @Size(max=200, message = ValidMessage.SIZE_TO_DO)
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    private final String todo;
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    private final String password;

    public ScheduleUpdateInput(UUID id, String todo,String password) {
        this.id = id;
        this.todo = todo;
        this.password = password;
    }
}
