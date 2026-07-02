package com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos;

/**
 * DTO de salida tras un inicio de sesión exitoso.
 */
public record LoginResponse(
        String token,
        String username,
        String role,
        String tenantId
) {}
