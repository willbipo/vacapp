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

/** Entidad Spring Data JDBC para la tabla {@code ciclos_reproductivos}. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("ciclos_reproductivos")
public class CicloReproductivoEntidad implements Persistable<UUID> {

    @Id
    private UUID id;
    private UUID vacaId;
    private LocalDate fechaInicio;
    private LocalDate fechaEstimadaParto;
    private LocalDate fechaPartoReal;
    private Integer diasReposo;
    private LocalDate fechaFinReposo;
    private String estatus;
    private String notas;
    private String tenantId;

    @Transient
    @Builder.Default
    private boolean esNueva = true;

    @Override
    public boolean isNew() {
        return esNueva;
    }
}
