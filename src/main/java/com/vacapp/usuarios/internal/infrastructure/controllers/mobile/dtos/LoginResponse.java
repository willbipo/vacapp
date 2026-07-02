package com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO de salida tras un inicio de sesión exitoso.
 */
public record LoginResponse(
        @Schema(description = "JWT emitido para la sesión", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,
        @Schema(description = "Nombre de usuario autenticado", example = "admin")
        String username,
        @Schema(description = "Rol del usuario autenticado", example = "ADMIN")
        String role,
        @Schema(description = "Tenant del usuario autenticado", example = "tenant-default")
        String tenantId
) {}
