package com.vacapp.vacunas.internal.infrastructure.persistence;

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

/** Entidad JDBC para la tabla {@code categorias_vacunas}. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("categorias_vacunas")
public class CategoriaVacunaEntidad implements Persistable<UUID> {

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
