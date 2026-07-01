package com.vacapp.ganado.internal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidad de negocio pura que representa un animal del inventario.
 * Sin anotaciones JPA ni dependencias de framework.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Animal {

    private UUID id;

    /** Número de identificador (arete) del animal. */
    private String numeroIdentificador;

    /** Estatus actual del animal. */
    private Estatus estatus;

    /** Sexo del animal. */
    private Sexo sexo;

    /** Raza del animal. */
    private String raza;

    /** Fecha de nacimiento. */
    private LocalDate fechaNacimiento;

    /** Edad en meses (calculado o capturado). */
    private Integer meses;

    /** Fecha en que se colocó el arete. */
    private LocalDate fechaAretado;

    /** Tipo de movimiento/destino. */
    private Tipo tipo;

    /** Número de arete anterior, si aplica. */
    private String areteAnterior;

    /** Folio REEMO asignado. */
    private String folioReemo;

    /** Nota u observación adicional. */
    private String nota;

    /** Identificador del tenant al que pertenece este animal. */
    private String tenantId;
}
