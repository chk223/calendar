package com.example.calendar.dto;

import com.example.calendar.validation.ValidMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.UUID;

@Getter
public class WriterUpdateInput {
    @NotNull(message = ValidMessage.NOT_NULL)
    private final UUID id;
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    private final String name;
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    @Email(message = ValidMessage.NOT_EMAIL_FORM)
    private final String email;

    public WriterUpdateInput(UUID id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
