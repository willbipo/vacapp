package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.PersonalRancho;
import com.vacapp.ranchos.internal.domain.repository.PersonalRanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso: Listar el personal asignado a un rancho.
 */
@Service
@RequiredArgsConstructor
public class ListarPersonalPorRanchoUseCase {
    private final PersonalRanchoRepository personalRanchoRepository;

    public List<PersonalRancho> ejecutar(String ranchoId, String tenantId) {
        return personalRanchoRepository.obtenerPorRancho(ranchoId, tenantId);
    }
}
