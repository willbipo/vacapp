package com.vacapp.cicloReproductivo.internal.application.usecases;

import com.vacapp.cicloReproductivo.internal.domain.model.Becerro;
import com.vacapp.cicloReproductivo.internal.domain.repository.BecerroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/** Caso de uso: listar becerros. */
@Service
@RequiredArgsConstructor
public class ListarBecerrosUseCase {

    private final BecerroRepository repository;

    @Transactional(readOnly = true)
    public List<Becerro> ejecutarPorMadre(UUID madreId, String tenantId) {
        return repository.listarPorMadre(madreId, tenantId);
    }

    @Transactional(readOnly = true)
    public List<Becerro> ejecutarTodos(String tenantId) {
        return repository.listarPorTenant(tenantId);
    }
}
