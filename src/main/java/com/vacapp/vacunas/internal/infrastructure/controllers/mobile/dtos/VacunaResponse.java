package com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.vacunas.internal.domain.model.TipoVacuna;
import com.vacapp.vacunas.internal.domain.model.Vacuna;
import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida con los datos de una vacuna. */
public record VacunaResponse(
    @Schema(description = "Identificador único de la vacuna", example = "f49ad91d-08b9-4208-ae70-a85119f37089")
        UUID id,
    @Schema(description = "Nombre de la vacuna", example = "Brucelosis B19")
        String nombre,
    @Schema(description = "Tipo de vacuna", example = "PREVENTIVA")
        TipoVacuna tipo,
    @Schema(description = "Laboratorio fabricante", example = "Lab Vet")
        String laboratorio,
    @Schema(description = "Descripción de la vacuna", example = "Aplicación anual")
        String descripcion,
    @Schema(description = "Dosis recomendada", example = "2 ml")
        String dosis,
    @Schema(description = "Vía de administración", example = "INTRAMUSCULAR")
        ViaAdministracion viaAdministracion,
    @Schema(description = "Número de lote", example = "LT-2026-09")
        String lote,
    @Schema(description = "Fecha de caducidad", example = "2027-12-31")
        LocalDate fechaCaducidad,
    @Schema(description = "Stock disponible", example = "200")
        Integer stock,
    @Schema(description = "Unidad de medida", example = "FRASCO")
        String unidadMedida,
    @Schema(description = "Temperatura de almacenamiento", example = "2-8 C")
        String temperaturaAlmacenamiento,
    @Schema(description = "Intervalo entre dosis en días", example = "30")
        Integer intervaloDias
) {
    public static VacunaResponse desde(Vacuna v) {
        return new VacunaResponse(
                v.getId(), v.getNombre(), v.getTipo(), v.getLaboratorio(),
                v.getDescripcion(), v.getDosis(), v.getViaAdministracion(),
                v.getLote(), v.getFechaCaducidad(), v.getStock(),
                v.getUnidadMedida(), v.getTemperaturaAlmacenamiento(), v.getIntervaloDias()
        );
    }
}
