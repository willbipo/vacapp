package com.vacapp.calendario.internal.domain.repository;

import com.vacapp.calendario.internal.domain.model.EventoCalendario;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/** Puerto de salida para eventos del calendario. */
public interface EventoCalendarioRepository {

    EventoCalendario guardar(EventoCalendario evento);

    List<EventoCalendario> listarPorTenant(String tenantId);

    List<EventoCalendario> listarPorMes(LocalDate inicio, LocalDate fin, String tenantId);

    void eliminar(UUID id, String tenantId);
}
