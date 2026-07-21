package com.vacapp.empleados.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.*;

/**
 * DTO Request para crear/actualizar empleado.
 * Valida que el rol sea DOCTOR o WORKER (no ADMIN, no FARMER).
 * Roles permitidos:
 * - DOCTOR: Veterinario del rancho
 * - WORKER: Trabajador de campo
 */
public record EmpleadoRequest(
    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    String nombre,

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "El email debe ser válido")
    String email,

    @Pattern(regexp = "^[+\\d\\s\\-()]*$", message = "El teléfono tiene formato inválido")
    String telefono,

    @NotBlank(message = "El rol no puede estar vacío")
    String rol,

    @NotBlank(message = "El estado no puede estar vacío")
    String estado,

    String ranchoId
) {}
