package com.taskmanagerapi.TaskManagerAPI.services;

import com.taskmanagerapi.TaskManagerAPI.models.UserModel;
import com.taskmanagerapi.TaskManagerAPI.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModel> getAllUsersWithTasks() {
        return userRepository.findAllUsersWithTasks();
    }
}
