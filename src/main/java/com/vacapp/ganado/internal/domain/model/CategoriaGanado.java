package com.vacapp.ganado.internal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

/** Entidad de negocio que representa una categoría/tipo de ganado. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoriaGanado {

    private UUID id;
    /** Nombre de la categoría (ej. "Bovino", "Caprino", "Porcino"). */
    private String nombre;
    private String tenantId;
}
