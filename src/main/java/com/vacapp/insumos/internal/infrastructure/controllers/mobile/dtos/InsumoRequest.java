package com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/** DTO de entrada para registrar o actualizar un insumo. */
public record InsumoRequest(
        @Schema(description = "Nombre del insumo", example = "Alimento balanceado")
        @NotBlank String nombre,
        @Schema(description = "Categoría del insumo", example = "ALIMENTO")
        @NotNull CategoriaInsumo categoria,
        @Schema(description = "Unidad de medida", example = "KG")
        @NotNull UnidadMedida unidadMedida,
        @Schema(description = "Cantidad disponible", example = "120.5")
        Double cantidad,
        @Schema(description = "Cantidad mínima para alerta", example = "20.0")
        Double cantidadMinima,
        @Schema(description = "Descripción del insumo", example = "Saco para bovinos")
        String descripcion,
        @Schema(description = "Proveedor del insumo", example = "Proveedor Norte")
        String proveedor,
        @Schema(description = "Precio unitario", example = "450.00")
        BigDecimal precioUnitario,
        @Schema(description = "Ubicación física del insumo", example = "Bodega A")
        String ubicacion
) {}
