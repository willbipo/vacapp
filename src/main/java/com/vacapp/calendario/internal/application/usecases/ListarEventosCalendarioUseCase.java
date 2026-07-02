package com.vacapp.calendario.internal.application.usecases;

import com.vacapp.calendario.internal.domain.model.EventoCalendario;
import com.vacapp.calendario.internal.domain.repository.EventoCalendarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

/** Caso de uso: listar eventos del calendario por mes. */
@Service
@RequiredArgsConstructor
public class ListarEventosCalendarioUseCase {

    private final EventoCalendarioRepository repository;

    @Transactional(readOnly = true)
    public List<EventoCalendario> ejecutar(LocalDate inicio, LocalDate fin, String tenantId) {
        return repository.listarPorMes(inicio, fin, tenantId);
    }
}
