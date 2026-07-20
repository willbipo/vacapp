package com.vacapp.empleados.internal.infrastructure.persistence;

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

/**
 * Repositorio JPA para Empleado.
 */
@Repository
public interface EmpleadoJpaRepository extends CrudRepository<EmpleadoEntity, String> {
    @Query("SELECT * FROM empleados WHERE id = :id AND tenant_id = :tenantId")
    Optional<EmpleadoEntity> findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM empleados WHERE tenant_id = :tenantId ORDER BY fecha_registro DESC")
    List<EmpleadoEntity> findByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM empleados WHERE email = :email AND tenant_id = :tenantId")
    Optional<EmpleadoEntity> findByEmailAndTenantId(@Param("email") String email, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM empleados WHERE rancho_id = :ranchoId AND tenant_id = :tenantId ORDER BY fecha_registro DESC")
    List<EmpleadoEntity> findByRanchoIdAndTenantId(@Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);
}
