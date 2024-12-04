package com.example.calendar.dto;

import com.example.calendar.validation.ValidMessage;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class WriterInput {
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    private String name;
    @NotBlank(message = ValidMessage.NOT_BLANK)
    @NotNull(message = ValidMessage.NOT_NULL)
    @Email(message = ValidMessage.NOT_EMAIL_FORM)
    private String email;

    public WriterInput(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
