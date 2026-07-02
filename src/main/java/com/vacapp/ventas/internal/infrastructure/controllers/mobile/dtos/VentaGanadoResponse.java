package com.vacapp.ventas.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.ventas.internal.domain.model.VentaGanado;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida para una venta de ganado. */
public record VentaGanadoResponse(
    UUID id,
    String areteId,
    String nombreComprador,
    String ine,
    String credencialCedafod,
    String guiaPdf,
    LocalDate fechaVenta,
    String tenantId
) {
    public static VentaGanadoResponse desde(VentaGanado dominio) {
        return new VentaGanadoResponse(
            dominio.getId(),
            dominio.getAreteId(),
            dominio.getNombreComprador(),
            dominio.getIne(),
            dominio.getCredencialCedafod(),
            dominio.getGuiaPdf(),
            dominio.getFechaVenta(),
            dominio.getTenantId()
        );
    }
}
