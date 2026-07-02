package com.vacapp.vacunas.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/** Repositorio Spring Data JDBC para {@code categorias_vacunas}. */
public interface CategoriaVacunaJpaRepository extends CrudRepository<CategoriaVacunaEntidad, UUID> {

    @Query("SELECT * FROM categorias_vacunas WHERE tenant_id = :tenantId ORDER BY nombre")
    List<CategoriaVacunaEntidad> findAllByTenantId(@Param("tenantId") String tenantId);
}
