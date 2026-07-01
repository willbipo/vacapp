package com.vacapp.ganado.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio Spring Data JDBC para la tabla {@code animales}. */
public interface AnimalJpaRepository extends CrudRepository<AnimalEntidad, UUID> {

    @Query("SELECT * FROM animales WHERE tenant_id = :tenantId")
    List<AnimalEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM animales WHERE id = :id AND tenant_id = :tenantId")
    Optional<AnimalEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("DELETE FROM animales WHERE id = :id AND tenant_id = :tenantId")
    void deleteByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);
}
