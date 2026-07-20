package com.vacapp.vacunas.internal.domain.repository;

import com.vacapp.vacunas.internal.domain.model.Vacuna;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Puerto de salida para la persistencia de vacunas. */
public interface VacunaRepository {

    Vacuna guardar(Vacuna vacuna);

    Optional<Vacuna> buscarPorId(UUID id, String tenantId);

    List<Vacuna> listarPorTenant(String tenantId);

    List<Vacuna> listarPorRancho(String ranchoId, String tenantId);  // Filtrar por rancho (NULL = compartidas)

    void eliminar(UUID id, String tenantId);
}
