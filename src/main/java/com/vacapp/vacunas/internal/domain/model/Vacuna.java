package com.vacapp.vacunas.internal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidad de negocio pura que representa una vacuna en el inventario.
 * Sin anotaciones JPA ni dependencias de framework.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vacuna {

    private UUID id;

    /** Nombre comercial o genérico de la vacuna. */
    private String nombre;

    /** Tipo/categoría de vacuna (dinámico, definido por el usuario). */
    private String tipo;

    /** Laboratorio o fabricante. */
    private String laboratorio;

    /** Descripción o indicaciones de uso. */
    private String descripcion;

    /** Dosis recomendada (ej. "2 ml"). */
    private String dosis;

    /** Vía de administración. */
    private ViaAdministracion viaAdministracion;

    /** Número de lote del fabricante. */
    private String lote;

    /** Fecha de caducidad del lote. */
    private LocalDate fechaCaducidad;

    /** Unidades disponibles en inventario. */
    private Integer stock;

    /** Unidad de medida (ej. "dosis", "ml", "frasco"). */
    private String unidadMedida;

    /** Temperatura de almacenamiento recomendada (ej. "2°C a 8°C"). */
    private String temperaturaAlmacenamiento;

    /** Intervalo de aplicación en días (ej. 180 = cada 6 meses). */
    private Integer intervaloDias;

    /** Identificador del tenant. */
    private String tenantId;
}
