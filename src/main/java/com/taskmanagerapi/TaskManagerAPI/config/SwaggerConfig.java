package com.taskmanagerapi.TaskManagerAPI.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("TaskManagerAPI")
                        .description("Gerenciador de tarefas para empresas")
                        .contact(new Contact().name("Matheus José de Lima Costa").email("matheus.costa2616@gmail.com")
                                ).version("1.0.0"));
    }
}
