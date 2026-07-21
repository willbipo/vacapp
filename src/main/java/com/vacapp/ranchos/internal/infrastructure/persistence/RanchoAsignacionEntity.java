package com.vacapp.ranchos.internal.infrastructure.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad JDBC para asignaciones de ranchos a usuarios.
 */
@Data
@Table("rancho_asignaciones")
public class RanchoAsignacionEntity {
    @Id
    private String id;

    @Column("usuario_id")
    private String usuarioId;

    @Column("rancho_id")
    private String ranchoId;

    @Column("fecha_asignacion")
    private LocalDateTime fechaAsignacion;

    @Column("tenant_id")
    private String tenantId;
}
