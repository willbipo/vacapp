package com.vacapp.vacunas.internal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/** Entidad de negocio que representa un tipo/categoría de vacuna. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaVacuna {

    private UUID id;
    /** Nombre del tipo de vacuna (ej. "Aftosa", "Brucella Abortus"). */
    private String nombre;
    private String tenantId;
}
