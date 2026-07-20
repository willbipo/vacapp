package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.domain.repository.SeccionRepository;
import com.vacapp.ranchos.internal.domain.model.SeccionNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Caso de uso: Obtener una sección por ID.
 */
@Service
@RequiredArgsConstructor
public class ObtenerSeccionUseCase {
    private final SeccionRepository seccionRepository;

    public Seccion ejecutar(String id, String tenantId) {
        Optional<Seccion> seccion = seccionRepository.obtenerPorId(id, tenantId);
        return seccion.orElseThrow(() -> new SeccionNoEncontradaException("Sección no encontrada con ID: " + id));
    }
}
