package com.vacapp.ventas.internal.domain.repository;

import com.vacapp.ventas.internal.domain.model.VentaGanado;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Puerto de salida para la persistencia de ventas de ganado. */
public interface VentaGanadoRepository {

    VentaGanado guardar(VentaGanado ventaGanado);

    Optional<VentaGanado> buscarPorId(UUID id, String tenantId);

    List<VentaGanado> listarPorTenant(String tenantId);
}
