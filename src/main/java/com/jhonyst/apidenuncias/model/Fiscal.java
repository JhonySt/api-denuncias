package com.jhonyst.apidenuncias.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Fiscal extends Persona{

    @NotBlank
    String carneEmpleado;

    @NotBlank
    String estado;

    @OneToOne(targetEntity = UserEntity.class, cascade = CascadeType.ALL)
    private UserEntity userEntity;

    @OneToMany(mappedBy = "fiscal")
    private List<Denuncia> denuncias;
}
