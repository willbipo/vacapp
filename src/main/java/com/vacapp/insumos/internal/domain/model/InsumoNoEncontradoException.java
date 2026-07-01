package com.vacapp.insumos.internal.domain.model;

/** Excepción lanzada cuando no se encuentra un insumo con el ID indicado. */
public class InsumoNoEncontradoException extends RuntimeException {

    public InsumoNoEncontradoException(String id) {
        super("Insumo no encontrado con id: " + id);
    }
}
