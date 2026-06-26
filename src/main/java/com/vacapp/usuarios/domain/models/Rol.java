package com.vacapp.usuarios.domain.models;

/**
 * Roles disponibles en la plataforma Vacapp.
 * La jerarquía de permisos es: ADMIN > FARMER > DOCTOR > WORKER.
 */
public enum Rol {
    /** Administrador global del SaaS. */
    ADMIN,
    /** Dueño o administrador del rancho (tenant). */
    FARMER,
    /** Veterinario del rancho. */
    DOCTOR,
    /** Trabajador de campo. */
    WORKER
}
