package com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;

import java.util.UUID;

/** DTO de salida para una categoría de insumos. */
public record CategoriaInsumoResponse(UUID id, String nombre) {
    public static CategoriaInsumoResponse desde(CategoriaInsumo c) {
        return new CategoriaInsumoResponse(c.getId(), c.getNombre());
    }
}
