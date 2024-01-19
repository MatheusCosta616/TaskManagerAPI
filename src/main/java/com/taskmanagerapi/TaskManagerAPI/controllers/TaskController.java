package com.taskmanagerapi.TaskManagerAPI.controllers;

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


    @GetMapping("/task")
    public ResponseEntity<List<TaskModel>> getAllTasks(){
        List<TaskModel> taskModelList = taskRepository.findAll();
        if(!taskModelList.isEmpty()){
                for(TaskModel tasker : taskModelList) {
                    UUID id = tasker.getIdTask();
                    tasker.add(linkTo(methodOn(TaskController.class).getOneTask(id)).withSelfRel());
                }
        }
        return ResponseEntity.status(HttpStatus.OK).body(taskModelList);
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<Object> getOneTask(@PathVariable(value = "id") UUID id){
        Optional<TaskModel> task0 = taskRepository.findById(id);
        if(task0.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Task not Found");
        }
        task0.get().add(linkTo(methodOn(TaskController.class).getAllTasks()).withRel("User list"));
        return ResponseEntity.status(HttpStatus.OK).body(task0.get());
    }
}
