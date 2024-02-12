package com.jhonyst.apidenuncias.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-api")
public class TestRolesController {

    @GetMapping("/accessAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public String accessAdmin(){
        return "Hola, has accedido como Admin";
    }

    @GetMapping("/accessUser")
    @PreAuthorize("hasRole('USER')")
    public String accessUser(){
        return "Hola, has accedido como User";
    }

    @GetMapping("/accessFiscal")
    @PreAuthorize("hasRole('FISCAL')")
    public String accessFiscal(){
        return "Hola, has accedido como Fiscal";
    }
}
