package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.model.RanchoNoEncontradoException;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Caso de uso: Registrar un nuevo potrero en un rancho.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarPotreroUseCase {
    private final PotreroRepository potreroRepository;
    private final RanchoRepository ranchoRepository;

    public Potrero ejecutar(String ranchoId, String tenantId, String nombre, Double hectareas, String tipoPasto) {
        return ejecutar(ranchoId, null, tenantId, nombre, hectareas, tipoPasto);
    }

    public Potrero ejecutar(String ranchoId, String seccionId, String tenantId, String nombre, Double hectareas, String tipoPasto) {
        // Validar que no exista otro potrero con el mismo nombre en el rancho
        potreroRepository.obtenerPorNombre(nombre, ranchoId, tenantId)
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Ya existe un potrero con el nombre: " + nombre);
                });

        // Validar que las hectáreas del nuevo potrero no sobrepasen el total del rancho
        Rancho rancho = ranchoRepository.obtenerPorId(ranchoId, tenantId)
                .orElseThrow(() -> new RanchoNoEncontradoException("Rancho no encontrado con ID: " + ranchoId));

        Double hectareasActuales = potreroRepository.sumarHectareasPorRancho(ranchoId, tenantId);
        Double hectareasTotales = hectareasActuales + hectareas;

        if (hectareasTotales > rancho.getHectareas()) {
            throw new IllegalArgumentException(
                    String.format("Las hectáreas totales de los potreros (%.2f ha) sobrepasan el total del rancho (%.2f ha). Disponible: %.2f ha",
                            hectareasTotales, rancho.getHectareas(), rancho.getHectareas() - hectareasActuales)
            );
        }

        Potrero potrero;
        if (seccionId == null) {
            potrero = new Potrero(
                    UUID.randomUUID().toString(),
                    ranchoId,
                    nombre,
                    hectareas,
                    tipoPasto
            );
        } else {
            potrero = new Potrero(
                    UUID.randomUUID().toString(),
                    ranchoId,
                    seccionId,
                    nombre,
                    hectareas,
                    tipoPasto
            );
        }
        return potreroRepository.guardar(potrero);
    }
}
