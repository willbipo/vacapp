package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.RanchoNoEncontradoException;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso: Eliminar un rancho.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EliminarRanchoUseCase {
    private final RanchoRepository ranchoRepository;

    public void ejecutar(String id, String tenantId) {
        ranchoRepository.obtenerPorId(id, tenantId)
            .orElseThrow(() -> new RanchoNoEncontradoException(id));
        
        ranchoRepository.eliminar(id, tenantId);
    }
}
