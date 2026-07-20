package com.vacapp.insumos.internal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Entidad de negocio pura que representa un insumo del rancho.
 * Sin anotaciones JPA ni dependencias de framework.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Insumo {

    private UUID id;

    /** Identificador del rancho al que pertenece el insumo. */
    private String ranchoId;

    /** Nombre del insumo (ej. "Pala redonda", "Maíz amarillo"). */
    private String nombre;

    /** Categoría del insumo. */
    private CategoriaInsumo categoria;

    /** Unidad en la que se mide la cantidad. */
    private UnidadMedida unidadMedida;

    /** Cantidad actual en inventario. */
    private Double cantidad;

    /** Cantidad mínima antes de generar alerta de reabastecimiento. */
    private Double cantidadMinima;

    /** Descripción u observaciones adicionales. */
    private String descripcion;

    /** Proveedor o lugar de compra. */
    private String proveedor;

    /** Precio unitario de compra. */
    private BigDecimal precioUnitario;

    /** Ubicación física dentro del rancho (ej. "Bodega principal"). */
    private String ubicacion;

    /** Identificador del tenant. */
    private String tenantId;
}
