package com.vacapp.insumos.internal.infrastructure.persistence;

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

/** Repositorio Spring Data JDBC para la tabla {@code insumos}. */
@Repository
public interface InsumoJpaRepository extends CrudRepository<InsumoEntidad, UUID> {

    @Query("SELECT * FROM insumos WHERE tenant_id = :tenantId")
    List<InsumoEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM insumos WHERE id = :id AND tenant_id = :tenantId")
    Optional<InsumoEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("DELETE FROM insumos WHERE id = :id AND tenant_id = :tenantId")
    void deleteByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM insumos WHERE rancho_id = :ranchoId AND tenant_id = :tenantId")
    List<InsumoEntidad> findByRanchoIdAndTenantId(@Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);
}
