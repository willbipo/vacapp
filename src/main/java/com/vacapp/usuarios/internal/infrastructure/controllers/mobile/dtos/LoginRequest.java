package com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para el endpoint de inicio de sesión.
 */
public record LoginRequest(

        @Schema(description = "Nombre de usuario", example = "admin")
        @NotBlank(message = "El nombre de usuario es obligatorio.")
        String username,

        @Schema(description = "Contraseña del usuario", example = "Password123*")
        @NotBlank(message = "La contraseña es obligatoria.")
        String password
) {}
