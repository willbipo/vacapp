package com.vacapp.calendario.internal.infrastructure.persistence;

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

/** Entidad Spring Data JDBC para la tabla {@code eventos_calendario}. */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("eventos_calendario")
public class EventoCalendarioEntidad implements Persistable<UUID> {

    @Id
    private UUID id;
    private String titulo;
    private String descripcion;
    private LocalDate fecha;
    private String tipo;
    private String tenantId;

    @Transient
    @Builder.Default
    private boolean esNueva = true;

    @Override
    public boolean isNew() {
        return esNueva;
    }
}
