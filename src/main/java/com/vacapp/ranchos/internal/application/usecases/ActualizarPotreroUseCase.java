package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.model.PotreroNoEncontradoException;
import com.vacapp.ranchos.internal.domain.model.RanchoNoEncontradoException;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
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
    private final RanchoRepository ranchoRepository;

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

        // Validar que las hectáreas actualizadas no sobrepasen el total del rancho
        Rancho rancho = ranchoRepository.obtenerPorId(potrero.getRanchoId(), tenantId)
                .orElseThrow(() -> new RanchoNoEncontradoException("Rancho no encontrado con ID: " + potrero.getRanchoId()));

        Double hectareasActuales = potreroRepository.sumarHectareasPorRancho(potrero.getRanchoId(), tenantId);
        Double hectareasSinEstePotrero = hectareasActuales - potrero.getHectareas();
        Double hectareasTotales = hectareasSinEstePotrero + hectareas;

        if (hectareasTotales > rancho.getHectareas()) {
            throw new IllegalArgumentException(
                    String.format("Las hectáreas totales de los potreros (%.2f ha) sobrepasan el total del rancho (%.2f ha). Disponible: %.2f ha",
                            hectareasTotales, rancho.getHectareas(), rancho.getHectareas() - hectareasSinEstePotrero)
            );
        }

        potrero.setNombre(nombre);
        potrero.setHectareas(hectareas);
        potrero.setTipoPasto(tipoPasto);
        potreroRepository.actualizar(potrero);
        return potrero;
    }
}
