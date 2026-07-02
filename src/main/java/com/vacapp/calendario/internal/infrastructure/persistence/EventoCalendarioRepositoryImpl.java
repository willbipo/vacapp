package com.vacapp.calendario.internal.infrastructure.persistence;

import com.vacapp.calendario.internal.domain.model.EventoCalendario;
import com.vacapp.calendario.internal.domain.repository.EventoCalendarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/** Implementación del puerto {@link EventoCalendarioRepository}. */
@Repository
@RequiredArgsConstructor
public class EventoCalendarioRepositoryImpl implements EventoCalendarioRepository {

    private final EventoCalendarioJdbcRepository jdbcRepository;
    private final EventoCalendarioMapper mapper;

    @Override
    @Transactional
    public EventoCalendario guardar(EventoCalendario evento) {
        return mapper.aDominio(jdbcRepository.save(mapper.aEntidad(evento)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoCalendario> listarPorTenant(String tenantId) {
        return jdbcRepository.findAllByTenantId(tenantId).stream()
                .map(mapper::aDominio).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<EventoCalendario> listarPorMes(LocalDate inicio, LocalDate fin, String tenantId) {
        return jdbcRepository.findByFechaBetweenAndTenantId(inicio, fin, tenantId).stream()
                .map(mapper::aDominio).toList();
    }

    @Override
    @Transactional
    public void eliminar(UUID id, String tenantId) {
        jdbcRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
