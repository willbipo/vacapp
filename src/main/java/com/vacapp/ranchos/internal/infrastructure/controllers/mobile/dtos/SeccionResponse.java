package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import java.time.LocalDateTime;

public record SeccionResponse(
    String id,
    String ranchoId,
    String nombre,
    LocalDateTime fechaRegistro,
    LocalDateTime fechaActualizacion
) {
}
