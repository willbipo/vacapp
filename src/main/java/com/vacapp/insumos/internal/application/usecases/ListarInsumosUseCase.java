package com.vacapp.insumos.internal.application.usecases;

import com.vacapp.insumos.internal.domain.model.Insumo;
import com.vacapp.insumos.internal.domain.repository.InsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Caso de uso: listar todos los insumos del tenant activo. */
@Service
@RequiredArgsConstructor
public class ListarInsumosUseCase {

    private final InsumoRepository insumoRepository;

    @Transactional(readOnly = true)
    public List<Insumo> ejecutar(String tenantId) {
        return insumoRepository.listarPorTenant(tenantId);
    }

    @Transactional(readOnly = true)
    public List<Insumo> ejecutar(String ranchoId, String tenantId) {
        return insumoRepository.listarPorRancho(ranchoId, tenantId);
    }
}
