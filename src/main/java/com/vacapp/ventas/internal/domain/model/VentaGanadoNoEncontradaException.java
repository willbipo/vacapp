package com.vacapp.ventas.internal.domain.model;

/** Se lanza cuando no se encuentra una venta de ganado por su ID. */
public class VentaGanadoNoEncontradaException extends RuntimeException {

    public VentaGanadoNoEncontradaException(String id) {
        super("Venta de ganado no encontrada con ID: " + id);
    }
}
