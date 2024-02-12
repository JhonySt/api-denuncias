package com.jhonyst.apidenuncias.service;

import com.jhonyst.apidenuncias.model.Denunciante;
import com.jhonyst.apidenuncias.repository.DenuncianteRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface IDenuncianteService {

    List<Denunciante> getAllDenunciantes();

    Denunciante getDenuncianteById(Long id);

    Denunciante getDenuncianteByDocumento(String documento);

    void saveDenunciante(Denunciante denunciante);

    void deleteDenuncianteById(Long id);
}
