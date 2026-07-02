package com.vacapp.cicloReproductivo.internal.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/** Entidad de dominio: becerro nacido de una vaca. */
@Builder
@Getter
@Setter
public class Becerro {

    private UUID id;
    private String nombre;
    private LocalDate fechaNacimiento;

    /** MACHO o HEMBRA */
    private String sexo;

    private String nombrePadre;
    private String razaPadre;
    private String notas;

    /** ID de la madre (animal). */
    private UUID madreId;

    /** ID del ciclo reproductivo al que pertenece. */
    private UUID cicloId;

    private String tenantId;
}
