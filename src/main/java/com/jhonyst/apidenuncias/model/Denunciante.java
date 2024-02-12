package com.jhonyst.apidenuncias.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Denunciante extends Persona{

    @NotBlank
    private String direccionRecidencia;

    @NotBlank
    private String celular;

    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;

    @OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.ALL)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "denunciante")
    private List<Denuncia> denuncias;
}
