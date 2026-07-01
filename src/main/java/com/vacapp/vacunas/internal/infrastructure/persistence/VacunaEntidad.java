package com.vacapp.vacunas.internal.infrastructure.persistence;

import com.vacapp.vacunas.internal.domain.model.TipoVacuna;
import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/** Entidad JPA que representa la tabla {@code vacunas} en MySQL. */
@Entity
@Table(name = "vacunas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VacunaEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoVacuna tipo;

    @Column(length = 150)
    private String laboratorio;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 50)
    private String dosis;

    @Enumerated(EnumType.STRING)
    @Column(name = "via_administracion", length = 20)
    private ViaAdministracion viaAdministracion;

    @Column(length = 100)
    private String lote;

    @Column(name = "fecha_caducidad")
    private LocalDate fechaCaducidad;

    private Integer stock;

    @Column(name = "unidad_medida", length = 50)
    private String unidadMedida;

    @Column(name = "temperatura_almacenamiento", length = 50)
    private String temperaturaAlmacenamiento;

    /** Intervalo de aplicación en días. */
    @Column(name = "intervalo_dias")
    private Integer intervaloDias;

    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;
}
