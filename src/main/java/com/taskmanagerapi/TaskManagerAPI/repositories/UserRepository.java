package com.taskmanagerapi.TaskManagerAPI.repositories;

import com.taskmanagerapi.TaskManagerAPI.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
}
