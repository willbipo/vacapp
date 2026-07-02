package com.vacapp.ventas.internal.infrastructure.persistence;

import com.vacapp.ventas.internal.domain.model.VentaGanado;
import org.springframework.stereotype.Component;

/** Convierte entre la entidad de dominio y la entidad de persistencia. */
@Component
public class VentaGanadoMapper {

    public VentaGanado aDominio(VentaGanadoEntidad entidad) {
        return VentaGanado.builder()
                .id(entidad.getId())
                .areteId(entidad.getAreteId())
                .nombreComprador(entidad.getNombreComprador())
                .ine(entidad.getIne())
                .credencialCedafod(entidad.getCredencialCedafod())
                .guiaPdf(entidad.getGuiaPdf())
                .fechaVenta(entidad.getFechaVenta())
                .tenantId(entidad.getTenantId())
                .build();
    }

    public VentaGanadoEntidad aEntidad(VentaGanado dominio) {
        return VentaGanadoEntidad.builder()
                .id(dominio.getId())
                .areteId(dominio.getAreteId())
                .nombreComprador(dominio.getNombreComprador())
                .ine(dominio.getIne())
                .credencialCedafod(dominio.getCredencialCedafod())
                .guiaPdf(dominio.getGuiaPdf())
                .fechaVenta(dominio.getFechaVenta())
                .tenantId(dominio.getTenantId())
                .build();
    }
}
