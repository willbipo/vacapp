package com.vacapp.insumos.internal.application.usecases;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.repository.CategoriaInsumoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Caso de uso: crear una nueva categoría de insumos. */
@Service
@RequiredArgsConstructor
public class CrearCategoriaInsumoUseCase {

    private final CategoriaInsumoRepository categoriaInsumoRepository;

    public record Comando(String nombre, String tenantId) {}

    @Transactional
    public CategoriaInsumo ejecutar(Comando cmd) {
        CategoriaInsumo categoria = CategoriaInsumo.builder()
                .id(UUID.randomUUID())
                .nombre(cmd.nombre())
                .tenantId(cmd.tenantId())
                .build();
        return categoriaInsumoRepository.guardar(categoria);
    }
}
