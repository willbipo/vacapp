package com.vacapp.calendario.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/** DTO de entrada para crear un evento en el calendario. */
public record EventoCalendarioRequest(
        @NotBlank String titulo,
        String descripcion,
        @NotNull LocalDate fecha,
        String tipo
) {}
