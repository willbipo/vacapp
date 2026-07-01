package com.vacapp.vacunas.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio Spring Data JDBC para la tabla {@code vacunas}. */
public interface VacunaJpaRepository extends CrudRepository<VacunaEntidad, UUID> {

    @Query("SELECT * FROM vacunas WHERE tenant_id = :tenantId")
    List<VacunaEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM vacunas WHERE id = :id AND tenant_id = :tenantId")
    Optional<VacunaEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("DELETE FROM vacunas WHERE id = :id AND tenant_id = :tenantId")
    void deleteByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);
}
