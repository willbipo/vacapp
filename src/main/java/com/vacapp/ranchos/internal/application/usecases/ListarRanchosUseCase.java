package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso: Listar ranchos del usuario autenticado.
 */
@Service
@RequiredArgsConstructor
public class ListarRanchosUseCase {
    private final RanchoRepository ranchoRepository;

    public List<Rancho> ejecutar(String userId, String tenantId) {
        return ranchoRepository.obtenerPorUsuario(userId, tenantId);
    }
}
