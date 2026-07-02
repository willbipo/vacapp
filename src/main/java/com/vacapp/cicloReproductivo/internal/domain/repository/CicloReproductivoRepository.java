package com.vacapp.cicloReproductivo.internal.domain.repository;

import com.vacapp.cicloReproductivo.internal.domain.model.CicloReproductivo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Puerto de salida para ciclos reproductivos. */
public interface CicloReproductivoRepository {

    CicloReproductivo guardar(CicloReproductivo ciclo);

    Optional<CicloReproductivo> buscarPorId(UUID id, String tenantId);

    List<CicloReproductivo> listarPorTenant(String tenantId);

    List<CicloReproductivo> listarPorVaca(UUID vacaId, String tenantId);

    /** Ciclos con estatus EN_CURSO del tenant. */
    List<CicloReproductivo> listarActivos(String tenantId);

    /** Ciclos en reposo cuya fecha de fin ya venció (para el scheduler). */
    List<CicloReproductivo> listarReposoVencido(LocalDate hoy);
}
