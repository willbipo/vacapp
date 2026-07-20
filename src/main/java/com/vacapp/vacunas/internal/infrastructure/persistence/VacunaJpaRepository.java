package com.vacapp.vacunas.internal.infrastructure.persistence;

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
import java.util.UUID;
import org.springframework.stereotype.Repository;

/** Repositorio Spring Data JDBC para la tabla {@code vacunas}. */
@Repository
public interface VacunaJpaRepository extends CrudRepository<VacunaEntidad, UUID> {

    @Query("SELECT * FROM vacunas WHERE tenant_id = :tenantId")
    List<VacunaEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM vacunas WHERE id = :id AND tenant_id = :tenantId")
    Optional<VacunaEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("DELETE FROM vacunas WHERE id = :id AND tenant_id = :tenantId")
    void deleteByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM vacunas WHERE rancho_id = :ranchoId AND tenant_id = :tenantId")
    List<VacunaEntidad> findByRanchoIdAndTenantId(@Param("ranchoId") String ranchoId, @Param("tenantId") String tenantId);
}
