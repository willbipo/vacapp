package com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.cicloReproductivo.internal.domain.model.CicloReproductivo;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida de un ciclo reproductivo. */
public record CicloReproductivoResponse(
        UUID id,
        UUID vacaId,
        LocalDate fechaInicio,
        LocalDate fechaEstimadaParto,
        LocalDate fechaPartoReal,
        Integer diasReposo,
        LocalDate fechaFinReposo,
        String estatus,
        String notas
) {
    public static CicloReproductivoResponse desde(CicloReproductivo c) {
        return new CicloReproductivoResponse(
                c.getId(), c.getVacaId(),
                c.getFechaInicio(), c.getFechaEstimadaParto(), c.getFechaPartoReal(),
                c.getDiasReposo(), c.getFechaFinReposo(),
                c.getEstatus(), c.getNotas()
        );
    }
}
