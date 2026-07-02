package com.vacapp.ganado.internal.domain.repository;

import com.vacapp.ganado.internal.domain.model.CategoriaGanado;

import java.util.List;

/** Puerto de salida para la persistencia de categorías de ganado. */
public interface CategoriaGanadoRepository {

    CategoriaGanado guardar(CategoriaGanado categoria);

    List<CategoriaGanado> listarPorTenant(String tenantId);
}
