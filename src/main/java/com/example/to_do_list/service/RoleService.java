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
public class RoleService {
    @Autowired
    RoleRepository roleRepository;
    UserRepository userRepository;

    public Role addRole(Role role){

        return roleRepository.save(role);
    }

    public Role findRoleById(Long id){
       return roleRepository.findById(id).
               orElseThrow(()-> new NoSuchElementException("Rol no encontrado"));
    }

    public void removeRoleById(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rol no encontrado"));
        roleRepository.delete(role);
    }

    public Optional<Role> changeRole(Long id, String newName){
        Optional<Role> changedRole= roleRepository.findById(id);
        if (changedRole.isPresent()){
            Role role = changedRole.get();
            role.setName(newName);
            roleRepository.save(role);
            return Optional.of(role);
        }
        return Optional.empty();

    }

    public List<Role> getAllRoles(){
        return  roleRepository.findAll();

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
