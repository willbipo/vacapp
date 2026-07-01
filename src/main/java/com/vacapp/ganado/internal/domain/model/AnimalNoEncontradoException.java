package com.vacapp.ganado.internal.domain.model;

/** Excepción lanzada cuando no se encuentra un animal con el ID indicado. */
public class AnimalNoEncontradoException extends RuntimeException {

    public AnimalNoEncontradoException(String id) {
        super("Animal no encontrado con id: " + id);
    }
}
