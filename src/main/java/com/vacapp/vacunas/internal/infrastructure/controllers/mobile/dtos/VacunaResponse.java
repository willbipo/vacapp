package com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.vacunas.internal.domain.model.TipoVacuna;
import com.vacapp.vacunas.internal.domain.model.Vacuna;
import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida con los datos de una vacuna. */
public record VacunaResponse(
        UUID id,
        String nombre,
        TipoVacuna tipo,
        String laboratorio,
        String descripcion,
        String dosis,
        ViaAdministracion viaAdministracion,
        String lote,
        LocalDate fechaCaducidad,
        Integer stock,
        String unidadMedida,
        String temperaturaAlmacenamiento,
        Integer intervaloDias
) {
    public static VacunaResponse desde(Vacuna v) {
        return new VacunaResponse(
                v.getId(), v.getNombre(), v.getTipo(), v.getLaboratorio(),
                v.getDescripcion(), v.getDosis(), v.getViaAdministracion(),
                v.getLote(), v.getFechaCaducidad(), v.getStock(),
                v.getUnidadMedida(), v.getTemperaturaAlmacenamiento(), v.getIntervaloDias()
        );
    }
}
