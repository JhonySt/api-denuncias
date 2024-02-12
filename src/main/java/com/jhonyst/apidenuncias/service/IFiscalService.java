package com.jhonyst.apidenuncias.service;

import com.jhonyst.apidenuncias.model.Fiscal;

import java.util.List;

public interface IFiscalService {

    List<Fiscal> getAllFiscales();

    Fiscal getFiscalById(Long id);

    Fiscal getFiscalByDocumento(String documentoIdentidad);

    void saveFiscal(Fiscal fiscal);

    void deleteFiscalById(Long id);


}
