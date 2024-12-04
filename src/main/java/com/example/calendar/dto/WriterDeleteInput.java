package com.example.calendar.dto;

import com.example.calendar.validation.ValidMessage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;
@Getter
public class WriterDeleteInput {
    @NotNull(message = ValidMessage.NOT_NULL)
    private UUID id;

    public WriterDeleteInput(UUID id) {
        this.id = id;
    }
}
