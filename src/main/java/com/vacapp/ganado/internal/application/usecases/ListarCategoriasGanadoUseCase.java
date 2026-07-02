package com.vacapp.ganado.internal.application.usecases;

import com.vacapp.ganado.internal.domain.model.CategoriaGanado;
import com.vacapp.ganado.internal.domain.repository.CategoriaGanadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Caso de uso: listar las categorías de ganado de un tenant. */
@Service
@RequiredArgsConstructor
public class ListarCategoriasGanadoUseCase {

    private final CategoriaGanadoRepository categoriaGanadoRepository;

    @Transactional(readOnly = true)
    public List<CategoriaGanado> ejecutar(String tenantId) {
        return categoriaGanadoRepository.listarPorTenant(tenantId);
    }
}
