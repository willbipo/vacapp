package com.vacapp.ranchos.internal.infrastructure.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad JDBC para Potrero.
 */
@Data
@Table("potreros")
public class PotreroEntity {
    @Id
    private String id;

    @Column("rancho_id")
    private String ranchoId;

    @Column("seccion_id")
    private String seccionId;

    @Column("nombre")
    private String nombre;

    @Column("hectareas")
    private Double hectareas;

    @Column("tipo_pasto")
    private String tipoPasto;

    @Column("fecha_registro")
    private LocalDateTime fechaRegistro;

    @Column("fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column("tenant_id")
    private String tenantId;
}
