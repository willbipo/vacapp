package com.vacapp.ranchos.internal.infrastructure.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad JDBC para Seccion.
 */
@Data
@Table("secciones")
public class SeccionEntity {
    @Id
    private String id;

    @Column("rancho_id")
    private String ranchoId;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column("tenant_id")
    private String tenantId;
}
