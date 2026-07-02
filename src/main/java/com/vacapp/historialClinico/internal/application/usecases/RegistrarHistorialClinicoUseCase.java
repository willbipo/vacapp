package com.vacapp.historialClinico.internal.application.usecases;

import com.vacapp.historialClinico.internal.domain.model.HistorialClinico;
import com.vacapp.historialClinico.internal.domain.repository.HistorialClinicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/** Caso de uso: Registrar un evento clínico aplicado a un animal. */
@Service
@RequiredArgsConstructor
public class RegistrarHistorialClinicoUseCase {

    private final HistorialClinicoRepository historialClinicoRepository;

    /**
     * Datos necesarios para registrar un evento clínico.
     *
     * @param animalId        ID del animal.
     * @param vacunaId        ID de la vacuna del catálogo (null si es manual).
     * @param nombreVacuna    Nombre del tratamiento/vacuna.
     * @param dosis           Dosis aplicada.
     * @param viaAdministracion Vía de administración.
     * @param lote            Número de lote.
     * @param fechaAplicacion Fecha en que se aplicó.
     * @param proximaDosis    Próxima fecha de aplicación.
     * @param notas           Observaciones adicionales.
     * @param aplicadoPor     Nombre de quien aplicó el tratamiento.
     * @param tenantId        Identificador del tenant.
     */
    public record Comando(
            UUID animalId,
            UUID vacunaId,
            String nombreVacuna,
            String dosis,
            String viaAdministracion,
            String lote,
            LocalDate fechaAplicacion,
            LocalDate proximaDosis,
            String notas,
            String aplicadoPor,
            String tenantId
    ) {}

    @Transactional
    public HistorialClinico ejecutar(Comando cmd) {
        HistorialClinico historial = HistorialClinico.builder()
                .id(UUID.randomUUID())
                .animalId(cmd.animalId())
                .vacunaId(cmd.vacunaId())
                .nombreVacuna(cmd.nombreVacuna())
                .dosis(cmd.dosis())
                .viaAdministracion(cmd.viaAdministracion())
                .lote(cmd.lote())
                .fechaAplicacion(cmd.fechaAplicacion())
                .proximaDosis(cmd.proximaDosis())
                .notas(cmd.notas())
                .aplicadoPor(cmd.aplicadoPor())
                .tenantId(cmd.tenantId())
                .build();

        return historialClinicoRepository.guardar(historial);
    }
}
