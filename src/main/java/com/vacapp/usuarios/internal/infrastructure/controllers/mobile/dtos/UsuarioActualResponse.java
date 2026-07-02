package com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * DTO que expone el nombre del usuario actualmente autenticado.
 */
public record UsuarioActualResponse(
        @Schema(description = "Nombre de usuario autenticado", example = "admin")
        String username
) {}
