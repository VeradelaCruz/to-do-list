package com.example.to_do_list.service;

import com.example.to_do_list.classes.Role;
import com.example.to_do_list.classes.User;
import com.example.to_do_list.repository.RoleRepository;
import com.example.to_do_list.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    RoleRepository roleRepository;

    public List<User> findAllUsers(){
        return  userRepository.findAll();
    }

    public Optional<User> findUserById(Long idUser){
        return userRepository.findById(idUser);
    }

    public Optional<User> findUserByName(String username){
        return userRepository.findByUserIgnoreCase(username);
    }

    public User addUser(User user){
        return userRepository.save(user);

    }

    public void removeUser(Long idUser){
        User user = userRepository.findById(idUser).
                orElseThrow(()-> new NoSuchElementException("Usuario no encontrado."));
        userRepository.deleteById(idUser);
    }

    public User  giveRole(Long id, Long idUser){
        Role role = roleRepository.findById(id)
                .orElseThrow(()-> new NoSuchElementException("No se ha encontrado el rol."));

        User user = userRepository.findById(idUser)
                .orElseThrow(()-> new NoSuchElementException("No se ha encontrado el usuario."));

        user.getRoles().add(role);
        return userRepository.save(user);
    }
}
