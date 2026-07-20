package com.vacapp.empleados.internal.domain.model;

import com.vacapp.usuarios.internal.domain.model.Rol;
import java.time.LocalDateTime;

/**
 * Entidad de dominio Empleado.
 * Representa un empleado del sistema.
 * Usa el enum Rol centralizado de usuarios (DOCTOR, WORKER).
 */
public class Empleado {
    private final String id;
    private final String tenantId;
    private String ranchoId;  // FK a ranchos.id - identifica el rancho del empleado
    private String nombre;
    private String email;
    private String telefono;
    private Rol rol;
    private Estado estado;
    private final LocalDateTime fechaRegistro;
    private LocalDateTime fechaActualizacion;

    public Empleado(
        String id,
        String tenantId,
        String ranchoId,
        String nombre,
        String email,
        String telefono,
        Rol rol,
        Estado estado,
        LocalDateTime fechaRegistro,
        LocalDateTime fechaActualizacion
    ) {
        this.id = id;
        this.tenantId = tenantId;
        this.ranchoId = ranchoId;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.rol = rol;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.fechaActualizacion = fechaActualizacion;
    }

    public String getId() { return id; }
    public String getTenantId() { return tenantId; }
    public String getRanchoId() { return ranchoId; }
    public void setRanchoId(String ranchoId) { this.ranchoId = ranchoId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public Rol getRol() { return rol; }
    public void setRol(Rol rol) { this.rol = rol; }
    public Estado getEstado() { return estado; }
    public void setEstado(Estado estado) { this.estado = estado; }
    public LocalDateTime getFechaRegistro() { return fechaRegistro; }
    public LocalDateTime getFechaActualizacion() { return fechaActualizacion; }
    public void setFechaActualizacion(LocalDateTime fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}
