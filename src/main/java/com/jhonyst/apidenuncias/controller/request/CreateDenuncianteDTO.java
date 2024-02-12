package com.jhonyst.apidenuncias.controller.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDenuncianteDTO {

    @NotBlank
    private String nombre;

    @NotBlank
    private String apellido;

    @NotBlank
    private String documentoIdentidad;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String direccionRecidencia;

    @NotBlank
    private String celular;

    @NotBlank
    private String fechaNacimiento;

    @NotBlank
    @Size(max = 30)
    private String username;

    @NotBlank
    private String password;
}
