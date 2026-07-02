package com.vacapp.cicloReproductivo.internal.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/** Entidad de dominio: ciclo reproductivo de una vaca. */
@Builder
@Getter
@Setter
public class CicloReproductivo {

    private UUID id;
    private UUID vacaId;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaParto;
    private LocalDate fechaPartoReal;

    /** Días de reposo configurados al registrar el parto. */
    private Integer diasReposo;

    /** Fecha en que finaliza el reposo y la vaca vuelve a VIGENTE. */
    private LocalDate fechaFinReposo;

    /** EN_CURSO o COMPLETADO */
    private String estatus;

    private String notas;
    private String tenantId;
}
