package com.example.calendar.domain;

import com.example.calendar.validation.ValidMessage;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class Schedule {
    private UUID id;
    @Setter
    private String todo;
    private UUID writerId;
    private String password;
    private LocalDateTime createdAt;
    @Setter
    private LocalDateTime updatedAt;

    public Schedule(String todo, UUID writerId, String password) {
        this.todo = todo;
        this.id = UUID.randomUUID();
        this.writerId = writerId;
        this.password = password;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

}
