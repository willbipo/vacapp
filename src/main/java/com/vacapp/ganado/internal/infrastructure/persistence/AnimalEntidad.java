package com.vacapp.ganado.internal.infrastructure.persistence;

import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class AnimalEntidad {

    private UUID id;
    private String ranchoId;  // FK a ranchos.id
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
    private String tenantId;
}
