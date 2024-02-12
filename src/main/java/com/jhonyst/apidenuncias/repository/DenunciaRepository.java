package com.jhonyst.apidenuncias.repository;

import com.jhonyst.apidenuncias.model.Denuncia;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DenunciaRepository extends CrudRepository<Denuncia, Long> {
}
