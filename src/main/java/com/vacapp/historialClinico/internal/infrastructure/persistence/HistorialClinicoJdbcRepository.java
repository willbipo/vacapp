package com.vacapp.historialClinico.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/** Repositorio Spring Data JDBC para {@link HistorialClinicoEntidad}. */
public interface HistorialClinicoJdbcRepository extends CrudRepository<HistorialClinicoEntidad, UUID> {

    @Query("SELECT * FROM historial_clinico WHERE animal_id = :animalId AND tenant_id = :tenantId ORDER BY fecha_aplicacion DESC")
    List<HistorialClinicoEntidad> findByAnimalIdAndTenantId(
            @Param("animalId") UUID animalId,
            @Param("tenantId") String tenantId
    );

    @Query("SELECT * FROM historial_clinico WHERE tenant_id = :tenantId ORDER BY fecha_aplicacion DESC")
    List<HistorialClinicoEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM historial_clinico WHERE tenant_id = :tenantId AND proxima_dosis IS NOT NULL ORDER BY proxima_dosis ASC")
    List<HistorialClinicoEntidad> findProximasDosis(@Param("tenantId") String tenantId);
}
