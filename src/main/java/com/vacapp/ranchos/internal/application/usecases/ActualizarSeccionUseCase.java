package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.domain.repository.SeccionRepository;
import com.vacapp.ranchos.internal.domain.model.SeccionNoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso: Actualizar una sección.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ActualizarSeccionUseCase {
    private final SeccionRepository seccionRepository;

    public Seccion ejecutar(String id, String tenantId, String nombre) {
        Seccion seccion = seccionRepository.obtenerPorId(id, tenantId)
                .orElseThrow(() -> new SeccionNoEncontradaException("Sección no encontrada con ID: " + id));

        // Validar que no exista otra sección con el mismo nombre en el rancho
        seccionRepository.obtenerPorNombre(nombre, seccion.getRanchoId(), tenantId)
                .ifPresent(s -> {
                    if (!s.getId().equals(id)) {
                        throw new IllegalArgumentException("Ya existe una sección con el nombre: " + nombre);
                    }
                });

        seccion.setNombre(nombre);
        seccionRepository.actualizar(seccion);
        return seccion;
    }
}
