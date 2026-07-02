package com.vacapp.ventas.internal.infrastructure.persistence;

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

/**
 * Entidad Spring Data JDBC para la tabla {@code ventas_ganado}.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table("ventas_ganado")
public class VentaGanadoEntidad implements Persistable<UUID> {

    @Id
    private UUID id;

    @Column("arete_id")
    private String areteId;

    @Column("nombre_comprador")
    private String nombreComprador;

    private String ine;

    @Column("credencial_cedafod")
    private String credencialCedafod;

    @Column("guia_pdf")
    private String guiaPdf;

    @Column("fecha_venta")
    private LocalDate fechaVenta;

    @Column("tenant_id")
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
