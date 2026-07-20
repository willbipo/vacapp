package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.repository.SeccionRepository;
import com.vacapp.ranchos.internal.domain.model.SeccionNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso: Eliminar una sección.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EliminarSeccionUseCase {
    private final SeccionRepository seccionRepository;

    public void ejecutar(String id, String tenantId) {
        if (!seccionRepository.obtenerPorId(id, tenantId).isPresent()) {
            throw new SeccionNoEncontradaException("Sección no encontrada con ID: " + id);
        }
        seccionRepository.eliminar(id, tenantId);
    }
}
