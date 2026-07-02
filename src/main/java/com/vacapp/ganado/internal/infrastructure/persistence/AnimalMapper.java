package com.vacapp.ganado.internal.infrastructure.persistence;

import com.vacapp.ganado.internal.domain.model.Animal;
import org.springframework.stereotype.Component;

/** Mapea entre {@link Animal} (dominio) y {@link AnimalEntidad} (JPA). */
@Component
public class AnimalMapper {

    public AnimalEntidad aEntidad(Animal animal) {
        AnimalEntidad entidad = AnimalEntidad.builder()
                .id(animal.getId())
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
                .categoria(animal.getCategoria())
                .fechaInicioReposo(animal.getFechaInicioReposo())
                .fechaFinReposo(animal.getFechaFinReposo())
                .tenantId(animal.getTenantId())
                .build();
        // Si el animal ya tiene ID es una actualización, no una inserción nueva
        entidad.setEsNueva(animal.getId() == null);
        return entidad;
    }

    public Animal aDominio(AnimalEntidad entidad) {
        return Animal.builder()
                .id(entidad.getId())
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
                .categoria(entidad.getCategoria())
                .fechaInicioReposo(entidad.getFechaInicioReposo())
                .fechaFinReposo(entidad.getFechaFinReposo())
                .tenantId(entidad.getTenantId())
                .build();
    }
}
