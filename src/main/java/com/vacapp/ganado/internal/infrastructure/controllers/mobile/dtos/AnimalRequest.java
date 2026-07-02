package com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/** DTO de entrada para registrar o actualizar un animal. */
public record AnimalRequest(
        @Schema(description = "Número identificador del animal", example = "MX-0001")
        @NotBlank String numeroIdentificador,
        @Schema(description = "Estatus del animal", example = "ACTIVO")
        @NotNull Estatus estatus,
        @Schema(description = "Sexo del animal", example = "HEMBRA")
        @NotNull Sexo sexo,
        @Schema(description = "Raza del animal", example = "Holstein")
        String raza,
        @Schema(description = "Fecha de nacimiento", example = "2024-01-15")
        LocalDate fechaNacimiento,
        @Schema(description = "Edad en meses", example = "18")
        Integer meses,
        @Schema(description = "Fecha de aretado", example = "2024-02-01")
        LocalDate fechaAretado,
        @Schema(description = "Tipo de animal", example = "VACA")
        Tipo tipo,
        @Schema(description = "Arete anterior", example = "AR-900")
        String areteAnterior,
        @Schema(description = "Folio de reemplacado", example = "FR-2026-001")
        String folioReemo,
        @Schema(description = "Notas adicionales", example = "Animal en buen estado")
        String nota
) {}
