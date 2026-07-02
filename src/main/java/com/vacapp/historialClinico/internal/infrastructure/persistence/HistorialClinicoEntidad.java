package com.vacapp.historialClinico.internal.infrastructure.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

/** Entidad Spring Data JDBC para la tabla {@code historial_clinico}. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("historial_clinico")
public class HistorialClinicoEntidad implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column("animal_id")
    private UUID animalId;

    @Column("vacuna_id")
    private UUID vacunaId;

    @Column("nombre_vacuna")
    private String nombreVacuna;

    private String dosis;

    @Column("via_administracion")
    private String viaAdministracion;

    private String lote;

    @Column("fecha_aplicacion")
    private LocalDate fechaAplicacion;

    @Column("proxima_dosis")
    private LocalDate proximaDosis;

    private String notas;

    @Column("aplicado_por")
    private String aplicadoPor;

    @Column("tenant_id")
    private String tenantId;

    @Transient
    @Builder.Default
    private boolean esNueva = true;

    @Override
    public boolean isNew() {
        return esNueva;
    }
}
