package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.PersonalRancho;
import com.vacapp.ranchos.internal.domain.model.PersonalRanchoNoEncontradoException;
import com.vacapp.ranchos.internal.domain.repository.PersonalRanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Caso de uso: Desasignar un empleado de un rancho.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DesasignarPersonalDeRanchoUseCase {
    private final PersonalRanchoRepository personalRanchoRepository;

    public void ejecutar(String empleadoId, String ranchoId, String tenantId) {
        Optional<PersonalRancho> asignacion = personalRanchoRepository.obtenerAsignacionActiva(empleadoId, ranchoId, tenantId);
        
        if (asignacion.isEmpty()) {
            throw new PersonalRanchoNoEncontradoException("No existe una asignación activa para este empleado en el rancho");
        }
        
        PersonalRancho personalRancho = asignacion.get();
        personalRancho.setFechaFinAsignacion(LocalDateTime.now());
        personalRanchoRepository.actualizar(personalRancho);
    }
}
