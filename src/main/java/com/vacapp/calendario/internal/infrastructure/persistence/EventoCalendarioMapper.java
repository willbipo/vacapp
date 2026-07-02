package com.vacapp.calendario.internal.infrastructure.persistence;

import com.vacapp.calendario.internal.domain.model.EventoCalendario;
import org.springframework.stereotype.Component;

/** Transforma entre {@link EventoCalendario} y {@link EventoCalendarioEntidad}. */
@Component
public class EventoCalendarioMapper {

    public EventoCalendario aDominio(EventoCalendarioEntidad e) {
        return EventoCalendario.builder()
                .id(e.getId())
                .titulo(e.getTitulo())
                .descripcion(e.getDescripcion())
                .fecha(e.getFecha())
                .tipo(e.getTipo())
                .tenantId(e.getTenantId())
                .build();
    }

    public EventoCalendarioEntidad aEntidad(EventoCalendario ev) {
        return EventoCalendarioEntidad.builder()
                .id(ev.getId())
                .titulo(ev.getTitulo())
                .descripcion(ev.getDescripcion())
                .fecha(ev.getFecha())
                .tipo(ev.getTipo())
                .tenantId(ev.getTenantId())
                .build();
    }
}
