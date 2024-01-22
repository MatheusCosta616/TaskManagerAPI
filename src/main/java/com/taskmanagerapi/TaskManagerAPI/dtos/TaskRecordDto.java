package com.taskmanagerapi.TaskManagerAPI.dtos;

import com.taskmanagerapi.TaskManagerAPI.models.Priority;
import com.taskmanagerapi.TaskManagerAPI.models.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record TaskRecordDto(@NotBlank String taskName, @NotNull LocalDate begunDate, @NotNull LocalDate finishDate,
                            @NotNull Status status, @NotNull Priority priority, @NotBlank String category,
                            UUID userId) {
}

