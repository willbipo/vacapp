package com.vacapp.ganado.internal.infrastructure.persistence;

import com.vacapp.ganado.internal.domain.model.Animal;
import org.springframework.stereotype.Component;

/** Mapea entre {@link Animal} (dominio) y {@link AnimalEntidad} (JPA). */
@Component
public class AnimalMapper {

    public AnimalEntidad aEntidad(Animal animal) {
        return AnimalEntidad.builder()
                .id(animal.getId())
                .ranchoId(animal.getRanchoId())
                .numeroIdentificador(animal.getNumeroIdentificador())
                .estatus(animal.getEstatus())
                .sexo(animal.getSexo())
                .raza(animal.getRaza())
                .fechaNacimiento(animal.getFechaNacimiento())
                .meses(animal.getMeses())
                .fechaAretado(animal.getFechaAretado())
                .tipo(animal.getTipo())
                .areteAnterior(animal.getAreteAnterior())
                .folioReemo(animal.getFolioReemo())
                .nota(animal.getNota())
                .tenantId(animal.getTenantId())
                .build();
    }

    public Animal aDominio(AnimalEntidad entidad) {
        return Animal.builder()
                .id(entidad.getId())
                .ranchoId(entidad.getRanchoId())
                .numeroIdentificador(entidad.getNumeroIdentificador())
                .estatus(entidad.getEstatus())
                .sexo(entidad.getSexo())
                .raza(entidad.getRaza())
                .fechaNacimiento(entidad.getFechaNacimiento())
                .meses(entidad.getMeses())
                .fechaAretado(entidad.getFechaAretado())
                .tipo(entidad.getTipo())
                .areteAnterior(entidad.getAreteAnterior())
                .folioReemo(entidad.getFolioReemo())
                .nota(entidad.getNota())
                .tenantId(entidad.getTenantId())
                .build();
    }
}
