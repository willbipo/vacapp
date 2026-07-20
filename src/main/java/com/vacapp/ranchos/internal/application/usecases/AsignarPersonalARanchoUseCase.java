package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.PersonalRancho;
import com.vacapp.ranchos.internal.domain.repository.PersonalRanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso: Asignar un empleado a un rancho.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AsignarPersonalARanchoUseCase {
    private final PersonalRanchoRepository personalRanchoRepository;

    public PersonalRancho ejecutar(String empleadoId, String ranchoId, String tenantId) {
        // Verificar que no exista una asignación activa previa
        personalRanchoRepository.obtenerAsignacionActiva(empleadoId, ranchoId, tenantId)
                .ifPresent(asignacion -> {
                    throw new IllegalArgumentException("El empleado ya tiene una asignación activa en este rancho");
                });

        PersonalRancho personalRancho = new PersonalRancho(
                UUID.randomUUID().toString(),
                empleadoId,
                ranchoId,
                LocalDateTime.now(),
                null,
                true
        );
        return personalRanchoRepository.guardar(personalRancho);
    }
}
