package com.jhonyst.apidenuncias.controller;

import com.jhonyst.apidenuncias.controller.request.CreateUserDTO;
import com.jhonyst.apidenuncias.model.ERole;
import com.jhonyst.apidenuncias.model.RoleEntity;
import com.jhonyst.apidenuncias.model.UserEntity;
import com.jhonyst.apidenuncias.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<?>> getAllUsers(){
        List<UserEntity> listaUsuarios = (List<UserEntity>) userRepository.findAll();

        return ResponseEntity.ok(listaUsuarios);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> crearUsuario(@Valid @RequestBody CreateUserDTO createUserDTO){

        Set<RoleEntity> roles = createUserDTO.getRoles().stream()
                .map(roleEntity -> RoleEntity.builder()
                        .nameRole(ERole.valueOf(roleEntity))
                        .build())
                .collect(Collectors.toSet());

        UserEntity userEntity = UserEntity.builder()
                .username(createUserDTO.getUsername())
                .password(passwordEncoder.encode(createUserDTO.getPassword()))
                .roles(roles)
                .build();

        userRepository.save(userEntity);
        return ResponseEntity.ok(userEntity);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public String borrarUsuario(@PathVariable Long id){

        userRepository.deleteById(id);
        return "Se ha borrado el usuario con id: " + id;
    }

}
