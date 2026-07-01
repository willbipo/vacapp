package com.vacapp.usuarios;

/**
 * Puerto de salida para la generación de tokens JWT.
 * La implementación vive en la capa de infraestructura (core/security).
 * Forma parte de la API pública del módulo usuarios.
 */
public interface GeneradorDeToken {

    /** Genera un token JWT firmado con los datos del usuario. */
    String generar(DatosToken datos);
}
