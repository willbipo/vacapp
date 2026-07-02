package com.vacapp.ventas.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio Spring Data JDBC para la tabla {@code ventas_ganado}. */
public interface VentaGanadoJdbcRepository extends CrudRepository<VentaGanadoEntidad, UUID> {

    @Query("SELECT * FROM ventas_ganado WHERE tenant_id = :tenantId ORDER BY fecha_venta DESC")
    List<VentaGanadoEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM ventas_ganado WHERE id = :id AND tenant_id = :tenantId")
    Optional<VentaGanadoEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);
}
