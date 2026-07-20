package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.model.RanchoNoEncontradoException;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: Obtener un rancho por ID.
 */
@Service
@RequiredArgsConstructor
public class ObtenerRanchoUseCase {
    private final RanchoRepository ranchoRepository;

    public Rancho ejecutar(String id, String tenantId) {
        return ranchoRepository.obtenerPorId(id, tenantId)
            .orElseThrow(() -> new RanchoNoEncontradoException(id));
    }
}
