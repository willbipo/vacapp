package com.vacapp.ranchos.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JDBC para Rancho.
 */
@Repository
public interface RanchoJpaRepository extends CrudRepository<RanchoEntity, String> {
    @Query("SELECT * FROM ranchos WHERE id = :id AND tenant_id = :tenantId")
    Optional<RanchoEntity> findByIdAndTenantId(@Param("id") String id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM ranchos WHERE tenant_id = :tenantId ORDER BY fecha_registro DESC")
    List<RanchoEntity> findByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM ranchos WHERE user_id = :userId AND tenant_id = :tenantId")
    List<RanchoEntity> findByUserIdAndTenantId(@Param("userId") String userId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM ranchos WHERE nombre = :nombre AND tenant_id = :tenantId")
    Optional<RanchoEntity> findByNombreAndTenantId(@Param("nombre") String nombre, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM ranchos WHERE tenant_id = :tenantId")
    List<RanchoEntity> findAllByTenantId(@Param("tenantId") String tenantId);
}
