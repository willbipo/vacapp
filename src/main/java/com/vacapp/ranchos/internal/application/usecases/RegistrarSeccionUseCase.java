package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.domain.repository.SeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Caso de uso: Registrar una nueva sección en un rancho.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarSeccionUseCase {
    private final SeccionRepository seccionRepository;

    public Seccion ejecutar(String ranchoId, String tenantId, String nombre) {
        // Validar que no exista otra sección con el mismo nombre en el rancho
        seccionRepository.obtenerPorNombre(nombre, ranchoId, tenantId)
                .ifPresent(s -> {
                    throw new IllegalArgumentException("Ya existe una sección con el nombre: " + nombre);
                });

        Seccion seccion = new Seccion(
                UUID.randomUUID().toString(),
                ranchoId,
                nombre
        );
        return seccionRepository.guardar(seccion);
    }
}
