package com.vacapp.insumos.internal.infrastructure.persistence;

import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

/** Clase de mapeo para la tabla {@code insumos} en MySQL.
 * No es una entidad JPA — se mapea automáticamente con Spring Data JDBC. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("insumos")
public class InsumoEntidad implements Persistable<UUID> {

    @Id
    private UUID id;
    private String nombre;
    private String categoria;
    private UnidadMedida unidadMedida;
    private Double cantidad;
    private Double cantidadMinima;
    private String descripcion;
    private String proveedor;
    private BigDecimal precioUnitario;
    private String ubicacion;
    private String tenantId;

    /** Siempre es nueva: el id se genera en la capa de aplicación. */
    @Transient
    @Builder.Default
    private boolean esNueva = true;

    @Override
    public boolean isNew() {
        return esNueva;
    }
}
