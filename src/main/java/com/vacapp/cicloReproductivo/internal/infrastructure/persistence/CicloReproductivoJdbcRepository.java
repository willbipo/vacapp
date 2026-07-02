package com.vacapp.cicloReproductivo.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio Spring Data JDBC para {@code ciclos_reproductivos}. */
public interface CicloReproductivoJdbcRepository extends CrudRepository<CicloReproductivoEntidad, UUID> {

    @Query("SELECT * FROM ciclos_reproductivos WHERE id = :id AND tenant_id = :tenantId")
    Optional<CicloReproductivoEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM ciclos_reproductivos WHERE tenant_id = :tenantId ORDER BY fecha_inicio DESC")
    List<CicloReproductivoEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM ciclos_reproductivos WHERE vaca_id = :vacaId AND tenant_id = :tenantId ORDER BY fecha_inicio DESC")
    List<CicloReproductivoEntidad> findByVacaIdAndTenantId(@Param("vacaId") UUID vacaId, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM ciclos_reproductivos WHERE tenant_id = :tenantId AND estatus = 'EN_CURSO' ORDER BY fecha_inicio DESC")
    List<CicloReproductivoEntidad> findActivos(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM ciclos_reproductivos WHERE estatus = 'COMPLETADO' AND fecha_fin_reposo IS NOT NULL AND fecha_fin_reposo <= :hoy")
    List<CicloReproductivoEntidad> findReposoVencido(@Param("hoy") LocalDate hoy);

    @Modifying
    @Query("UPDATE ciclos_reproductivos SET estatus = :estatus, fecha_parto_real = :fechaPartoReal, dias_reposo = :diasReposo, fecha_fin_reposo = :fechaFinReposo WHERE id = :id AND tenant_id = :tenantId")
    void updateParto(
            @Param("id") UUID id,
            @Param("estatus") String estatus,
            @Param("fechaPartoReal") LocalDate fechaPartoReal,
            @Param("diasReposo") Integer diasReposo,
            @Param("fechaFinReposo") LocalDate fechaFinReposo,
            @Param("tenantId") String tenantId
    );
}
