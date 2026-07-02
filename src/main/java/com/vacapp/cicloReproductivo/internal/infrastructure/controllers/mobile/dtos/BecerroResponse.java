package com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.cicloReproductivo.internal.domain.model.Becerro;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida de un becerro. */
public record BecerroResponse(
        UUID id,
        String nombre,
        LocalDate fechaNacimiento,
        String sexo,
        String nombrePadre,
        String razaPadre,
        String notas,
        UUID madreId,
        UUID cicloId
) {
    public static BecerroResponse desde(Becerro b) {
        return new BecerroResponse(
                b.getId(), b.getNombre(), b.getFechaNacimiento(), b.getSexo(),
                b.getNombrePadre(), b.getRazaPadre(), b.getNotas(),
                b.getMadreId(), b.getCicloId()
        );
    }
}
