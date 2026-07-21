package com.vacapp.usuarios;

/**
 * Datos necesarios para generar un token JWT.
 * Forma parte de la API pública del módulo usuarios.
 */
public record DatosToken(String userId, String username, String role, String tenantId) {}
