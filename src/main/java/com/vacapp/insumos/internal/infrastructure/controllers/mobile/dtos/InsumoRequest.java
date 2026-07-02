package com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/** DTO de entrada para registrar o actualizar un insumo. */
public record InsumoRequest(
        @NotBlank String nombre,
        @NotBlank String categoria,
        @NotNull UnidadMedida unidadMedida,
        Double cantidad,
        Double cantidadMinima,
        String descripcion,
        String proveedor,
        BigDecimal precioUnitario,
        String ubicacion
) {}
