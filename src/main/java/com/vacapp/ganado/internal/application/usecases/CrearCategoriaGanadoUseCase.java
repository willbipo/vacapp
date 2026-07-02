package com.vacapp.ganado.internal.application.usecases;

import com.vacapp.ganado.internal.domain.model.CategoriaGanado;
import com.vacapp.ganado.internal.domain.repository.CategoriaGanadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Caso de uso: crear una nueva categoría de ganado. */
@Service
@RequiredArgsConstructor
public class CrearCategoriaGanadoUseCase {

    private final CategoriaGanadoRepository categoriaGanadoRepository;

    public record Comando(String nombre, String tenantId) {}

    @Transactional
    public CategoriaGanado ejecutar(Comando cmd) {
        CategoriaGanado categoria = CategoriaGanado.builder()
                .id(UUID.randomUUID())
                .nombre(cmd.nombre())
                .tenantId(cmd.tenantId())
                .build();
        return categoriaGanadoRepository.guardar(categoria);
    }
}
