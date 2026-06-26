package com.vacapp.usuarios.application.ports;

import com.vacapp.usuarios.domain.models.Usuario;

/**
 * Puerto de salida para la generación de tokens JWT.
 * La implementación vive en la capa de infraestructura (core/security).
 */
public interface GeneradorDeToken {

    /** Genera un token JWT firmado con los datos del usuario. */
    String generar(Usuario usuario);
}
