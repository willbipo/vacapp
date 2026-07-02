package com.vacapp.historialClinico.internal.application.usecases;

import com.vacapp.historialClinico.internal.domain.model.HistorialClinico;
import com.vacapp.historialClinico.internal.domain.repository.HistorialClinicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/** Caso de uso: Listar el historial clínico de un animal. */
@Service
@RequiredArgsConstructor
public class ListarHistorialPorAnimalUseCase {

    private final HistorialClinicoRepository historialClinicoRepository;

    @Transactional(readOnly = true)
    public List<HistorialClinico> ejecutar(UUID animalId, String tenantId) {
        return historialClinicoRepository.listarPorAnimal(animalId, tenantId);
    }
}
