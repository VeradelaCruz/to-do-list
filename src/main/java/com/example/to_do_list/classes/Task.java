package com.example.to_do_list.classes;

import com.example.to_do_list.Priority;
import com.example.to_do_list.Status;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long idTask;


    @NotNull
    @Size(max = 30)
    @NotBlank(message = "El titulo no puede estar vacío")
    private String title;

    @NotNull
    @NotBlank(message = "La descripción no puede estar vacía")
    @Size(max = 200)
    private String description;

    @NotNull
    @Future
    @Column(name = "due_date")
    private LocalDate dueDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Priority priority;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @JsonManagedReference
    @ManyToMany
    @JoinTable(
            name = "task_user", // Nombre de la tabla intermedia
            joinColumns = @JoinColumn(name = "id_task"), // Columna de clave foránea para Task
            inverseJoinColumns = @JoinColumn(name = "id_user") // Columna de clave foránea para User
    )
    private List<User> users;
}
