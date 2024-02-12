package com.jhonyst.apidenuncias.repository;

import com.jhonyst.apidenuncias.model.ERole;
import com.jhonyst.apidenuncias.model.RoleEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {

    @Query("SELECT r FROM RoleEntity r WHERE r.nameRole = ?1")
    Optional<RoleEntity> getRoleByName(ERole name);
}
