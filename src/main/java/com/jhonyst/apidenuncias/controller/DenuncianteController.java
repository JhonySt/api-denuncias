package com.jhonyst.apidenuncias.controller;

import com.jhonyst.apidenuncias.controller.request.CreateDenuncianteDTO;
import com.jhonyst.apidenuncias.model.Denunciante;
import com.jhonyst.apidenuncias.model.ERole;
import com.jhonyst.apidenuncias.model.RoleEntity;
import com.jhonyst.apidenuncias.model.UserEntity;
import com.jhonyst.apidenuncias.repository.RoleRepository;
import com.jhonyst.apidenuncias.repository.UserRepository;
import com.jhonyst.apidenuncias.service.impl.DenuncianteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/denunciantes")
public class DenuncianteController {

    @Autowired
    private DenuncianteServiceImpl denuncianteService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'FISCAL')")
    public ResponseEntity<List<?>> getAllDenunciantes(){

        List<Denunciante> listaDenunciantes = denuncianteService.getAllDenunciantes();

        if(listaDenunciantes.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(listaDenunciantes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    //@PreAuthorize("hasAnyRole('ADMIN', 'FISCAL')")
    public ResponseEntity<?> getDenuncianteById(@PathVariable Long id){

        Denunciante denunciante = denuncianteService.getDenuncianteById(id);
        if (denunciante == null){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(denunciante, HttpStatus.OK);
    }

    @PostMapping("/create")
    //@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> saveDenunciante(@RequestBody CreateDenuncianteDTO denuncianteDTO){

        if(denuncianteService.getDenuncianteByDocumento(denuncianteDTO.getDocumentoIdentidad()) != null){
            return ResponseEntity.badRequest().body("El documento de identidad ya se encuentra registrado");
        }

        if(userRepository.getByUsername(denuncianteDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("El username ingresado no esta disponible, por favor ingrese otro.");
        }

        Set<RoleEntity> roles = new HashSet<>();
        Optional<RoleEntity> optionalRole = roleRepository.getRoleByName(ERole.USER);
        RoleEntity roleEntity = optionalRole.get();
        roles.add(roleEntity);

        UserEntity userEntity = UserEntity.builder()
                .username(denuncianteDTO.getUsername())
                .password(passwordEncoder.encode(denuncianteDTO.getPassword()))
                .roles(roles)
                .build();

        Denunciante denunciante = new Denunciante();
        denunciante.setNombre(denuncianteDTO.getNombre());
        denunciante.setApellido(denuncianteDTO.getApellido());
        denunciante.setDocumentoIdentidad(denuncianteDTO.getDocumentoIdentidad());
        denunciante.setEmail(denuncianteDTO.getEmail());
        denunciante.setDireccionRecidencia(denuncianteDTO.getDireccionRecidencia());
        denunciante.setCelular(denuncianteDTO.getCelular());
        denunciante.setUserEntity(userEntity);

        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaJava = formato.parse(denuncianteDTO.getFechaNacimiento());
            denunciante.setFechaNacimiento(fechaJava);
        } catch (ParseException ex) {
            Logger.getLogger(DenuncianteController.class.getName()).log(Level.SEVERE, null, ex);
        }

        denuncianteService.saveDenunciante(denunciante);

        return new ResponseEntity<>(denunciante, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> updateDenunciante(@PathVariable Long id, Denunciante denunciante){

        Denunciante denuncianteBD = denuncianteService.getDenuncianteById(id);

        if (denuncianteBD == null){
            return ResponseEntity.badRequest().build();
        }

        denuncianteBD.setNombre(denunciante.getNombre());
        denuncianteBD.setApellido(denunciante.getApellido());
        denuncianteBD.setDocumentoIdentidad(denunciante.getDocumentoIdentidad());
        denuncianteBD.setEmail(denunciante.getEmail());
        denuncianteBD.setDireccionRecidencia(denunciante.getDireccionRecidencia());
        denuncianteBD.setCelular(denunciante.getCelular());
        denuncianteBD.setFechaNacimiento(denunciante.getFechaNacimiento());
        denuncianteBD.setUserEntity(denunciante.getUserEntity());

        denuncianteService.saveDenunciante(denuncianteBD);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteDenuncianteById(@PathVariable Long id){

        if(id == null) return ResponseEntity.badRequest().body("El id esta vacio");

        if(denuncianteService.getDenuncianteById(id) != null){
            denuncianteService.deleteDenuncianteById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body("El denunciante con la id: " + id + " no existe.");
    }
}
