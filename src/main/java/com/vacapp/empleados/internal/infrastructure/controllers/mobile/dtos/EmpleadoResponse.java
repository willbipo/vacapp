package com.vacapp.empleados.internal.infrastructure.controllers.mobile.dtos;

import java.time.LocalDateTime;

/**
 * DTO Response para empleado.
 */
public record EmpleadoResponse(
    String id,
    String nombre,
    String email,
    String telefono,
    String rol,
    String estado,
    LocalDateTime fechaRegistro
) {}
