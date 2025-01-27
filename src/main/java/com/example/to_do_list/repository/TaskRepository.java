package com.example.to_do_list.repository;

import com.example.to_do_list.Status;
import com.example.to_do_list.classes.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface TaskRepository extends JpaRepository<Task, Long> {
    @Query("SELECT t FROM Task t WHERE LOWER(t.title) = LOWER(:title)")
    Optional<Task> findByTitleIgnoreCase(@Param("title") String title);


    @Query("SELECT s FROM Task s WHERE s.status = :statusI")
    List<Task> findByStatus(@Param("statusI")Status status);
    //Otra opcion sin usar springboot:
    //List<Task> findByStatus(Status status);
}
