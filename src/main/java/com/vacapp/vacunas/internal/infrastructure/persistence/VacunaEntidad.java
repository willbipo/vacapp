package com.vacapp.vacunas.internal.infrastructure.persistence;

import com.vacapp.vacunas.internal.domain.model.TipoVacuna;
import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/** Clase de mapeo para la tabla {@code vacunas} en MySQL.
 * No es una entidad JPA — se mapea automáticamente con Spring Data JDBC. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacunaEntidad {

    private UUID id;
    private String nombre;
    private TipoVacuna tipo;
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
}
