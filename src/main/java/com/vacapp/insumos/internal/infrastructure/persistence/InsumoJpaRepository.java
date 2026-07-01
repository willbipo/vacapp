package com.vacapp.insumos.internal.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio JPA para la tabla {@code insumos}. */
public interface InsumoJpaRepository extends JpaRepository<InsumoEntidad, UUID> {

    List<InsumoEntidad> findAllByTenantId(String tenantId);

    Optional<InsumoEntidad> findByIdAndTenantId(UUID id, String tenantId);

    void deleteByIdAndTenantId(UUID id, String tenantId);
}
