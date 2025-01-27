package com.example.to_do_list.repository;

import com.example.to_do_list.classes.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT n FROM User n WHERE LOWER(n.username)= LOWER(:username)")
    Optional<User> findByUserIgnoreCase(@Param("username") String username);


}
