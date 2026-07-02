package com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;

/** DTO de entrada para crear una categoría de ganado. */
public record CategoriaGanadoRequest(@NotBlank String nombre) {}
