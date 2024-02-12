package com.jhonyst.apidenuncias.service.impl;

import com.jhonyst.apidenuncias.model.Denunciante;
import com.jhonyst.apidenuncias.repository.DenuncianteRepository;
import com.jhonyst.apidenuncias.service.IDenuncianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DenuncianteServiceImpl implements IDenuncianteService {

    @Autowired
    private DenuncianteRepository denuncianteRepository;

    @Override
    public List<Denunciante> getAllDenunciantes() {
        return (List<Denunciante>) denuncianteRepository.findAll();
    }

    @Override
    public Denunciante getDenuncianteById(Long id) {
        Optional<Denunciante> denuncianteOptional = denuncianteRepository.findById(id);
        return denuncianteOptional.orElse(null);
    }

    @Override
    public Denunciante getDenuncianteByDocumento(String documento) {
        Optional<Denunciante> denuncianteOptional = denuncianteRepository.getByDocumento(documento);
        return denuncianteOptional.orElse(null);
    }

    @Override
    public void saveDenunciante(Denunciante denunciante) {
        denuncianteRepository.save(denunciante);
    }

    @Override
    public void deleteDenuncianteById(Long id) {
        denuncianteRepository.deleteById(id);
    }
}
