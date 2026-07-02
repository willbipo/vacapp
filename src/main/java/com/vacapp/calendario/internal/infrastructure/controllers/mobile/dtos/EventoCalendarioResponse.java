package com.vacapp.calendario.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.calendario.internal.domain.model.EventoCalendario;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida de un evento del calendario. */
public record EventoCalendarioResponse(
        UUID id,
        String titulo,
        String descripcion,
        LocalDate fecha,
        String tipo
) {
    public static EventoCalendarioResponse desde(EventoCalendario ev) {
        return new EventoCalendarioResponse(
                ev.getId(),
                ev.getTitulo(),
                ev.getDescripcion(),
                ev.getFecha(),
                ev.getTipo()
        );
    }
}
