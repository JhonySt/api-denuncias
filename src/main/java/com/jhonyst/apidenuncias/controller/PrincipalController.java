package com.jhonyst.apidenuncias.controller;

import com.jhonyst.apidenuncias.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrincipalController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/index")
    public String hello(){
        return "Bienvenido a la api de denuncias";
    }

    @GetMapping("/helloSecured")
    public String helloSecured(){
        return "Hola esta ruta si esta protegida";
    }


}
