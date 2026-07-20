package com.vacapp.ranchos.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JDBC para Potrero.
 */
@Repository
public interface PotreroJpaRepository extends CrudRepository<PotreroEntity, String> {
    @Query("SELECT * FROM potreros WHERE id = :id AND tenant_id = :tenantId")
    Optional<PotreroEntity> findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM potreros WHERE rancho_id = :ranchoId AND tenant_id = :tenantId ORDER BY nombre ASC")
    List<PotreroEntity> findByRanchoIdAndTenantId(@Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM potreros WHERE seccion_id = :seccionId AND tenant_id = :tenantId ORDER BY nombre ASC")
    List<PotreroEntity> findBySeccionIdAndTenantId(@Param("seccionId") String seccionId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM potreros WHERE nombre = :nombre AND rancho_id = :ranchoId AND tenant_id = :tenantId")
    Optional<PotreroEntity> findByNombreAndRanchoIdAndTenantId(
        @Param("nombre") String nombre,
        @Param("ranchoId") String ranchoId,
        @Param("tenantId") String tenantId
    );
}
