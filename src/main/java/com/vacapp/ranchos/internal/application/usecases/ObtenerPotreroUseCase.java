package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import com.vacapp.ranchos.internal.domain.model.PotreroNoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Caso de uso: Obtener un potrero por ID.
 */
@Service
@RequiredArgsConstructor
public class ObtenerPotreroUseCase {
    private final PotreroRepository potreroRepository;

    public Potrero ejecutar(String id, String tenantId) {
        Optional<Potrero> potrero = potreroRepository.obtenerPorId(id, tenantId);
        return potrero.orElseThrow(() -> new PotreroNoEncontradoException("Potrero no encontrado con ID: " + id));
    }
}
