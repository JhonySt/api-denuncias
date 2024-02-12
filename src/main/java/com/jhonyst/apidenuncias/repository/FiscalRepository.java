package com.jhonyst.apidenuncias.repository;

import com.jhonyst.apidenuncias.model.Fiscal;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FiscalRepository extends CrudRepository<Fiscal, Long> {

    @Query("SELECT f FROM Fiscal f WHERE f.documentoIdentidad = ?1")
    Optional<Fiscal> getByDocumento(String documentoIdentidad);
}
