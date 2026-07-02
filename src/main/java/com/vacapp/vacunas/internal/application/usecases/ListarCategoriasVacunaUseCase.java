package com.vacapp.vacunas.internal.application.usecases;

import com.vacapp.vacunas.internal.domain.model.CategoriaVacuna;
import com.vacapp.vacunas.internal.domain.repository.CategoriaVacunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Caso de uso: listar las categorías de vacunas de un tenant. */
@Service
@RequiredArgsConstructor
public class ListarCategoriasVacunaUseCase {

    private final CategoriaVacunaRepository categoriaVacunaRepository;

    @Transactional(readOnly = true)
    public List<CategoriaVacuna> ejecutar(String tenantId) {
        return categoriaVacunaRepository.listarPorTenant(tenantId);
    }
}
