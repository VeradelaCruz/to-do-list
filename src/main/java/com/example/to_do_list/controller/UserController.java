package com.example.to_do_list.controller;

import com.example.to_do_list.classes.User;
import com.example.to_do_list.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/allUsers")
    public List<User> showAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/showUser/{idUser}")
    public ResponseEntity<User> showUserById(@PathVariable Long idUser) {
        Optional<User> foundedUser = userService.findUserById(idUser);
        if (foundedUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(foundedUser.get());
        }
    }

    @GetMapping("/showUser/{username}")
    public ResponseEntity<?> showUserByName(@PathVariable String username) {
        Optional<User> foundedUser = userService.findUserByName(username);
        if (foundedUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        } else {
            return ResponseEntity.ok(foundedUser.get());
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user) {
        try {
            User addedUser = userService.addUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al agregar usuario: " + e.getMessage());
        }
    }

    @DeleteMapping("/deleteUsuer/{idUser}")
    public ResponseEntity<?> deleteUser(@PathVariable Long idUser) {
        try{
            userService.removeUser(idUser);
            return ResponseEntity.ok("Usuario eliminado.");
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}