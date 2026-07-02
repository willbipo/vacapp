package com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.vacunas.internal.domain.model.CategoriaVacuna;

import java.util.UUID;

/** DTO de salida para una categoría de vacunas. */
public record CategoriaVacunaResponse(UUID id, String nombre) {
    public static CategoriaVacunaResponse desde(CategoriaVacuna c) {
        return new CategoriaVacunaResponse(c.getId(), c.getNombre());
    }
}
