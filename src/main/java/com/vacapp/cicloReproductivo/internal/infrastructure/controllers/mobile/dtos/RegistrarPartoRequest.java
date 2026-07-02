package com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/** DTO para registrar el parto y los datos del becerro. */
public record RegistrarPartoRequest(
        @NotNull LocalDate fechaPartoReal,
        @NotNull Integer diasReposo,
        // Datos del becerro
        @NotBlank String nombreBecerro,
        @NotNull LocalDate fechaNacimientoBecerro,
        @NotBlank String sexoBecerro,
        String nombrePadre,
        String razaPadre,
        String notasBecerro
) {}
