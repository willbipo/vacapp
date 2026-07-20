package com.vacapp.vacunas.internal.application.usecases;

import com.vacapp.vacunas.internal.domain.model.Vacuna;
import com.vacapp.vacunas.internal.domain.repository.VacunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Caso de uso: listar todas las vacunas del tenant activo. */
@Service
@RequiredArgsConstructor
public class ListarVacunasUseCase {

    private final VacunaRepository vacunaRepository;

    @Transactional(readOnly = true)
    public List<Vacuna> ejecutar(String tenantId) {
        return vacunaRepository.listarPorTenant(tenantId);
    }

    @Transactional(readOnly = true)
    public List<Vacuna> ejecutar(String ranchoId, String tenantId) {
        return vacunaRepository.listarPorRancho(ranchoId, tenantId);
    }
}
