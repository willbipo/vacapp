package com.vacapp.historialClinico.internal.domain.model;

/** Se lanza cuando no se encuentra un registro de historial clínico. */
public class HistorialClinicoNoEncontradoException extends RuntimeException {
    public HistorialClinicoNoEncontradoException(String id) {
        super("No se encontró historial clínico con id: " + id);
    }
}
