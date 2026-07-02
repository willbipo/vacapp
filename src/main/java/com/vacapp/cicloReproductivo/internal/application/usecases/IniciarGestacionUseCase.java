package com.vacapp.cicloReproductivo.internal.application.usecases;

import com.vacapp.cicloReproductivo.internal.domain.model.CicloReproductivo;
import com.vacapp.cicloReproductivo.internal.domain.repository.CicloReproductivoRepository;
import com.vacapp.ganado.GanadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/** Caso de uso: iniciar la gestación de una vaca. */
@Service
@RequiredArgsConstructor
public class IniciarGestacionUseCase {

    private final CicloReproductivoRepository repository;
    private final GanadoService ganadoService;

    public record Comando(
            UUID vacaId,
            LocalDate fechaInicio,
            String notas,
            String tenantId
    ) {}

    @Transactional
    public CicloReproductivo ejecutar(Comando cmd) {
        // La gestación bovina dura ~280 días (≈ 9 meses)
        LocalDate fechaEstimadaParto = cmd.fechaInicio().plusDays(280);

        CicloReproductivo ciclo = CicloReproductivo.builder()
                .id(UUID.randomUUID())
                .vacaId(cmd.vacaId())
                .fechaInicio(cmd.fechaInicio())
                .fechaEstimadaParto(fechaEstimadaParto)
                .estatus("EN_CURSO")
                .notas(cmd.notas())
                .tenantId(cmd.tenantId())
                .build();

        CicloReproductivo guardado = repository.guardar(ciclo);
        ganadoService.cambiarEstatus(cmd.vacaId(), "GESTACION", cmd.tenantId());
        return guardado;
    }
}
