package com.vacapp.ganado.internal.application.usecases;

import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
import com.vacapp.ganado.internal.domain.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

/** Caso de uso: registrar un nuevo animal en el inventario. */
@Service
@RequiredArgsConstructor
public class RegistrarAnimalUseCase {

    private final AnimalRepository animalRepository;

    public record Comando(
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
        String nota,
        String tenantId
    ) {}

    @Transactional
    public Animal ejecutar(Comando cmd) {
        Animal animal = Animal.builder()
                .numeroIdentificador(cmd.numeroIdentificador())
                .estatus(cmd.estatus())
                .sexo(cmd.sexo())
                .raza(cmd.raza())
                .fechaNacimiento(cmd.fechaNacimiento())
                .meses(cmd.meses())
                .fechaAretado(cmd.fechaAretado())
                .tipo(cmd.tipo())
                .areteAnterior(cmd.areteAnterior())
                .folioReemo(cmd.folioReemo())
                .nota(cmd.nota())
                .tenantId(cmd.tenantId())
                .build();

        return animalRepository.guardar(animal);
    }
}
