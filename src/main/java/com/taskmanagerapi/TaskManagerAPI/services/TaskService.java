package com.taskmanagerapi.TaskManagerAPI.services;

import com.taskmanagerapi.TaskManagerAPI.dtos.TaskRecordDto;
import com.taskmanagerapi.TaskManagerAPI.models.TaskModel;
import com.taskmanagerapi.TaskManagerAPI.models.UserModel;
import com.taskmanagerapi.TaskManagerAPI.repositories.TaskRepository;
import com.taskmanagerapi.TaskManagerAPI.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<Object> createTask(@RequestBody @Valid TaskRecordDto taskRecordDto,
                                             @PathVariable(value = "id") UUID userId) {
        Optional<UserModel> userOptional = userRepository.findById(userId);
        TaskModel taskModel;
        try {
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            UserModel user = userOptional.get();
            taskModel = new TaskModel();
            BeanUtils.copyProperties(taskRecordDto, taskModel);
            taskModel.setUser(user);

            TaskModel newTask = taskRepository.save(taskModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
