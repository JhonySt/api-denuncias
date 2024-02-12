package com.jhonyst.apidenuncias.controller.request;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateDenunciaDTO {

    @NotBlank
    private String fechaIncidente;

    @NotBlank
    private String lugar;

    @NotBlank
    private String descripcion;

    private String documentoDenunciante;
}
