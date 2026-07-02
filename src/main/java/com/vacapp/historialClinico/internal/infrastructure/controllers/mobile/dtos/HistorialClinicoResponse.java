package com.vacapp.historialClinico.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.historialClinico.internal.domain.model.HistorialClinico;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida con los datos de un evento clínico. */
public record HistorialClinicoResponse(
        UUID id,
        UUID animalId,
        UUID vacunaId,
        String nombreVacuna,
        String dosis,
        String viaAdministracion,
        String lote,
        LocalDate fechaAplicacion,
        LocalDate proximaDosis,
        String notas,
        String aplicadoPor
) {
    public static HistorialClinicoResponse desde(HistorialClinico h) {
        return new HistorialClinicoResponse(
                h.getId(),
                h.getAnimalId(),
                h.getVacunaId(),
                h.getNombreVacuna(),
                h.getDosis(),
                h.getViaAdministracion(),
                h.getLote(),
                h.getFechaAplicacion(),
                h.getProximaDosis(),
                h.getNotas(),
                h.getAplicadoPor()
        );
    }
}
