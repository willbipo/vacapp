package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;

public record AsignarPersonalRequest(
    @NotBlank(message = "El ID del empleado es obligatorio")
    String empleadoId
) {
}
