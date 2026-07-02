package com.vacapp.ganado.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/** Repositorio Spring Data JDBC para {@code categorias_ganado}. */
public interface CategoriaGanadoJpaRepository extends CrudRepository<CategoriaGanadoEntidad, UUID> {

    @Query("SELECT * FROM categorias_ganado WHERE tenant_id = :tenantId ORDER BY nombre")
    List<CategoriaGanadoEntidad> findAllByTenantId(@Param("tenantId") String tenantId);
}
