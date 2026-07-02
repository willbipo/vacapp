package com.vacapp.insumos.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/** Repositorio Spring Data JDBC para {@code categorias_insumos}. */
public interface CategoriaInsumoJpaRepository extends CrudRepository<CategoriaInsumoEntidad, UUID> {

    @Query("SELECT * FROM categorias_insumos WHERE tenant_id = :tenantId ORDER BY nombre")
    List<CategoriaInsumoEntidad> findAllByTenantId(@Param("tenantId") String tenantId);
}
