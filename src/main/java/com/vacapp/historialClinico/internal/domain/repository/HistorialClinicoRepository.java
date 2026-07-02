package com.vacapp.historialClinico.internal.domain.repository;

import com.vacapp.historialClinico.internal.domain.model.HistorialClinico;

import java.util.List;
import java.util.UUID;

/**
 * Puerto de salida para la persistencia de historial clínico.
 */
public interface HistorialClinicoRepository {

    /** Persiste un nuevo evento clínico. */
    HistorialClinico guardar(HistorialClinico historial);

    /** Lista el historial de un animal específico ordenado por fecha descendente. */
    List<HistorialClinico> listarPorAnimal(UUID animalId, String tenantId);

    /** Lista todo el historial del tenant ordenado por fecha descendente. */
    List<HistorialClinico> listarPorTenant(String tenantId);

    /** Lista los registros que tienen próxima dosis programada, ordenados por fecha ascendente. */
    List<HistorialClinico> listarProximasDosis(String tenantId);
}
