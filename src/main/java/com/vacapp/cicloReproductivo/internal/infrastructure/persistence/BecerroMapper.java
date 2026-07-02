package com.vacapp.cicloReproductivo.internal.infrastructure.persistence;

import com.vacapp.cicloReproductivo.internal.domain.model.Becerro;
import org.springframework.stereotype.Component;

/** Transforma entre {@link Becerro} y {@link BecerroEntidad}. */
@Component
public class BecerroMapper {

    public Becerro aDominio(BecerroEntidad e) {
        return Becerro.builder()
                .id(e.getId())
                .nombre(e.getNombre())
                .fechaNacimiento(e.getFechaNacimiento())
                .sexo(e.getSexo())
                .nombrePadre(e.getNombrePadre())
                .razaPadre(e.getRazaPadre())
                .notas(e.getNotas())
                .madreId(e.getMadreId())
                .cicloId(e.getCicloId())
                .tenantId(e.getTenantId())
                .build();
    }

    public BecerroEntidad aEntidad(Becerro b) {
        return BecerroEntidad.builder()
                .id(b.getId())
                .nombre(b.getNombre())
                .fechaNacimiento(b.getFechaNacimiento())
                .sexo(b.getSexo())
                .nombrePadre(b.getNombrePadre())
                .razaPadre(b.getRazaPadre())
                .notas(b.getNotas())
                .madreId(b.getMadreId())
                .cicloId(b.getCicloId())
                .tenantId(b.getTenantId())
                .build();
    }
}
