package com.vacapp.vacunas.internal.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio JPA para la tabla {@code vacunas}. */
public interface VacunaJpaRepository extends JpaRepository<VacunaEntidad, UUID> {

    List<VacunaEntidad> findAllByTenantId(String tenantId);

    Optional<VacunaEntidad> findByIdAndTenantId(UUID id, String tenantId);

    void deleteByIdAndTenantId(UUID id, String tenantId);
}
