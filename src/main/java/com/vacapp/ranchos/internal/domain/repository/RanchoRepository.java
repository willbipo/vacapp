package com.vacapp.ranchos.internal.domain.repository;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Contrato para persistencia de ranchos.
 */
public interface RanchoRepository {
    Rancho guardar(Rancho rancho);
    Optional<Rancho> obtenerPorId(String id, String tenantId);
    List<Rancho> obtenerTodosPorTenant(String tenantId);
    List<Rancho> obtenerPorUsuario(String userId, String tenantId);
    Optional<Rancho> obtenerPorNombre(String nombre, String tenantId);
    void actualizar(Rancho rancho);
    void eliminar(String id, String tenantId);
}
