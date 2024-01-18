package com.taskmanagerapi.TaskManagerAPI.services;

import com.taskmanagerapi.TaskManagerAPI.dtos.TaskRecordDto;
import com.taskmanagerapi.TaskManagerAPI.models.TaskModel;
import com.taskmanagerapi.TaskManagerAPI.models.UserModel;
import com.taskmanagerapi.TaskManagerAPI.repositories.TaskRepository;
import com.taskmanagerapi.TaskManagerAPI.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public TaskModel createTask(UUID userId, TaskRecordDto taskRecordDto) {
        Optional<UserModel> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        TaskModel taskModel = new TaskModel();
        BeanUtils.copyProperties(taskRecordDto, taskModel);
        taskModel.setUser(optionalUser.get());

        return taskRepository.save(taskModel);
    }
}
