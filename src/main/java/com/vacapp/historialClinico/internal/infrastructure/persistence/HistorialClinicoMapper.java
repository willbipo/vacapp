package com.vacapp.historialClinico.internal.infrastructure.persistence;

import com.vacapp.historialClinico.internal.domain.model.HistorialClinico;
import org.springframework.stereotype.Component;

/** Transforma entre {@link HistorialClinico} (dominio) y {@link HistorialClinicoEntidad} (JDBC). */
@Component
public class HistorialClinicoMapper {

    public HistorialClinico aDominio(HistorialClinicoEntidad e) {
        return HistorialClinico.builder()
                .id(e.getId())
                .animalId(e.getAnimalId())
                .vacunaId(e.getVacunaId())
                .nombreVacuna(e.getNombreVacuna())
                .dosis(e.getDosis())
                .viaAdministracion(e.getViaAdministracion())
                .lote(e.getLote())
                .fechaAplicacion(e.getFechaAplicacion())
                .proximaDosis(e.getProximaDosis())
                .notas(e.getNotas())
                .aplicadoPor(e.getAplicadoPor())
                .tenantId(e.getTenantId())
                .build();
    }

    public HistorialClinicoEntidad aEntidad(HistorialClinico h) {
        return HistorialClinicoEntidad.builder()
                .id(h.getId())
                .animalId(h.getAnimalId())
                .vacunaId(h.getVacunaId())
                .nombreVacuna(h.getNombreVacuna())
                .dosis(h.getDosis())
                .viaAdministracion(h.getViaAdministracion())
                .lote(h.getLote())
                .fechaAplicacion(h.getFechaAplicacion())
                .proximaDosis(h.getProximaDosis())
                .notas(h.getNotas())
                .aplicadoPor(h.getAplicadoPor())
                .tenantId(h.getTenantId())
                .build();
    }
}
