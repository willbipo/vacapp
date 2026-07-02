package com.vacapp.vacunas.internal.application.usecases;

import com.vacapp.vacunas.internal.domain.model.CategoriaVacuna;
import com.vacapp.vacunas.internal.domain.repository.CategoriaVacunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/** Caso de uso: crear una nueva categoría de vacunas. */
@Service
@RequiredArgsConstructor
public class CrearCategoriaVacunaUseCase {

    private final CategoriaVacunaRepository categoriaVacunaRepository;

    public record Comando(String nombre, String tenantId) {}

    @Transactional
    public CategoriaVacuna ejecutar(Comando cmd) {
        CategoriaVacuna categoria = CategoriaVacuna.builder()
                .id(UUID.randomUUID())
                .nombre(cmd.nombre())
                .tenantId(cmd.tenantId())
                .build();
        return categoriaVacunaRepository.guardar(categoria);
    }
}
