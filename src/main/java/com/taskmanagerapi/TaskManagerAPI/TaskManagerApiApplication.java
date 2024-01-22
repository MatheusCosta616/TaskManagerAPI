package com.taskmanagerapi.TaskManagerAPI;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "TaskManagerAPI", version = "1", description = "API DE CONTROLE DE TAREFAS"))
public class TaskManagerApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApiApplication.class, args);
    }
}
