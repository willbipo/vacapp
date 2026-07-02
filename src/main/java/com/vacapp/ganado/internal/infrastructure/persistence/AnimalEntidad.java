package com.vacapp.ganado.internal.infrastructure.persistence;

import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
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

/**
 * Clase de mapeo para la tabla {@code animales} en MySQL.
 * No es una entidad JPA — se mapea automáticamente con Spring Data JDBC.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("animales")
public class AnimalEntidad implements Persistable<UUID> {

    @Id
    private UUID id;
    private String numeroIdentificador;
    private Estatus estatus;
    private Sexo sexo;
    private String raza;
    private LocalDate fechaNacimiento;
    private Integer meses;
    private LocalDate fechaAretado;
    private Tipo tipo;
    private String areteAnterior;
    private String folioReemo;
    private String nota;
    private String categoria;
    private LocalDate fechaInicioReposo;
    private LocalDate fechaFinReposo;
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
