package com.vacapp.historialClinico.internal.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidad de dominio que representa un evento clínico aplicado a un animal.
 * Relaciona un animal con una vacuna (existente o nueva).
 */
@Builder
@Getter
@Setter
public class HistorialClinico {

    private UUID id;

    /** ID del animal al que se aplicó el tratamiento. */
    private UUID animalId;

    /** ID de la vacuna del catálogo; null si se registró manualmente. */
    private UUID vacunaId;

    /** Nombre de la vacuna (del catálogo o escrito manualmente). */
    private String nombreVacuna;

    private String dosis;
    private String viaAdministracion;
    private String lote;
    private LocalDate fechaAplicacion;
    private LocalDate proximaDosis;
    private String notas;
    private String aplicadoPor;
    private String tenantId;
}
