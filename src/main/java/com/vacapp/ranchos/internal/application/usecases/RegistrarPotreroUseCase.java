package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
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

    public Potrero ejecutar(String ranchoId, String tenantId, String nombre, Double hectareas, String tipoPasto) {
        return ejecutar(ranchoId, null, tenantId, nombre, hectareas, tipoPasto);
    }

    public Potrero ejecutar(String ranchoId, String seccionId, String tenantId, String nombre, Double hectareas, String tipoPasto) {
        // Validar que no exista otro potrero con el mismo nombre en el rancho
        potreroRepository.obtenerPorNombre(nombre, ranchoId, tenantId)
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Ya existe un potrero con el nombre: " + nombre);
                });

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
