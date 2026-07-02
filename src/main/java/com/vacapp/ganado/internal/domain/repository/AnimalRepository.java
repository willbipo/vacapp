package com.vacapp.ganado.internal.domain.repository;

import com.vacapp.ganado.internal.domain.model.Animal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Puerto de salida para la persistencia de animales. */
public interface AnimalRepository {

    /** Inserta un animal nuevo en la base de datos. */
    Animal guardar(Animal animal);

    /** Actualiza un animal existente en la base de datos. */
    Animal actualizar(Animal animal);

    Optional<Animal> buscarPorId(UUID id, String tenantId);

    Optional<Animal> buscarPorNumeroIdentificador(String numeroIdentificador, String tenantId);

    List<Animal> listarPorTenant(String tenantId);

    void eliminar(UUID id, String tenantId);

    void actualizarEstatus(UUID id, String estatus, String tenantId);

    void actualizarReposo(UUID id, java.time.LocalDate fechaInicio, java.time.LocalDate fechaFin, String tenantId);
}
