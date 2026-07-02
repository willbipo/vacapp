package com.vacapp.insumos.internal.application.usecases;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.repository.CategoriaInsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Caso de uso: listar las categorías de insumos de un tenant. */
@Service
@RequiredArgsConstructor
public class ListarCategoriasInsumoUseCase {

    private final CategoriaInsumoRepository categoriaInsumoRepository;

    @Transactional(readOnly = true)
    public List<CategoriaInsumo> ejecutar(String tenantId) {
        return categoriaInsumoRepository.listarPorTenant(tenantId);
    }
}
