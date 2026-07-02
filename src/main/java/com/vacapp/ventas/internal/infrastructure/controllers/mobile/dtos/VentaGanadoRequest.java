package com.vacapp.ventas.internal.infrastructure.controllers.mobile.dtos;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

/** DTO de entrada para registrar una venta de ganado. */
public record VentaGanadoRequest(

    @NotBlank(message = "El ID del arete es obligatorio")
    String areteId,

    @NotBlank(message = "El nombre del comprador es obligatorio")
    String nombreComprador,

    @NotBlank(message = "El INE del comprador es obligatorio")
    String ine,

    /** Nombre del archivo PDF de credencial CEDAFOD (guardado previamente). */
    String credencialCedafod,

    /** Nombre del archivo PDF de la guía de venta (guardado previamente). */
    String guiaPdf,

    LocalDate fechaVenta
) {}
