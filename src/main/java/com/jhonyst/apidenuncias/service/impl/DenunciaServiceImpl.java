package com.jhonyst.apidenuncias.service.impl;

import com.jhonyst.apidenuncias.model.Denuncia;
import com.jhonyst.apidenuncias.model.Denunciante;
import com.jhonyst.apidenuncias.repository.DenunciaRepository;
import com.jhonyst.apidenuncias.service.IDenunciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DenunciaServiceImpl implements IDenunciaService {

    @Autowired
    private DenunciaRepository denunciaRepository;

    @Override
    public List<Denuncia> getAllDenuncias() {
        return (List<Denuncia>) denunciaRepository.findAll();
    }

    @Override
    public Denuncia getDenunciaById(Long id) {
        Optional<Denuncia> denunciaOptional = denunciaRepository.findById(id);
        return denunciaOptional.orElse(null);
    }

    @Override
    public void saveDenuncia(Denuncia denuncia) {
        denunciaRepository.save(denuncia);
    }

    @Override
    public void deleteDenunciaById(Long id) {
        denunciaRepository.deleteById(id);
    }
}
