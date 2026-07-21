package com.vacapp.ranchos.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JDBC para asignaciones de ranchos.
 */
@Repository
public interface RanchoAsignacionJpaRepository extends CrudRepository<RanchoAsignacionEntity, String> {
    @Query("SELECT * FROM rancho_asignaciones WHERE usuario_id = :usuarioId AND tenant_id = :tenantId")
    List<RanchoAsignacionEntity> findByUsuarioIdAndTenantId(@Param("usuarioId") String usuarioId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM rancho_asignaciones WHERE rancho_id = :ranchoId AND tenant_id = :tenantId")
    List<RanchoAsignacionEntity> findByRanchoIdAndTenantId(@Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM rancho_asignaciones WHERE usuario_id = :usuarioId AND rancho_id = :ranchoId AND tenant_id = :tenantId")
    Optional<RanchoAsignacionEntity> findByUsuarioIdAndRanchoIdAndTenantId(
        @Param("usuarioId") String usuarioId,
        @Param("ranchoId") String ranchoId,
        @Param("tenantId") String tenantId
    );
}
