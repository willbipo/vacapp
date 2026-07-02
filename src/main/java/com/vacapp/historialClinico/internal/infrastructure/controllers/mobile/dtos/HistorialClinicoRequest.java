package com.vacapp.historialClinico.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO de entrada para registrar un evento clínico.
 *
 * @param animalId         ID del animal (obligatorio).
 * @param vacunaId         ID de la vacuna del catálogo (null si es manual).
 * @param nombreVacuna     Nombre del tratamiento/vacuna (obligatorio).
 * @param dosis            Dosis aplicada.
 * @param viaAdministracion Vía de administración.
 * @param lote             Número de lote.
 * @param fechaAplicacion  Fecha de aplicación (obligatorio).
 * @param proximaDosis     Próxima fecha de aplicación (calculada o manual).
 * @param notas            Observaciones.
 * @param aplicadoPor      Persona que aplicó el tratamiento.
 */
public record HistorialClinicoRequest(
        @NotNull UUID animalId,
        UUID vacunaId,
        @NotBlank String nombreVacuna,
        String dosis,
        String viaAdministracion,
        String lote,
        @NotNull LocalDate fechaAplicacion,
        LocalDate proximaDosis,
        String notas,
        String aplicadoPor
) {}
