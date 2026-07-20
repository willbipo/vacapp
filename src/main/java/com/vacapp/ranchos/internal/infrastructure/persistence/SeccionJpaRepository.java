package com.vacapp.ranchos.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JDBC para Seccion.
 */
@Repository
public interface SeccionJpaRepository extends CrudRepository<SeccionEntity, String> {
    @Query("SELECT * FROM secciones WHERE id = :id AND tenant_id = :tenantId")
    Optional<SeccionEntity> findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM secciones WHERE rancho_id = :ranchoId AND tenant_id = :tenantId ORDER BY nombre ASC")
    List<SeccionEntity> findByRanchoIdAndTenantId(@Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM secciones WHERE nombre = :nombre AND rancho_id = :ranchoId AND tenant_id = :tenantId")
    Optional<SeccionEntity> findByNombreAndRanchoIdAndTenantId(
        @Param("nombre") String nombre,
        @Param("ranchoId") String ranchoId,
        @Param("tenantId") String tenantId
    );
}
