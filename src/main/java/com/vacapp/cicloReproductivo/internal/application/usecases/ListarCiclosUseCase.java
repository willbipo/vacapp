package com.vacapp.cicloReproductivo.internal.application.usecases;

import com.vacapp.cicloReproductivo.internal.domain.model.CicloReproductivo;
import com.vacapp.cicloReproductivo.internal.domain.repository.CicloReproductivoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/** Caso de uso: listar ciclos reproductivos. */
@Service
@RequiredArgsConstructor
public class ListarCiclosUseCase {

    private final CicloReproductivoRepository repository;

    @Transactional(readOnly = true)
    public List<CicloReproductivo> ejecutarActivos(String tenantId) {
        return repository.listarActivos(tenantId);
    }

    @Transactional(readOnly = true)
    public List<CicloReproductivo> ejecutarTodos(String tenantId) {
        return repository.listarPorTenant(tenantId);
    }

    @Transactional(readOnly = true)
    public List<CicloReproductivo> ejecutarPorVaca(UUID vacaId, String tenantId) {
        return repository.listarPorVaca(vacaId, tenantId);
    }
}
