package com.vacapp.ganado.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

/** Repositorio Spring Data JDBC para la tabla {@code animales}. */
@Repository
public interface AnimalJpaRepository extends CrudRepository<AnimalEntidad, UUID> {

    @Query("SELECT * FROM animales WHERE tenant_id = :tenantId")
    List<AnimalEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM animales WHERE id = :id AND tenant_id = :tenantId")
    Optional<AnimalEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("DELETE FROM animales WHERE id = :id AND tenant_id = :tenantId")
    void deleteByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM animales WHERE rancho_id = :ranchoId AND tenant_id = :tenantId")
    List<AnimalEntidad> findByRanchoIdAndTenantId(@Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);
}
