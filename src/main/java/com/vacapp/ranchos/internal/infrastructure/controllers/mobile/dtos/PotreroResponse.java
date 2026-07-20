package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import java.time.LocalDateTime;

public record PotreroResponse(
    String id,
    String ranchoId,
    String seccionId,
    String nombre,
    Double hectareas,
    String tipoPasto,
    LocalDateTime fechaRegistro,
    LocalDateTime fechaActualizacion
) {
}
