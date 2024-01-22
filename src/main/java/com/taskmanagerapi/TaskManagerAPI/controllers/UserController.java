package com.taskmanagerapi.TaskManagerAPI.controllers;

import com.taskmanagerapi.TaskManagerAPI.dtos.UserRecordDto;
import com.taskmanagerapi.TaskManagerAPI.models.TaskModel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import com.taskmanagerapi.TaskManagerAPI.models.UserModel;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.taskmanagerapi.TaskManagerAPI.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/taskmanager-api", produces = {"application/json"})
@Tag(name = "TaskManagerAPI")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(summary = "Realiza o upload de usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Upload de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar o upload de arquivo"),
    })
    @PostMapping("/user")
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);

        UserModel savedUser = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @Operation(summary = "Realiza o requisição do usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> userModelList = userRepository.findAll();
        if (!userModelList.isEmpty()) {
            for (UserModel user : userModelList) {
                UUID id = user.getIdUser();
                user.add(linkTo(methodOn(UserController.class).getOneUser(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelList);
    }


    @Operation(summary = "Realiza o requisição do usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> user0 = userRepository.findById(id);
        if (user0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
        }
        user0.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("User list"));
        return ResponseEntity.status(HttpStatus.OK).body(user0.get());
    }

    @Operation(summary = "Realiza o requisição de um usuário especifico", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @GetMapping("/user/{id}/tasks")
    public ResponseEntity<Object> getUserTasks(@PathVariable UUID id) {
        Optional<UserModel> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
        }

        List<TaskModel> userTasks = optionalUser.get().getTasks();

        List<UUID> taskIds = userTasks.stream().map(TaskModel::getIdTask).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(taskIds);
    }

    @Operation(summary = "Deleta um usuário especifico", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @DeleteMapping("/user/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> user0 = userRepository.findById(id);
        if (user0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
        userRepository.delete(user0.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }
    @Operation(summary = "Faz a edição usuário especifico", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição de arquivo realizado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválida"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro ao realizar a requisição do arquivo"),
    })
    @PutMapping("/user/{id}")
    public ResponseEntity<Object> updateUser(@PathVariable(value = "id") UUID id,
                                             @RequestBody @Valid UserRecordDto userRecordDto) {
        Optional<UserModel> user0 = userRepository.findById(id);
        if (user0.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var userModel = user0.get();
        BeanUtils.copyProperties(userRecordDto, userModel);
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.save(userModel));
    }

}
