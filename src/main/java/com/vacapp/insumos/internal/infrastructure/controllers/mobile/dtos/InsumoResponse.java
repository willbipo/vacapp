package com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.model.Insumo;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.util.UUID;

/** DTO de salida con los datos de un insumo. */
public record InsumoResponse(
    @Schema(description = "Identificador único del insumo", example = "6aa2b11e-66c2-4f21-a57e-a65014d6f6da")
        UUID id,
    @Schema(description = "Nombre del insumo", example = "Alimento balanceado")
        String nombre,
    @Schema(description = "Categoría del insumo", example = "ALIMENTO")
        CategoriaInsumo categoria,
    @Schema(description = "Unidad de medida", example = "KG")
        UnidadMedida unidadMedida,
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
) {
    public static InsumoResponse desde(Insumo i) {
        return new InsumoResponse(
                i.getId(), i.getNombre(), i.getCategoria(), i.getUnidadMedida(),
                i.getCantidad(), i.getCantidadMinima(), i.getDescripcion(),
                i.getProveedor(), i.getPrecioUnitario(), i.getUbicacion()
        );
    }
}
