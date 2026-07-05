package com.portfolio.taskmanager.dto.task;

import com.portfolio.taskmanager.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record TaskRequest(
        @NotBlank(message = "Título é obrigatório")
        @Size(max = 150, message = "Título deve ter no máximo 150 caracteres")
        String title,

        @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
        String description,

        TaskStatus status,

        LocalDate dueDate
) {}
