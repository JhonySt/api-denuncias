package com.jhonyst.apidenuncias.service.impl;

import com.jhonyst.apidenuncias.model.Fiscal;
import com.jhonyst.apidenuncias.repository.FiscalRepository;
import com.jhonyst.apidenuncias.service.IFiscalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FiscalServiceImpl implements IFiscalService {

    @Autowired
    FiscalRepository fiscalRepository;

    @Override
    public List<Fiscal> getAllFiscales() {
        return (List<Fiscal>) fiscalRepository.findAll();
    }

    @Override
    public Fiscal getFiscalById(Long id) {
        Optional<Fiscal> fiscalOptional = fiscalRepository.findById(id);
        return fiscalOptional.orElse(null);
    }

    @Override
    public Fiscal getFiscalByDocumento(String documentoIdentidad) {
        Optional<Fiscal> fiscalOptional = fiscalRepository.getByDocumento(documentoIdentidad);
        return fiscalOptional.orElse(null);
    }

    @Override
    public void saveFiscal(Fiscal fiscal) {
        fiscalRepository.save(fiscal);
    }

    @Override
    public void deleteFiscalById(Long id) {
        fiscalRepository.deleteById(id);
    }
}
