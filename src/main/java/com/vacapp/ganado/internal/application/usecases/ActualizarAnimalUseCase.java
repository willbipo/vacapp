package com.vacapp.ganado.internal.application.usecases;

import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.domain.model.AnimalNoEncontradoException;
import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
import com.vacapp.ganado.internal.domain.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/** Caso de uso: actualizar los datos de un animal existente. */
@Service
@RequiredArgsConstructor
public class ActualizarAnimalUseCase {

    private final AnimalRepository animalRepository;

    public record Comando(
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
        String nota,
        String categoria,
        String tenantId
    ) {}

    @Transactional
    public Animal ejecutar(Comando cmd) {
        Animal animal = animalRepository.buscarPorId(cmd.id(), cmd.tenantId())
                .orElseThrow(() -> new AnimalNoEncontradoException(cmd.id().toString()));

        animal.setNumeroIdentificador(cmd.numeroIdentificador());
        animal.setEstatus(cmd.estatus());
        animal.setSexo(cmd.sexo());
        animal.setRaza(cmd.raza());
        animal.setFechaNacimiento(cmd.fechaNacimiento());
        animal.setMeses(cmd.meses());
        animal.setFechaAretado(cmd.fechaAretado());
        animal.setTipo(cmd.tipo());
        animal.setAreteAnterior(cmd.areteAnterior());
        animal.setFolioReemo(cmd.folioReemo());
        animal.setNota(cmd.nota());
        animal.setCategoria(cmd.categoria());

        return animalRepository.actualizar(animal);
    }
}
