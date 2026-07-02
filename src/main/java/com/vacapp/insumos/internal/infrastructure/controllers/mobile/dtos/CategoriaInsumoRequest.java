package com.vacapp.insumos.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;

/** DTO de entrada para crear una categoría de insumos. */
public record CategoriaInsumoRequest(@NotBlank String nombre) {}
