package com.taskmanagerapi.TaskManagerAPI.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record UserRecordDto(
        @NotBlank String userName,
        @NotNull Integer userPass,
        @NotBlank String userEmail,
        List<TaskRecordDto> tasks) {
}
