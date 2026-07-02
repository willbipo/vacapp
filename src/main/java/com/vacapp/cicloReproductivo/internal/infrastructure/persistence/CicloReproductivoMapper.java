package com.vacapp.cicloReproductivo.internal.infrastructure.persistence;

import com.vacapp.cicloReproductivo.internal.domain.model.CicloReproductivo;
import org.springframework.stereotype.Component;

/** Transforma entre {@link CicloReproductivo} y {@link CicloReproductivoEntidad}. */
@Component
public class CicloReproductivoMapper {

    public CicloReproductivo aDominio(CicloReproductivoEntidad e) {
        return CicloReproductivo.builder()
                .id(e.getId())
                .vacaId(e.getVacaId())
                .fechaInicio(e.getFechaInicio())
                .fechaEstimadaParto(e.getFechaEstimadaParto())
                .fechaPartoReal(e.getFechaPartoReal())
                .diasReposo(e.getDiasReposo())
                .fechaFinReposo(e.getFechaFinReposo())
                .estatus(e.getEstatus())
                .notas(e.getNotas())
                .tenantId(e.getTenantId())
                .build();
    }

    public CicloReproductivoEntidad aEntidad(CicloReproductivo c) {
        return CicloReproductivoEntidad.builder()
                .id(c.getId())
                .vacaId(c.getVacaId())
                .fechaInicio(c.getFechaInicio())
                .fechaEstimadaParto(c.getFechaEstimadaParto())
                .fechaPartoReal(c.getFechaPartoReal())
                .diasReposo(c.getDiasReposo())
                .fechaFinReposo(c.getFechaFinReposo())
                .estatus(c.getEstatus())
                .notas(c.getNotas())
                .tenantId(c.getTenantId())
                .build();
    }
}
