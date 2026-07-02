package com.vacapp.vacunas.internal.application.usecases;

import com.vacapp.vacunas.internal.domain.model.Vacuna;
import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import com.vacapp.vacunas.internal.domain.repository.VacunaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/** Caso de uso: registrar una nueva vacuna en el inventario. */
@Service
@RequiredArgsConstructor
public class RegistrarVacunaUseCase {

    private final VacunaRepository vacunaRepository;

    public record Comando(
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
        Vacuna vacuna = Vacuna.builder()
                .id(UUID.randomUUID())
                .nombre(cmd.nombre())
                .tipo(cmd.tipo())
                .laboratorio(cmd.laboratorio())
                .descripcion(cmd.descripcion())
                .dosis(cmd.dosis())
                .viaAdministracion(cmd.viaAdministracion())
                .lote(cmd.lote())
                .fechaCaducidad(cmd.fechaCaducidad())
                .stock(cmd.stock())
                .unidadMedida(cmd.unidadMedida())
                .temperaturaAlmacenamiento(cmd.temperaturaAlmacenamiento())
                .intervaloDias(cmd.intervaloDias())
                .tenantId(cmd.tenantId())
                .build();
        return vacunaRepository.guardar(vacuna);
    }
}
