package com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO Response para rancho con jerarquía completa (secciones y potreros).
 */
public record RanchoConJerarquiaResponse(
    String id,
    String nombre,
    String descripcion,
    Double hectareas,
    String ubicacion,
    Double hectareasEnUso,
    List<SeccionConPotrerosResponse> secciones,
    List<PotreroResponse> potrerosDirectos,
    LocalDateTime fechaRegistro,
    LocalDateTime fechaActualizacion
) {}
