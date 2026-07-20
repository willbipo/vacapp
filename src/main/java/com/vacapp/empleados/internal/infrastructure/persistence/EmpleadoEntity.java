package com.vacapp.empleados.internal.infrastructure.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad JPA para Empleado.
 */
@Data
@Table("empleados")
public class EmpleadoEntity {
    @Id
    private String id;

    @Column("tenant_id")
    private String tenantId;

    @Column("rancho_id")
    private String ranchoId;

    @Column("nombre")
    private String nombre;

    @Column("email")
    private String email;

    @Column("telefono")
    private String telefono;

    @Column("rol")
    private String rol;

    @Column("estado")
    private String estado;

    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
