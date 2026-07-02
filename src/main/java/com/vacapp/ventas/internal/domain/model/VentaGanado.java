package com.vacapp.ventas.internal.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Entidad de negocio pura que representa una venta de ganado.
 * Sin anotaciones JPA ni dependencias de framework.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaGanado {

    private UUID id;

    /** ID del arete de la vaca vendida. */
    private String areteId;

    /** Nombre completo del comprador. */
    private String nombreComprador;

    /** Número de INE del comprador. */
    private String ine;

    /** Nombre del archivo PDF de la credencial CEDAFOD. */
    private String credencialCedafod;

    /** Nombre del archivo PDF de la guía de venta. */
    private String guiaPdf;

    /** Fecha en que se realizó la venta. */
    private LocalDate fechaVenta;

    private String tenantId;
}
