package com.vacapp.cicloReproductivo.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

/** Repositorio Spring Data JDBC para {@code becerros}. */
public interface BecerroJdbcRepository extends CrudRepository<BecerroEntidad, UUID> {

    @Query("SELECT * FROM becerros WHERE madre_id = :madreId AND tenant_id = :tenantId ORDER BY fecha_nacimiento DESC")
    List<BecerroEntidad> findByMadreIdAndTenantId(@Param("madreId") UUID madreId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM becerros WHERE tenant_id = :tenantId ORDER BY fecha_nacimiento DESC")
    List<BecerroEntidad> findAllByTenantId(@Param("tenantId") String tenantId);
}
