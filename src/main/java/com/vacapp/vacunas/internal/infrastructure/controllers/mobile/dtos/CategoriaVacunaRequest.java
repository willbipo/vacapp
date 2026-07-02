package com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;

/** DTO de entrada para crear una categoría de vacunas. */
public record CategoriaVacunaRequest(@NotBlank String nombre) {}
