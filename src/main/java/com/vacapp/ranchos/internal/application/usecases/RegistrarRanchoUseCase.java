package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso: Registrar un nuevo rancho.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarRanchoUseCase {
    private final RanchoRepository ranchoRepository;

    public Rancho ejecutar(
        String tenantId,
        String userId,
        String nombre,
        String descripcion,
        Double hectareas,
        String ubicacion
    ) {
        Rancho rancho = new Rancho(
            UUID.randomUUID().toString(),
            tenantId,
            userId,
            nombre,
            descripcion,
            hectareas,
            ubicacion,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        return ranchoRepository.guardar(rancho);
    }
}
