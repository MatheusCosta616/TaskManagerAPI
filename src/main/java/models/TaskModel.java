package models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;
import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;


@Getter
@Setter
@Entity
public class TaskModel extends RepresentationModel<TaskModel> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
