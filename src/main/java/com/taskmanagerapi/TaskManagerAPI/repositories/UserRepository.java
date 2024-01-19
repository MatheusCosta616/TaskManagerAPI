package com.taskmanagerapi.TaskManagerAPI.repositories;

import com.taskmanagerapi.TaskManagerAPI.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    @Query("SELECT DISTINCT u FROM UserModel u LEFT JOIN FETCH u.tasks")
    List<UserModel> findAllUsersWithTasks();

    @Query("SELECT u FROM UserModel u JOIN FETCH u.tasks WHERE u.idUser = :id")
    Optional<UserModel> findUserWithTasksById(@Param("id") UUID id);
}

