package com.vacapp.ranchos.internal.domain.model;

import java.time.LocalDateTime;

/**
 * Entidad de dominio que representa la asignación de un empleado a un rancho.
 */
public class PersonalRancho {
    private final String id;
    private final String empleadoId;
    private final String ranchoId;
    private final LocalDateTime fechaAsignacion;
    private LocalDateTime fechaFinAsignacion;
    private final boolean activo;

    public PersonalRancho(String id, String empleadoId, String ranchoId, LocalDateTime fechaAsignacion, LocalDateTime fechaFinAsignacion, boolean activo) {
        this.id = id;
        this.empleadoId = empleadoId;
        this.ranchoId = ranchoId;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaFinAsignacion = fechaFinAsignacion;
        this.activo = activo;
    }

    public String getId() { return id; }
    public String getEmpleadoId() { return empleadoId; }
    public String getRanchoId() { return ranchoId; }
    public LocalDateTime getFechaAsignacion() { return fechaAsignacion; }
    public LocalDateTime getFechaFinAsignacion() { return fechaFinAsignacion; }
    public boolean isActivo() { return activo; }

    public void setFechaFinAsignacion(LocalDateTime fechaFinAsignacion) {
        this.fechaFinAsignacion = fechaFinAsignacion;
    }
}
