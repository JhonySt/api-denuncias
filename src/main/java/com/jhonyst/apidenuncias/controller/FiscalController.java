package com.jhonyst.apidenuncias.controller;

import com.jhonyst.apidenuncias.controller.request.CreateFiscalDTO;
import com.jhonyst.apidenuncias.model.*;
import com.jhonyst.apidenuncias.repository.RoleRepository;
import com.jhonyst.apidenuncias.repository.UserRepository;
import com.jhonyst.apidenuncias.service.impl.FiscalServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/fiscales")
public class FiscalController {

    @Autowired
    private FiscalServiceImpl fiscalService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<?>> getAllFiscales(){

        List<Fiscal> listaFiscales = fiscalService.getAllFiscales();

        if(listaFiscales.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(listaFiscales, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FISCAL')")
    public ResponseEntity<?> getFiscalById(@PathVariable Long id){

        Fiscal fiscal = fiscalService.getFiscalById(id);
        if (fiscal == null){
            return ResponseEntity.notFound().build();
        }

        return new ResponseEntity<>(fiscal, HttpStatus.OK);
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveFiscal(@RequestBody CreateFiscalDTO fiscalDTO){

        if(fiscalService.getFiscalByDocumento(fiscalDTO.getDocumentoIdentidad()) != null){
            return ResponseEntity.badRequest().body("El documento de identidad ya se encuentra registrado");
        }

        if(userRepository.getByUsername(fiscalDTO.getUsername()).isPresent()){
            return ResponseEntity.badRequest().body("El username ingresado no esta disponible, por favor ingrese otro.");
        }

        Set<RoleEntity> roles = new HashSet<>();
        Optional<RoleEntity> optionalRole = roleRepository.getRoleByName(ERole.FISCAL);
        RoleEntity roleEntity = optionalRole.get();
        roles.add(roleEntity);

        UserEntity userEntity = UserEntity.builder()
                .username(fiscalDTO.getUsername())
                .password(passwordEncoder.encode(fiscalDTO.getPassword()))
                .roles(roles)
                .build();

        Fiscal fiscal = new Fiscal();
        fiscal.setNombre(fiscalDTO.getNombre());
        fiscal.setApellido(fiscalDTO.getApellido());
        fiscal.setDocumentoIdentidad(fiscalDTO.getDocumentoIdentidad());
        fiscal.setEmail(fiscalDTO.getEmail());
        fiscal.setCarneEmpleado(fiscalDTO.getCarneEmpleado());
        fiscal.setEstado(fiscalDTO.getEstado());
        fiscal.setUserEntity(userEntity);

        fiscalService.saveFiscal(fiscal);

        return new ResponseEntity<>(fiscal, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'FISCAL')")
    public ResponseEntity<?> updateFiscal(@PathVariable Long id, Fiscal fiscal){

        Fiscal fiscalBD = fiscalService.getFiscalById(id);

        if (fiscalBD == null){
            return ResponseEntity.badRequest().build();
        }

        fiscalBD.setNombre(fiscal.getNombre());
        fiscalBD.setApellido(fiscal.getApellido());
        fiscalBD.setDocumentoIdentidad(fiscal.getDocumentoIdentidad());
        fiscalBD.setEmail(fiscal.getEmail());
        fiscalBD.setCarneEmpleado(fiscal.getCarneEmpleado());
        fiscalBD.setEstado(fiscal.getEstado());

        fiscalService.saveFiscal(fiscalBD);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteFiscalById(@PathVariable Long id){

        if(id == null) return ResponseEntity.badRequest().body("El id esta vacio");

        if(fiscalService.getFiscalById(id) != null){
            fiscalService.deleteFiscalById(id);
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.badRequest().body("El fiscal con la id: " + id + " no existe.");
    }
}
