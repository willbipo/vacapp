package com.vacapp.insumos.internal.domain.repository;

import com.vacapp.insumos.internal.domain.model.Insumo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Puerto de salida para la persistencia de insumos. */
public interface InsumoRepository {

    Insumo guardar(Insumo insumo);

    Optional<Insumo> buscarPorId(UUID id, String tenantId);

    List<Insumo> listarPorTenant(String tenantId);

    void eliminar(UUID id, String tenantId);
}
