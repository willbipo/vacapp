package com.vacapp.insumos.internal.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/** Entidad JDBC para la tabla {@code categorias_insumos}. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("categorias_insumos")
public class CategoriaInsumoEntidad implements Persistable<UUID> {

    @Id
    private UUID id;
    private String nombre;
    private String tenantId;

    @Transient
    @Builder.Default
    private boolean esNueva = true;

    @Override
    public boolean isNew() { return esNueva; }
}
