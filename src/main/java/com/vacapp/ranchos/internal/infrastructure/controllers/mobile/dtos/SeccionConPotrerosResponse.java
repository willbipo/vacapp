package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import java.util.List;

/**
 * DTO Response para sección con sus potreros.
 */
public record SeccionConPotrerosResponse(
    String id,
    String ranchoId,
    String nombre,
    Double hectareasTotales,
    List<PotreroResponse> potreros
) {}
