package com.example.to_do_list.controller;

import com.example.to_do_list.Status;
import com.example.to_do_list.classes.Task;
import com.example.to_do_list.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/task")
public class TaskController {

    @Autowired
    TaskService taskService;

    //Mostrar todas las tareas:
    @GetMapping("/allTasks")
    public List<Task> showAllTask(){
        return taskService.findAllTask();
    }

    //Mostrar tareas por id:
    @GetMapping("/allTasks/{idTask}")
    public ResponseEntity<Task> findTaskById(@PathVariable Long idTask){
        Optional<Task> task = taskService.findTaskById(idTask);
        if (task.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(task.get());  // Aquí obtienes el Task del Optional
        }
    }

    //Mostrar tareas por nombre:
    @GetMapping("/allTasks/title/{title}")
    public ResponseEntity<Task> findByTitle(@PathVariable String title){
        Optional<Task> task = taskService.findTaskByTitle(title);
        if (task.isPresent()){
            return ResponseEntity.ok(task.get());
        }else {
            return ResponseEntity.notFound().build();
        }

    }

    //Agregar una tarea:
    @PostMapping("/addTask")
    public ResponseEntity<?> addTask(@RequestBody @Valid List<Task> tasks) {
        try {
            List<Task> savedTasks = taskService.addTaskToList(tasks);
            return ResponseEntity.ok(savedTasks);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al guardar las tareas: " + e.getMessage());
        }
    }



    //Eliminar una tarea:
    @DeleteMapping("/deleteTask/{idTask}")
    public ResponseEntity<?> deleteTask(@PathVariable Long idTask){
        Optional<Task> foundedTask = taskService.removeTask(idTask);
        if (foundedTask.isEmpty()){
            return ResponseEntity.ok("La tarea con id "+ idTask + "ha sido eliminada.");
        } else{
            return ResponseEntity.notFound().build();
        }
    }

    //Marcar una tarea como completa:
    @PutMapping("/updateTask/{idTask}")
    public ResponseEntity<String> updateTask(@PathVariable Long idTask, @RequestBody Status newStatus){
        Optional<Task> task = taskService.changeTask(idTask, newStatus);
        if (task.isPresent()) {
            return ResponseEntity.ok("La tarea con ID " + idTask + " ha sido actualizada exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No se encontró la tarea con ID " + idTask + ".");
        }
    }

    //Buscar una tarea por status:
    @GetMapping("/allTask/{status}")
    public ResponseEntity<List<Task>> findByStatus(@PathVariable Status status){
        List<Task> foundedTask = taskService.findTaskByStatus(status);
        if (foundedTask.isEmpty()){
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(foundedTask);
        }

    }

}
