package com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/** DTO de entrada para registrar o actualizar una vacuna. */
public record VacunaRequest(
        @NotBlank String nombre,
        @NotBlank String tipo,
        String laboratorio,
        String descripcion,
        String dosis,
        ViaAdministracion viaAdministracion,
        String lote,
        LocalDate fechaCaducidad,
        Integer stock,
        String unidadMedida,
        String temperaturaAlmacenamiento,
        Integer intervaloDias
) {}
