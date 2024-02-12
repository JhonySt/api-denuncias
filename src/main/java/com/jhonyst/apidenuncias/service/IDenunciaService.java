package com.jhonyst.apidenuncias.service;

import com.jhonyst.apidenuncias.model.Denuncia;

import java.util.List;

public interface IDenunciaService {

    List<Denuncia> getAllDenuncias();

    Denuncia getDenunciaById(Long id);

    void saveDenuncia(Denuncia denuncia);

    void deleteDenunciaById(Long id);
}
