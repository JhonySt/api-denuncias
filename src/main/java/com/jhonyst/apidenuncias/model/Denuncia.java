package com.jhonyst.apidenuncias.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Denuncia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date fechaIncidente;

    @NotBlank
    private String lugar;

    @NotBlank
    private String descripcion;

    private String observacion;

    private EEstado estado;

    @JsonIgnore
    @ManyToOne(targetEntity = Denunciante.class, fetch = FetchType.LAZY)
    private Denunciante denunciante;

    @JsonIgnore
    @ManyToOne(targetEntity = Fiscal.class, fetch = FetchType.LAZY)
    private Fiscal fiscal;
}
