package com.vacapp.empleados.internal.domain.repository;

import com.vacapp.empleados.internal.domain.model.Empleado;
import java.util.List;
import java.util.Optional;

/**
 * Puerto de salida: Contrato para la persistencia de empleados.
 */
public interface EmpleadoRepository {
    Empleado guardar(Empleado empleado);
    Optional<Empleado> obtenerPorId(String id, String tenantId);
    List<Empleado> obtenerTodos(String tenantId);
    List<Empleado> obtenerPorRancho(String ranchoId, String tenantId);  // Filtrar por rancho
    Optional<Empleado> obtenerPorEmail(String email, String tenantId);
    void actualizar(Empleado empleado);
}
