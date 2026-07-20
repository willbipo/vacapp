package com.vacapp.ranchos.internal.domain.repository;

import com.vacapp.ranchos.internal.domain.model.Potrero;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Contrato para persistencia de potreros.
 */
public interface PotreroRepository {
    Potrero guardar(Potrero potrero);
    Optional<Potrero> obtenerPorId(String id, String tenantId);
    List<Potrero> obtenerPorRancho(String ranchoId, String tenantId);
    List<Potrero> obtenerPorSeccion(String seccionId, String tenantId);
    Optional<Potrero> obtenerPorNombre(String nombre, String ranchoId, String tenantId);
    void actualizar(Potrero potrero);
    void eliminar(String id, String tenantId);
}
