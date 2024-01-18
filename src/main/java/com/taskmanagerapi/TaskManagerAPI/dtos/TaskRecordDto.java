package com.taskmanagerapi.TaskManagerAPI.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import com.taskmanagerapi.TaskManagerAPI.models.Priority;
import com.taskmanagerapi.TaskManagerAPI.models.Status;

import java.time.LocalDate;

public record TaskRecordDto(
        @NotBlank String taskName,
        @NotNull LocalDate begunDate,
        @NotNull LocalDate finishDate,
        @NotBlank Status status,
        @NotBlank Priority priority,
        @NotBlank String category
        ) {
}
