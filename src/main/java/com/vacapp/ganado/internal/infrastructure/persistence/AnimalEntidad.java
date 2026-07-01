package com.vacapp.ganado.internal.infrastructure.persistence;

import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
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

/**
 * Entidad JPA que representa la tabla {@code animales} en MySQL.
 */
@Entity
@Table(name = "animales")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnimalEntidad {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "numero_identificador", nullable = false, length = 100)
    private String numeroIdentificador;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Estatus estatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Sexo sexo;

    @Column(length = 100)
    private String raza;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private Integer meses;

    @Column(name = "fecha_aretado")
    private LocalDate fechaAretado;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Tipo tipo;

    @Column(name = "arete_anterior", length = 100)
    private String areteAnterior;

    @Column(name = "folio_reemo", length = 100)
    private String folioReemo;

    @Column(columnDefinition = "TEXT")
    private String nota;

    @Column(name = "tenant_id", nullable = false, length = 100)
    private String tenantId;
}
