package com.vacapp.empleados.internal.domain.model;

/**
 * Enumeración de estados de empleado.
 */
public enum Estado {
    ACTIVO("Activo"),
    INACTIVO("Inactivo"),
    PENDIENTE("Pendiente de aceptación");

    private final String descripcion;

    Estado(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
