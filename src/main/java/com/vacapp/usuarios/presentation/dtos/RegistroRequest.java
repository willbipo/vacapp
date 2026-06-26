package com.vacapp.usuarios.presentation.dtos;

import com.vacapp.usuarios.domain.models.Rol;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para el registro de un nuevo usuario.
 */
public record RegistroRequest(

        @NotBlank(message = "El nombre de usuario es obligatorio.")
        @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres.")
        String username,

        @NotBlank(message = "El correo electrónico es obligatorio.")
        @Email(message = "El formato del correo electrónico no es válido.")
        String email,

        @NotBlank(message = "La contraseña es obligatoria.")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
        String password,

        @NotNull(message = "El rol es obligatorio.")
        Rol role,

        @NotBlank(message = "El identificador del tenant es obligatorio.")
        String tenantId
) {}
