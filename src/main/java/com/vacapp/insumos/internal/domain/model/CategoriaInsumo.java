package com.vacapp.insumos.internal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/** Entidad de negocio que representa una categoría de insumos. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaInsumo {

    private UUID id;
    /** Nombre de la categoría (ej. "Herramienta", "Alimento"). */
    private String nombre;
    private String tenantId;
}
