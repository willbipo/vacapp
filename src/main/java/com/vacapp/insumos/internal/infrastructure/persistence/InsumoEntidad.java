package com.vacapp.insumos.internal.infrastructure.persistence;

import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;
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

import java.math.BigDecimal;
import java.util.UUID;

/** Entidad JPA que representa la tabla {@code insumos} en MySQL. */
@Entity
@Table(name = "insumos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InsumoEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private CategoriaInsumo categoria;

    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false, length = 20)
    private UnidadMedida unidadMedida;

    private Double cantidad;

    @Column(name = "cantidad_minima")
    private Double cantidadMinima;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(length = 150)
    private String proveedor;

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(length = 150)
    private String ubicacion;

    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;
}
