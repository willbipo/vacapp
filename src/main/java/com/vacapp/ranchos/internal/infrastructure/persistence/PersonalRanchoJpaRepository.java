package com.vacapp.ranchos.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JDBC para PersonalRancho.
 */
@Repository
public interface PersonalRanchoJpaRepository extends CrudRepository<PersonalRanchoEntity, String> {
    @Query("SELECT * FROM empleados_ranchos WHERE id = :id AND tenant_id = :tenantId")
    Optional<PersonalRanchoEntity> findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM empleados_ranchos WHERE rancho_id = :ranchoId AND tenant_id = :tenantId ORDER BY fecha_asignacion DESC")
    List<PersonalRanchoEntity> findByRanchoIdAndTenantId(@Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM empleados_ranchos WHERE empleado_id = :empleadoId AND tenant_id = :tenantId ORDER BY fecha_asignacion DESC")
    List<PersonalRanchoEntity> findByEmpleadoIdAndTenantId(@Param("empleadoId") String empleadoId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM empleados_ranchos WHERE empleado_id = :empleadoId AND rancho_id = :ranchoId AND activo = true AND tenant_id = :tenantId")
    Optional<PersonalRanchoEntity> findActivaByEmpleadoAndRancho(@Param("empleadoId") String empleadoId, @Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);
}
