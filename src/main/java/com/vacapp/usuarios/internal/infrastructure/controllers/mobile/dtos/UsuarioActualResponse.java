package com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos;

/**
 * DTO que expone el nombre del usuario actualmente autenticado.
 */
public record UsuarioActualResponse(
        String username
) {}
