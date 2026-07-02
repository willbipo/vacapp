package com.vacapp.usuarios.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.usuarios.internal.domain.model.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO de entrada para el registro de un nuevo usuario.
 */
public record RegistroRequest(

        @Schema(description = "Nombre de usuario", example = "nuevo.usuario")
        @NotBlank(message = "El nombre de usuario es obligatorio.")
        @Size(min = 3, max = 50, message = "El username debe tener entre 3 y 50 caracteres.")
        String username,

        @Schema(description = "Correo electrónico del usuario", example = "usuario@vacapp.com")
        @NotBlank(message = "El correo electrónico es obligatorio.")
        @Email(message = "El formato del correo electrónico no es válido.")
        String email,

        @Schema(description = "Contraseña del usuario", example = "Password123*")
        @NotBlank(message = "La contraseña es obligatoria.")
        @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres.")
        String password,

        @Schema(description = "Rol del usuario", example = "FARMER")
        @NotNull(message = "El rol es obligatorio.")
        Rol role,

        @Schema(description = "Tenant al que pertenece el usuario", example = "tenant-default")
        @NotBlank(message = "El identificador del tenant es obligatorio.")
        String tenantId
) {}
