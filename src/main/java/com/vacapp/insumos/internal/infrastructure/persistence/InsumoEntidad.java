package com.vacapp.insumos.internal.infrastructure.persistence;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

/** Clase de mapeo para la tabla {@code insumos} en MySQL.
 * No es una entidad JPA — se mapea automáticamente con Spring Data JDBC. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoEntidad {

    private UUID id;
    private String nombre;
    private CategoriaInsumo categoria;
    private UnidadMedida unidadMedida;
    private Double cantidad;
    private Double cantidadMinima;
    private String descripcion;
    private String proveedor;
    private BigDecimal precioUnitario;
    private String ubicacion;
    private String tenantId;
}
