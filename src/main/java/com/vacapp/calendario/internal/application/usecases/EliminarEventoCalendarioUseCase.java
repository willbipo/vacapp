package com.vacapp.calendario.internal.application.usecases;

import com.vacapp.calendario.internal.domain.repository.EventoCalendarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Caso de uso: eliminar un evento del calendario. */
@Service
@RequiredArgsConstructor
public class EliminarEventoCalendarioUseCase {

    private final EventoCalendarioRepository repository;

    @Transactional
    public void ejecutar(UUID id, String tenantId) {
        repository.eliminar(id, tenantId);
    }
}
