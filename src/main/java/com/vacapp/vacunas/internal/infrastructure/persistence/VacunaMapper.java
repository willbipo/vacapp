package com.vacapp.vacunas.internal.infrastructure.persistence;

import com.vacapp.vacunas.internal.domain.model.Vacuna;
import org.springframework.stereotype.Component;

/** Mapea entre {@link Vacuna} (dominio) y {@link VacunaEntidad} (JPA). */
@Component
public class VacunaMapper {

    public VacunaEntidad aEntidad(Vacuna vacuna) {
        return VacunaEntidad.builder()
                .id(vacuna.getId())
                .nombre(vacuna.getNombre())
                .tipo(vacuna.getTipo())
                .laboratorio(vacuna.getLaboratorio())
                .descripcion(vacuna.getDescripcion())
                .dosis(vacuna.getDosis())
                .viaAdministracion(vacuna.getViaAdministracion())
                .lote(vacuna.getLote())
                .fechaCaducidad(vacuna.getFechaCaducidad())
                .stock(vacuna.getStock())
                .unidadMedida(vacuna.getUnidadMedida())
                .temperaturaAlmacenamiento(vacuna.getTemperaturaAlmacenamiento())
                .intervaloDias(vacuna.getIntervaloDias())
                .tenantId(vacuna.getTenantId())
                .build();
    }

    public Vacuna aDominio(VacunaEntidad entidad) {
        return Vacuna.builder()
                .id(entidad.getId())
                .nombre(entidad.getNombre())
                .tipo(entidad.getTipo())
                .laboratorio(entidad.getLaboratorio())
                .descripcion(entidad.getDescripcion())
                .dosis(entidad.getDosis())
                .viaAdministracion(entidad.getViaAdministracion())
                .lote(entidad.getLote())
                .fechaCaducidad(entidad.getFechaCaducidad())
                .stock(entidad.getStock())
                .unidadMedida(entidad.getUnidadMedida())
                .temperaturaAlmacenamiento(entidad.getTemperaturaAlmacenamiento())
                .intervaloDias(entidad.getIntervaloDias())
                .tenantId(entidad.getTenantId())
                .build();
    }
}
