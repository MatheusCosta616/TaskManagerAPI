package com.taskmanagerapi.TaskManagerAPI.controllers;

import com.taskmanagerapi.TaskManagerAPI.dtos.TaskRecordDto;
import com.taskmanagerapi.TaskManagerAPI.models.TaskModel;
import com.taskmanagerapi.TaskManagerAPI.services.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/task/{userId}")
    public ResponseEntity<TaskModel> createTask(@PathVariable UUID userId,
                                                @RequestBody @Valid TaskRecordDto taskRecordDto) {
        try {
            TaskModel savedTask = taskService.createTask(userId, taskRecordDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedTask);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
