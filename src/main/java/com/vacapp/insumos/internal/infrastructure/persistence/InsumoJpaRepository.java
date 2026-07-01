package com.vacapp.insumos.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio Spring Data JDBC para la tabla {@code insumos}. */
public interface InsumoJpaRepository extends CrudRepository<InsumoEntidad, UUID> {

    @Query("SELECT * FROM insumos WHERE tenant_id = :tenantId")
    List<InsumoEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM insumos WHERE id = :id AND tenant_id = :tenantId")
    Optional<InsumoEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("DELETE FROM insumos WHERE id = :id AND tenant_id = :tenantId")
    void deleteByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);
}
