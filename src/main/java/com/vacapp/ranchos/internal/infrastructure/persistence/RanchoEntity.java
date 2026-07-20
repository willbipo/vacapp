package com.vacapp.ranchos.internal.infrastructure.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad JDBC para Rancho.
 */
@Data
@Table("ranchos")
public class RanchoEntity {
    @Id
    private String id;

    @Column("tenant_id")
    private String tenantId;

    @Column("user_id")
    private String userId;

    @Column("nombre")
    private String nombre;

    @Column("descripcion")
    private String descripcion;

    @Column("hectareas")
    private Double hectareas;

    @Column("ubicacion")
    private String ubicacion;

    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
}
