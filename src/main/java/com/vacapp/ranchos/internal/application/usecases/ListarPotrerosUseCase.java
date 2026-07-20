package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso: Listar potreros de un rancho.
 */
@Service
@RequiredArgsConstructor
public class ListarPotrerosUseCase {
    private final PotreroRepository potreroRepository;

    public List<Potrero> ejecutar(String ranchoId, String tenantId) {
        return potreroRepository.obtenerPorRancho(ranchoId, tenantId);
    }
}
