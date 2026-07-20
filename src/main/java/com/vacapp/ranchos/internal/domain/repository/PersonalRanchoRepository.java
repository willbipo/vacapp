package com.vacapp.ranchos.internal.domain.repository;

import com.vacapp.ranchos.internal.domain.model.PersonalRancho;

import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Contrato para persistencia de asignaciones de personal a ranchos.
 */
public interface PersonalRanchoRepository {
    PersonalRancho guardar(PersonalRancho personalRancho);
    Optional<PersonalRancho> obtenerPorId(String id, String tenantId);
    List<PersonalRancho> obtenerPorRancho(String ranchoId, String tenantId);
    List<PersonalRancho> obtenerPorEmpleado(String empleadoId, String tenantId);
    Optional<PersonalRancho> obtenerAsignacionActiva(String empleadoId, String ranchoId, String tenantId);
    void actualizar(PersonalRancho personalRancho);
    void eliminar(String id, String tenantId);
}
