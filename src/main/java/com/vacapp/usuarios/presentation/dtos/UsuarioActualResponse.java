package com.vacapp.usuarios.presentation.dtos;

/**
 * DTO que expone el nombre del usuario actualmente autenticado.
 */
public record UsuarioActualResponse(
        String username
) {}
