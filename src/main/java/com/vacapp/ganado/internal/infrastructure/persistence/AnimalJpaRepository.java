package com.vacapp.ganado.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio Spring Data JDBC para la tabla {@code animales}. */
public interface AnimalJpaRepository extends CrudRepository<AnimalEntidad, UUID> {

    @Query("SELECT * FROM animales WHERE tenant_id = :tenantId")
    List<AnimalEntidad> findAllByTenantId(@Param("tenantId") String tenantId);

    @Query("SELECT * FROM animales WHERE id = :id AND tenant_id = :tenantId")
    Optional<AnimalEntidad> findByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);

    @Query("SELECT * FROM animales WHERE numero_identificador = :numId AND tenant_id = :tenantId LIMIT 1")
    Optional<AnimalEntidad> findByNumeroIdentificadorAndTenantId(
            @Param("numId") String numId, @Param("tenantId") String tenantId);

    @Modifying
    @Query("UPDATE animales SET estatus = :estatus WHERE id = :id AND tenant_id = :tenantId")
    void updateEstatus(@Param("id") UUID id, @Param("estatus") String estatus, @Param("tenantId") String tenantId);

    @Modifying
    @Query("UPDATE animales SET estatus = 'REPOSO', fecha_inicio_reposo = :fechaInicio, fecha_fin_reposo = :fechaFin WHERE id = :id AND tenant_id = :tenantId")
    void updateReposo(@Param("id") UUID id, @Param("fechaInicio") java.time.LocalDate fechaInicio, @Param("fechaFin") java.time.LocalDate fechaFin, @Param("tenantId") String tenantId);

    @Query("DELETE FROM animales WHERE id = :id AND tenant_id = :tenantId")
    void deleteByIdAndTenantId(@Param("id") UUID id, @Param("tenantId") String tenantId);
}
