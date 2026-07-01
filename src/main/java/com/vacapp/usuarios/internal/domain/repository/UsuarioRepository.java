package com.vacapp.usuarios.internal.domain.repository;

import com.vacapp.usuarios.internal.domain.model.Usuario;

import java.util.Optional;

/**
 * Puerto de salida para la persistencia de usuarios.
 * La implementación vive en la capa de infraestructura.
 */
public interface UsuarioRepository {

    /** Persiste o actualiza un usuario y retorna la entidad guardada. */
    Usuario guardar(Usuario usuario);

    /** Busca un usuario por su nombre de usuario. */
    Optional<Usuario> buscarPorUsername(String username);

    /** Verifica si ya existe un usuario con el nombre de usuario dado. */
    boolean existePorUsername(String username);
}
