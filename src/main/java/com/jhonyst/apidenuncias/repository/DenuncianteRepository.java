package com.jhonyst.apidenuncias.repository;

import com.jhonyst.apidenuncias.model.Denunciante;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DenuncianteRepository extends CrudRepository<Denunciante, Long> {

    @Query("SELECT d FROM Denunciante d WHERE d.documentoIdentidad = ?1")
    Optional<Denunciante> getByDocumento(String documento);
}
