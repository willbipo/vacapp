package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.*;

/**
 * DTO Request para crear/actualizar rancho.
 */
public record RanchoRequest(
    @NotBlank(message = "El nombre del rancho no puede estar vacío")
    @Size(min = 2, max = 255, message = "El nombre debe tener entre 2 y 255 caracteres")
    String nombre,

    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    String descripcion,

    @NotNull(message = "Las hectáreas no pueden ser nulas")
    @Positive(message = "Las hectáreas deben ser mayores a 0")
    Double hectareas,

    @Size(max = 255, message = "La ubicación no puede exceder 255 caracteres")
    String ubicacion
) {}
