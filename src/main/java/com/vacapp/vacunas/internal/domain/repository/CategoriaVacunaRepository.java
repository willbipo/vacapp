package com.vacapp.vacunas.internal.domain.repository;

import com.vacapp.vacunas.internal.domain.model.CategoriaVacuna;

import java.util.List;

/** Puerto de salida para la persistencia de categorías de vacunas. */
public interface CategoriaVacunaRepository {

    CategoriaVacuna guardar(CategoriaVacuna categoria);

    List<CategoriaVacuna> listarPorTenant(String tenantId);
}
