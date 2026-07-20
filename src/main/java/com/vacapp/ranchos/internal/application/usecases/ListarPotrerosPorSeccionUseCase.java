package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso: Listar potreros de una sección.
 */
@Service
@RequiredArgsConstructor
public class ListarPotrerosPorSeccionUseCase {
    private final PotreroRepository potreroRepository;

    public List<Potrero> ejecutar(String seccionId, String tenantId) {
        return potreroRepository.obtenerPorSeccion(seccionId, tenantId);
    }
}
