package com.example.to_do_list.service;

import com.example.to_do_list.Status;
import com.example.to_do_list.classes.Task;
import com.example.to_do_list.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    TaskRepository taskRepository;

    public List<Task> findAllTask(){
        return taskRepository.findAll();
    }

    public Optional<Task> findTaskById(Long idTask){
        return taskRepository.findById(idTask);
    }

    public Optional<Task> findTaskByTitle(String title){
        return taskRepository.findByTitleIgnoreCase(title);
    }

    public List<Task> addTaskToList(List<Task> tasks) {
        if (tasks == null || tasks.isEmpty()) {
            throw new IllegalArgumentException("La lista de tareas no puede estar vacía.");
        }

        try {
            return taskRepository.saveAll(tasks);
        } catch (Exception e) {
            throw new RuntimeException("Error al guardar las tareas: " + e.getMessage(), e);
        }
    }


    public Optional<Task> removeTask(Long idTask) {
        Optional<Task> task = findTaskById(idTask);
        if (task.isPresent()) {
            taskRepository.deleteById(idTask); // Elimina la tarea
            return task; // Devuelve la tarea eliminada
        }
        return Optional.empty(); // Si no se encontró, devuelve un Optional vacío
    }


    public Optional<Task> changeTask(Long idTask, Status newStatus){
        Optional<Task> taskFounded= findTaskById(idTask);
        if (taskFounded.isPresent()){
            Task task= taskFounded.get();
            task.setStatus(newStatus);
            taskRepository.save(task);
            return  Optional.of(task);
        }else {
            System.out.println("No se encontró la tarea con ID: " + idTask);
            return Optional.empty();
        }
    }
    public List<Task> findTaskByStatus(Status status){
        return taskRepository.findByStatus(status);
    }
}
