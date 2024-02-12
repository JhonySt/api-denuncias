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
public class UpdateDenunciaDTO {

    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fechaIncidente;

    @NotBlank
    private String lugar;

    @NotBlank
    private String descripcion;

    private String estado;

    private String documentoDenunciante;

    private String documentoFiscal;
}
