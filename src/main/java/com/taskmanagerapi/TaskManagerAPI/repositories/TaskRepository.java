package com.taskmanagerapi.TaskManagerAPI.repositories;

import com.taskmanagerapi.TaskManagerAPI.models.TaskModel;
import com.taskmanagerapi.TaskManagerAPI.models.UserModel;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findByUser(UserModel user);
}
