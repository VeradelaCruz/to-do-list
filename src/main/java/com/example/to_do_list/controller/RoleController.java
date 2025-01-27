package com.example.to_do_list.controller;

import com.example.to_do_list.classes.Role;
import com.example.to_do_list.classes.User;
import com.example.to_do_list.service.RoleService;
import com.example.to_do_list.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    UserService userService;

    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody Role role){
        try{
            Role addedRole = roleService.addRole(role);
            return ResponseEntity.status(HttpStatus.CREATED).body("Rol agregado.");
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Ha ocurrido un error" + e.getMessage());
        }
    }

    @GetMapping("/showRole/{id}")
    public ResponseEntity<?> showRoleById(@PathVariable Long id){
        try {
            Role role = roleService.findRoleById(id);
            return ResponseEntity.ok(role);  // 200 OK
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);  // 404 Not Found
        }
    }


    @DeleteMapping("/deleteRole")
    public  ResponseEntity<?> deleteRole(@PathVariable Long id){
        try{
            roleService.removeRoleById(id);
            return ResponseEntity.ok("Rol eliminado.");
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }

    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody String newName){
        Optional<Role> role = roleService.changeRole(id, newName);
        if (role.isPresent()){
            return ResponseEntity.ok("El rol ha sido cambiado exitosamente.");
        }else {
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el rol.");
        }

    }

    @GetMapping("/showAllRoles")
    public List<Role> showAllRoles(){
        List<Role> roles= roleService.getAllRoles();
        return roles;
    }

    @PutMapping("/asigneRole")
    public ResponseEntity<?> asigneRole (@PathVariable Long id, @RequestBody Long idUser){
        try {
            User updatedUser = userService.giveRole(id, idUser);
                    return ResponseEntity.ok("Rol asignado.");
        } catch (NoSuchElementException e){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }








}
