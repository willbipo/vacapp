package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.model.RanchoNoEncontradoException;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Caso de uso: Actualizar un rancho existente.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ActualizarRanchoUseCase {
    private final RanchoRepository ranchoRepository;

    public Rancho ejecutar(
        String id,
        String tenantId,
        String nombre,
        String descripcion,
        Double hectareas,
        String ubicacion
    ) {
        Rancho rancho = ranchoRepository.obtenerPorId(id, tenantId)
            .orElseThrow(() -> new RanchoNoEncontradoException(id));
        
        rancho.setNombre(nombre);
        rancho.setDescripcion(descripcion);
        rancho.setHectareas(hectareas);
        rancho.setUbicacion(ubicacion);
        rancho.setFechaActualizacion(LocalDateTime.now());
        
        ranchoRepository.actualizar(rancho);
        return rancho;
    }
}
