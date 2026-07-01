package com.vacapp.ganado.internal.application.usecases;

import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.domain.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/** Caso de uso: listar todos los animales del tenant activo. */
@Service
@RequiredArgsConstructor
public class ListarAnimalesUseCase {

    private final AnimalRepository animalRepository;

    @Transactional(readOnly = true)
    public List<Animal> ejecutar(String tenantId) {
        return animalRepository.listarPorTenant(tenantId);
    }
}
