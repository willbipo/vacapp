package com.vacapp.insumos.internal.domain.repository;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;

import java.util.List;

/** Puerto de salida para la persistencia de categorías de insumos. */
public interface CategoriaInsumoRepository {

    CategoriaInsumo guardar(CategoriaInsumo categoria);

    List<CategoriaInsumo> listarPorTenant(String tenantId);
}
