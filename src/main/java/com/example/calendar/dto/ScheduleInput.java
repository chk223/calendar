package com.example.calendar.dto;

import com.example.calendar.validation.ValidMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ScheduleInput {
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    @Size(max=200, message = ValidMessage.SIZE_TO_DO)
    private String todo;
    @NotNull(message = ValidMessage.NOT_NULL)
    private UUID writerId;
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    private String password;

    public ScheduleInput(String todo, UUID writerId, String password) {
        this.todo = todo;
        this.writerId = writerId;
        this.password = password;
    }

}
