package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import com.vacapp.ranchos.internal.domain.model.PotreroNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso: Eliminar un potrero.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class EliminarPotreroUseCase {
    private final PotreroRepository potreroRepository;

    public void ejecutar(String id, String tenantId) {
        if (!potreroRepository.obtenerPorId(id, tenantId).isPresent()) {
            throw new PotreroNoEncontradoException("Potrero no encontrado con ID: " + id);
        }
        potreroRepository.eliminar(id, tenantId);
    }
}
