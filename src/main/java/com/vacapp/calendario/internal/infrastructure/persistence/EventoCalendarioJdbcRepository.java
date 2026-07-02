package com.vacapp.calendario.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/** Repositorio Spring Data JDBC para {@code eventos_calendario}. */
public interface EventoCalendarioJdbcRepository extends CrudRepository<EventoCalendarioEntidad, UUID> {

    @Query("SELECT * FROM eventos_calendario WHERE tenant_id = :tenantId ORDER BY fecha")
    List<EventoCalendarioEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM eventos_calendario WHERE tenant_id = :tenantId AND fecha BETWEEN :inicio AND :fin ORDER BY fecha")
    List<EventoCalendarioEntidad> findByFechaBetweenAndTenantId(
            @Param("inicio") LocalDate inicio,
            @Param("fin") LocalDate fin,
            @Param("tenantId") String tenantId);

    @Modifying
    @Query("DELETE FROM eventos_calendario WHERE id = :id AND tenant_id = :tenantId")
    void deleteByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);
}
