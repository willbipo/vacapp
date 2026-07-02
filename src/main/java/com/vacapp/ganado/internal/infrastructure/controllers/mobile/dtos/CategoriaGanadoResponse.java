package com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.ganado.internal.domain.model.CategoriaGanado;

import java.util.UUID;

/** DTO de salida para una categoría de ganado. */
public record CategoriaGanadoResponse(UUID id, String nombre) {
    public static CategoriaGanadoResponse desde(CategoriaGanado c) {
        return new CategoriaGanadoResponse(c.getId(), c.getNombre());
    }
}
