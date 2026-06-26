package com.vacapp.usuarios.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio JPA de Spring Data para {@link UsuarioEntidad}.
 */
public interface UsuarioJpaRepository extends JpaRepository<UsuarioEntidad, UUID> {

    Optional<UsuarioEntidad> findByUsername(String username);

    boolean existsByUsername(String username);
}
