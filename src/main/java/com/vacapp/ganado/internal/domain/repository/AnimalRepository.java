package com.vacapp.ganado.internal.domain.repository;

import com.vacapp.ganado.internal.domain.model.Animal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Puerto de salida para la persistencia de animales. */
public interface AnimalRepository {

    Animal guardar(Animal animal);

    Optional<Animal> buscarPorId(UUID id, String tenantId);

    List<Animal> listarPorTenant(String tenantId);

    void eliminar(UUID id, String tenantId);
}
