package com.jhonyst.apidenuncias.controller.request;

import com.jhonyst.apidenuncias.model.EEstado;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoDenunciaDTO {

    private String observacion;

    private String estado;
}
