package com.vacapp.ranchos.internal.domain.repository;

import com.vacapp.ranchos.internal.domain.model.Seccion;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Contrato para persistencia de secciones.
 */
public interface SeccionRepository {
    Seccion guardar(Seccion seccion);
    Optional<Seccion> obtenerPorId(String id, String tenantId);
    List<Seccion> obtenerPorRancho(String ranchoId, String tenantId);
    Optional<Seccion> obtenerPorNombre(String nombre, String ranchoId, String tenantId);
    void actualizar(Seccion seccion);
    void eliminar(String id, String tenantId);
}
