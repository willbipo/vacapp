package com.vacapp.calendario.internal.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/** Entidad de dominio: evento personalizado del calendario. */
@Builder
@Getter
@Setter
public class EventoCalendario {

    private UUID id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;

    /** Tipo para color: VACUNA, VETERINARIO, MANTENIMIENTO, OTRO. */
    private String tipo;

    private String tenantId;
}
