package com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

/** DTO de entrada para registrar o actualizar un animal. */
public record AnimalRequest(
        @NotBlank String numeroIdentificador,
        @NotNull Estatus estatus,
        @NotNull Sexo sexo,
        String raza,
        LocalDate fechaNacimiento,
        Integer meses,
        LocalDate fechaAretado,
        Tipo tipo,
        String areteAnterior,
        String folioReemo,
        String nota,
        String categoria
) {}
