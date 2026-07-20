package com.vacapp.usuarios.internal.infrastructure.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import java.util.UUID;
import org.springframework.stereotype.Repository;

/**
 * Repositorio Spring Data JDBC para {@link UsuarioEntidad}.
 */
@Repository
public interface UsuarioJpaRepository extends CrudRepository<UsuarioEntidad, UUID> {

    @Query("SELECT * FROM usuarios WHERE username = :username")
    Optional<UsuarioEntidad> findByUsername(@Param("username") String username);

    @Query("SELECT COUNT(*) > 0 FROM usuarios WHERE username = :username")
    boolean existsByUsername(@Param("username") String username);
}
