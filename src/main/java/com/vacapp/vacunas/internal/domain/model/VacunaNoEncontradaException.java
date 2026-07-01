package com.vacapp.vacunas.internal.domain.model;

/** Excepción lanzada cuando no se encuentra una vacuna con el ID indicado. */
public class VacunaNoEncontradaException extends RuntimeException {

    public VacunaNoEncontradaException(String id) {
        super("Vacuna no encontrada con id: " + id);
    }
}
