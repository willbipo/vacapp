package com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.model.Insumo;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;

import java.math.BigDecimal;
import java.util.UUID;

/** DTO de salida con los datos de un insumo. */
public record InsumoResponse(
        UUID id,
        String nombre,
        CategoriaInsumo categoria,
        UnidadMedida unidadMedida,
        Double cantidad,
        Double cantidadMinima,
        String descripcion,
        String proveedor,
        BigDecimal precioUnitario,
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
