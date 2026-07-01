package com.vacapp.usuarios.internal.domain.repository;

import com.vacapp.usuarios.internal.domain.model.Usuario;

/**
 * Puerto de salida para la generación de tokens JWT.
 * La implementación vive en la capa de infraestructura (core/security).
 */
public interface GeneradorDeToken {

    /** Genera un token JWT firmado con los datos del usuario. */
    String generar(Usuario usuario);
}
