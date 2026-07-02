package com.vacapp.cicloReproductivo.internal.domain.repository;

import com.vacapp.cicloReproductivo.internal.domain.model.Becerro;

import java.util.List;
import java.util.UUID;

/** Puerto de salida para becerros. */
public interface BecerroRepository {

    Becerro guardar(Becerro becerro);

    List<Becerro> listarPorMadre(UUID madreId, String tenantId);

    List<Becerro> listarPorTenant(String tenantId);
}
