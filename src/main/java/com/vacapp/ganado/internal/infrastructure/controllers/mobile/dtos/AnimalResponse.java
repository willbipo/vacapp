package com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos;

import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;

import java.time.LocalDate;
import java.util.UUID;

/** DTO de salida con los datos de un animal. */
public record AnimalResponse(
        UUID id,
        String numeroIdentificador,
        Estatus estatus,
        Sexo sexo,
        String raza,
        LocalDate fechaNacimiento,
        Integer meses,
        LocalDate fechaAretado,
        Tipo tipo,
        String areteAnterior,
        String folioReemo,
        String nota
) {
    public static AnimalResponse desde(Animal animal) {
        return new AnimalResponse(
                animal.getId(),
                animal.getNumeroIdentificador(),
                animal.getEstatus(),
                animal.getSexo(),
                animal.getRaza(),
                animal.getFechaNacimiento(),
                animal.getMeses(),
                animal.getFechaAretado(),
                animal.getTipo(),
                animal.getAreteAnterior(),
                animal.getFolioReemo(),
                animal.getNota()
        );
    }
}
