package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.domain.repository.SeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso: Listar secciones de un rancho.
 */
@Service
@RequiredArgsConstructor
public class ListarSeccionesUseCase {
    private final SeccionRepository seccionRepository;

    public List<Seccion> ejecutar(String ranchoId, String tenantId) {
        return seccionRepository.obtenerPorRancho(ranchoId, tenantId);
    }
}
