package com.vacapp.empleados.internal.domain.model;

/**
 * Excepción lanzada cuando un empleado no es encontrado.
 */
public class EmpleadoNoEncontradoException extends RuntimeException {
    public EmpleadoNoEncontradoException(String id) {
        super("Empleado con ID " + id + " no encontrado");
    }
}
