package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import java.time.LocalDateTime;

/**
 * DTO Response para operaciones de rancho.
 */
public record RanchoResponse(
    String id,
    String nombre,
    String descripcion,
    Double hectareas,
    String ubicacion,
    LocalDateTime fechaRegistro,
    LocalDateTime fechaActualizacion
) {}
