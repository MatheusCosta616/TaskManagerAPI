package com.taskmanagerapi.TaskManagerAPI.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

public record UserRecordDto(
        @NotBlank String userName,
        @NotNull Integer userPass,
        @NotBlank String userEmail,
        List<UUID> taskIds
) {
}
