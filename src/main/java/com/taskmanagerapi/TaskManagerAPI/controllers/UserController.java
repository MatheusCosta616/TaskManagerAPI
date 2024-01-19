package com.taskmanagerapi.TaskManagerAPI.controllers;

import com.taskmanagerapi.TaskManagerAPI.dtos.UserRecordDto;
import com.taskmanagerapi.TaskManagerAPI.models.TaskModel;
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
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/user")
    public ResponseEntity<UserModel> createUser(@RequestBody @Valid UserRecordDto userRecordDto) {
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties(userRecordDto, userModel);

        UserModel savedUser = userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @GetMapping("/user")
    public ResponseEntity<List<UserModel>> getAllUsers(){
        List<UserModel> userModelList = userRepository.findAll();
        if(!userModelList.isEmpty()){
            for(UserModel user : userModelList){
                UUID id = user.getIdUser();
                user.add(linkTo(methodOn(UserController.class).getOneUser(id)).withSelfRel());
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(userModelList);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Object> getOneUser(@PathVariable(value = "id") UUID id){
        Optional<UserModel> user0 = userRepository.findById(id);
        if(user0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
        }
        user0.get().add(linkTo(methodOn(UserController.class).getAllUsers()).withRel("User list"));
        return ResponseEntity.status(HttpStatus.OK).body(user0.get());
    }

    @GetMapping("/user/{id}/tasks")
    public ResponseEntity<Object> getUserTasks(@PathVariable UUID id) {
        Optional<UserModel> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not Found");
        }

        List<TaskModel> userTasks = optionalUser.get().getTasks();
        // Mapeie para IDs ou detalhes completos conforme necessário
        List<UUID> taskIds = userTasks.stream().map(TaskModel::getIdTask).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(taskIds);
        // Ou, se você quiser retornar detalhes completos:
        // return ResponseEntity.status(HttpStatus.OK).body(userTasks);
    }




}
