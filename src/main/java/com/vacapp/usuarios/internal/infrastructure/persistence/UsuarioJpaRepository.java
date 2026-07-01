package com.vacapp.usuarios.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositorio Spring Data JDBC para {@link UsuarioEntidad}.
 */
public interface UsuarioJpaRepository extends CrudRepository<UsuarioEntidad, UUID> {

    @Query("SELECT * FROM usuarios WHERE username = :username")
    Optional<UsuarioEntidad> findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(*) > 0 FROM usuarios WHERE username = :username")
    boolean existsByUsername(@Param("username") String username);
}
