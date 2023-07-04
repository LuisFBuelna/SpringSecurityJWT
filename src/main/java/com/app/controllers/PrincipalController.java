package com.app.controllers;

import com.app.dtos.UserDTO;
import com.app.entities.ERole;
import com.app.entities.RoleEntity;
import com.app.entities.UserEntity;
import com.app.repositories.UserRepository;
import jakarta.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author luis.buelna
 */

@RestController
public class PrincipalController {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRepository userRepository;
    
    @GetMapping("/hello")
    public String hello(){
        return "Hello World Not Secured";
    }
    
    @GetMapping("/helloSecured")
    public String helloSecured(){
        return "Hello World Secured";
    }
    
    @PostMapping("/createUser")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDTO userDTO){
        
        Set<RoleEntity> roles = userDTO.getRoles().stream()
                .map(role -> RoleEntity.builder()
                .name(ERole.valueOf(role))
                .build())
                .collect(Collectors.toSet());
        
        UserEntity userEntity = UserEntity.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .email(userDTO.getEmail())
                .roles(roles)
                .build();
        
        userRepository.save(userEntity);
        
        return ResponseEntity.ok(userEntity);
    }
    
    @DeleteMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable Long id){
        userRepository.deleteById(id);
        
        return "Se ha borrado el user con id: " + id;
    }
}
