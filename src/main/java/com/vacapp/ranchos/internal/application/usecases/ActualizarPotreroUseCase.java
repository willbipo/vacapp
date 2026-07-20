package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import com.vacapp.ranchos.internal.domain.model.PotreroNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso: Actualizar un potrero.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ActualizarPotreroUseCase {
    private final PotreroRepository potreroRepository;

    public Potrero ejecutar(String id, String tenantId, String nombre, Double hectareas, String tipoPasto) {
        Potrero potrero = potreroRepository.obtenerPorId(id, tenantId)
                .orElseThrow(() -> new PotreroNoEncontradoException("Potrero no encontrado con ID: " + id));

        // Validar que no exista otro potrero con el mismo nombre en el rancho
        potreroRepository.obtenerPorNombre(nombre, potrero.getRanchoId(), tenantId)
                .ifPresent(p -> {
                    if (!p.getId().equals(id)) {
                        throw new IllegalArgumentException("Ya existe un potrero con el nombre: " + nombre);
                    }
                });

        potrero.setNombre(nombre);
        potrero.setHectareas(hectareas);
        potrero.setTipoPasto(tipoPasto);
        potreroRepository.actualizar(potrero);
        return potrero;
    }
}
