package com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/** DTO para iniciar una gestación. */
public record IniciarGestacionRequest(
        @NotNull UUID vacaId,
        @NotNull LocalDate fechaInicio,
        String notas
) {}
