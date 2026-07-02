package com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO de entrada para el endpoint de inicio de sesión.
 */
public record LoginRequest(

        @NotBlank(message = "El nombre de usuario es obligatorio.")
        String username,

        @NotBlank(message = "La contraseña es obligatoria.")
        String password
) {}
