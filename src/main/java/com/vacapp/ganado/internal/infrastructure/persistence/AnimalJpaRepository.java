package com.vacapp.ganado.internal.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Repositorio JPA para la tabla {@code animales}. */
public interface AnimalJpaRepository extends JpaRepository<AnimalEntidad, UUID> {

    List<AnimalEntidad> findAllByTenantId(String tenantId);

    Optional<AnimalEntidad> findByIdAndTenantId(UUID id, String tenantId);

    void deleteByIdAndTenantId(UUID id, String tenantId);
}
