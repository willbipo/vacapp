package com.vacapp.ranchos.internal.domain.model;

/**
 * Excepción de dominio cuando un rancho no existe.
 */
public class RanchoNoEncontradoException extends RuntimeException {
    public RanchoNoEncontradoException(String id) {
        super("Rancho con ID " + id + " no encontrado");
    }
}
