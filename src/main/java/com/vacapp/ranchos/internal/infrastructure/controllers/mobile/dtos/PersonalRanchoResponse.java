package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import java.time.LocalDateTime;

public record PersonalRanchoResponse(
    String id,
    String empleadoId,
    String ranchoId,
    LocalDateTime fechaAsignacion,
    LocalDateTime fechaFinAsignacion,
    boolean activo
) {
}
