package com.vacapp.ranchos.internal.infrastructure.persistence;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * Entidad JDBC para PersonalRancho.
 */
@Data
@Table("empleados_ranchos")
public class PersonalRanchoEntity {
    @Id
    private String id;

    @Column("empleado_id")
    private String empleadoId;

    @Column("rancho_id")
    private String ranchoId;

    @Column("fecha_asignacion")
    private LocalDateTime fechaAsignacion;

    @Column("fecha_fin_asignacion")
    private LocalDateTime fechaFinAsignacion;

    @Column("activo")
    private Boolean activo;

    @Column("tenant_id")
    private String tenantId;
}
