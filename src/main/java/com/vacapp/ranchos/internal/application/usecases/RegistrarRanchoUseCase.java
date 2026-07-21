package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso: Registrar un nuevo rancho.
 */
@Slf4j
@Service
@RequiredArgsConstructor
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
        log.info("[RANCHOS] RegistrarRanchoUseCase - userId: {}, tenantId: {}, nombre: {}", userId, tenantId, nombre);
        
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
        
        Rancho guardado = ranchoRepository.guardar(rancho);
        log.info("[RANCHOS] Rancho guardado con ID: {}", guardado.getId());
        
        return guardado;
    }
}
