package com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida con los datos de un animal. */
public record AnimalResponse(
    @Schema(description = "Identificador único del animal", example = "1f5644fb-7ae1-4f87-8b07-8a2036e28cd1")
        UUID id,
    @Schema(description = "Número identificador del animal", example = "MX-0001")
        String numeroIdentificador,
    @Schema(description = "Estatus del animal", example = "ACTIVO")
        Estatus estatus,
    @Schema(description = "Sexo del animal", example = "HEMBRA")
        Sexo sexo,
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
) {
    public static AnimalResponse desde(Animal animal) {
        return new AnimalResponse(
                animal.getId(),
                animal.getNumeroIdentificador(),
                animal.getEstatus(),
                animal.getSexo(),
                animal.getRaza(),
                animal.getFechaNacimiento(),
                animal.getMeses(),
                animal.getFechaAretado(),
                animal.getTipo(),
                animal.getAreteAnterior(),
                animal.getFolioReemo(),
                animal.getNota()
        );
    }
}
