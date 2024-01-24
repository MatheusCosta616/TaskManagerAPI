package com.taskmanagerapi.TaskManagerAPI.controllers;

import com.taskmanagerapi.TaskManagerAPI.dtos.TaskRecordDto;

import com.taskmanagerapi.TaskManagerAPI.models.TaskModel;
import com.taskmanagerapi.TaskManagerAPI.models.UserModel;
import com.taskmanagerapi.TaskManagerAPI.repositories.TaskRepository;
import com.taskmanagerapi.TaskManagerAPI.repositories.UserRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController

public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Autowired
    public TaskController(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    @Operation(summary = "Faz o upload de uma tarefa para um usuário especifico", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @PostMapping("/task/{id}")
    public ResponseEntity<TaskModel> createTask(@RequestBody @Valid TaskRecordDto taskRecordDto,
                                                @PathVariable(value = "id") UUID id) {
        try {
            Optional<UserModel> userOptional = userRepository.findById(id);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            UserModel user = userOptional.get();
            TaskModel taskModel = new TaskModel();
            BeanUtils.copyProperties(taskRecordDto, taskModel);
            taskModel.setUser(user);

            TaskModel newTask = taskRepository.save(taskModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTask);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Realiza o requisição de uma tarefa especifica", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @GetMapping("/task")
    public ResponseEntity<List<TaskModel>> getAllTasks() {
        List<TaskModel> taskModelList = taskRepository.findAll();
        if (!taskModelList.isEmpty()) {
            for (TaskModel tasker : taskModelList) {
                UUID id = tasker.getIdTask();
                tasker.add(linkTo(methodOn(TaskController.class).getOneTask(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskModelList);
    }

    @Operation(summary = "Realiza o requisição de uma tarefa especifica", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @GetMapping("/task/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id") UUID id) {
        Optional<TaskModel> task0 = taskRepository.findById(id);
        if (task0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not Found");
        }
        task0.get().add(linkTo(methodOn(TaskController.class).getAllTasks()).withRel("User list"));
        return ResponseEntity.status(HttpStatus.OK).body(task0.get());
    }

    @Operation(summary = "Edita uma tarefa especifica", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @PutMapping("/task/{id}")
    public ResponseEntity<Object> updateTask(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid TaskRecordDto taskRecordDto) {
        Optional<TaskModel> task0 = taskRepository.findById(id);
        if (task0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found");
        }
        var taskModel = task0.get();
        BeanUtils.copyProperties(taskRecordDto, taskModel);
        return ResponseEntity.status(HttpStatus.OK).body(taskRepository.save(taskModel));
    }


    @Operation(summary = "Deleta uma tarefa especifica", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @DeleteMapping("/task/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable(value = "id") UUID id) {
        Optional<TaskModel> task0 = taskRepository.findById(id);
        if (task0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not found.");
        }
        taskRepository.delete(task0.get());
        return ResponseEntity.status(HttpStatus.OK).body("Task deleted successfully.");
    }

}
