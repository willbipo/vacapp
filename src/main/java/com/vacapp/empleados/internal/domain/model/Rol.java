package com.vacapp.empleados.internal.domain.model;

/**
 * Enumeración de roles de empleado.
 */
public enum Rol {
    ADMIN("Administrador"),
    OPERADOR("Operador"),
    VISUALIZADOR("Visualizador");

    private final String descripcion;

    Rol(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }
}
