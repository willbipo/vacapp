package com.vacapp.vacunas.internal.infrastructure.persistence;

import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;
import java.util.UUID;

/** Clase de mapeo para la tabla {@code vacunas} en MySQL.
 * No es una entidad JPA — se mapea automáticamente con Spring Data JDBC. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("vacunas")
public class VacunaEntidad implements Persistable<UUID> {

    @Id
    private UUID id;
    private String nombre;
    private String tipo;
    private String laboratorio;
    private String descripcion;
    private String dosis;
    private ViaAdministracion viaAdministracion;
    private String lote;
    private LocalDate fechaCaducidad;
    private Integer stock;
    private String unidadMedida;
    private String temperaturaAlmacenamiento;
    /** Intervalo de aplicación en días. */
    private Integer intervaloDias;
    private String tenantId;

    /** Siempre es nueva: el id se genera en la capa de aplicación. */
    @Transient
    @Builder.Default
    private boolean esNueva = true;

    @Override
    public boolean isNew() {
        return esNueva;
    }
}
