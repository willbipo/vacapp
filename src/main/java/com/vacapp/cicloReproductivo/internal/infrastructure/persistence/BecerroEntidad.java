package com.vacapp.cicloReproductivo.internal.infrastructure.persistence;

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

/** Entidad Spring Data JDBC para la tabla {@code becerros}. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("becerros")
public class BecerroEntidad implements Persistable<UUID> {

    @Id
    private UUID id;
    private String nombre;
    private LocalDate fechaNacimiento;
    private String sexo;
    private String nombrePadre;
    private String razaPadre;
    private String notas;
    private UUID madreId;
    private UUID cicloId;
    private String tenantId;

    @Transient
    @Builder.Default
    private boolean esNueva = true;

    @Override
    public boolean isNew() {
        return esNueva;
    }
}
