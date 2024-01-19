package com.taskmanagerapi.TaskManagerAPI.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator; // Importe esta classe para utilizar a anotação GenericGenerator
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonFormat;

@Table(name = "TB_TASK")
@Getter
@Setter
@Entity
public class TaskModel extends RepresentationModel<TaskModel> implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    private UUID idTask;

    private String taskName;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate begunDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate finishDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Priority priority;

    private String category;

    @ManyToOne
    private UserModel user;
}
