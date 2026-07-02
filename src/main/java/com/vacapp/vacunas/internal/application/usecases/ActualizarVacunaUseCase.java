package com.vacapp.vacunas.internal.application.usecases;

import com.vacapp.vacunas.internal.domain.model.Vacuna;
import com.vacapp.vacunas.internal.domain.model.VacunaNoEncontradaException;
import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import com.vacapp.vacunas.internal.domain.repository.VacunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/** Caso de uso: actualizar los datos de una vacuna existente. */
@Service
@RequiredArgsConstructor
public class ActualizarVacunaUseCase {

    private final VacunaRepository vacunaRepository;

    public record Comando(
        UUID id,
        String nombre,
        String tipo,
        String laboratorio,
        String descripcion,
        String dosis,
        ViaAdministracion viaAdministracion,
        String lote,
        LocalDate fechaCaducidad,
        Integer stock,
        String unidadMedida,
        String temperaturaAlmacenamiento,
        Integer intervaloDias,
        String tenantId
    ) {}

    @Transactional
    public Vacuna ejecutar(Comando cmd) {
        Vacuna vacuna = vacunaRepository.buscarPorId(cmd.id(), cmd.tenantId())
                .orElseThrow(() -> new VacunaNoEncontradaException(cmd.id().toString()));

        vacuna.setNombre(cmd.nombre());
        vacuna.setTipo(cmd.tipo());
        vacuna.setLaboratorio(cmd.laboratorio());
        vacuna.setDescripcion(cmd.descripcion());
        vacuna.setDosis(cmd.dosis());
        vacuna.setViaAdministracion(cmd.viaAdministracion());
        vacuna.setLote(cmd.lote());
        vacuna.setFechaCaducidad(cmd.fechaCaducidad());
        vacuna.setStock(cmd.stock());
        vacuna.setUnidadMedida(cmd.unidadMedida());
        vacuna.setTemperaturaAlmacenamiento(cmd.temperaturaAlmacenamiento());
        vacuna.setIntervaloDias(cmd.intervaloDias());

        return vacunaRepository.guardar(vacuna);
    }
}
