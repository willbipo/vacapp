package com.vacapp.ganado;

import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

/**
 * API pública del módulo Ganado.
 * Otros módulos deben interactuar con ganado únicamente a través de esta clase.
 */
@Service
@RequiredArgsConstructor
public class GanadoService {

    private final AnimalRepository animalRepository;

    /** Busca un animal por su número de identificador (arete) y tenant. */
    @Transactional(readOnly = true)
    public Optional<Animal> buscarPorNumeroIdentificador(String numeroIdentificador, String tenantId) {
        return animalRepository.buscarPorNumeroIdentificador(numeroIdentificador, tenantId);
    }

    /**
     * Da de baja el animal con el número de identificador dado.
     * Método público para uso de otros módulos (ej. ventas).
     */
    @Transactional
    public void darDeBaja(String numeroIdentificador, String tenantId) {
        animalRepository.buscarPorNumeroIdentificador(numeroIdentificador, tenantId)
                .ifPresent(animal ->
                        animalRepository.actualizarEstatus(animal.getId(), "BAJA", tenantId));
    }

    /**
     * Cambia el estatus del animal directamente por ID.
     * Uso interno de otros módulos (ciclo reproductivo).
     */
    @Transactional
    public void cambiarEstatus(java.util.UUID animalId, String nuevoEstatus, String tenantId) {
        animalRepository.actualizarEstatus(animalId, nuevoEstatus, tenantId);
    }

    /**
     * Pone el animal en REPOSO con fechas de inicio y fin.
     * Llamado por RegistrarPartoUseCase al registrar un parto.
     */
    @Transactional
    public void ponerEnReposo(UUID animalId, LocalDate fechaInicio,
                              LocalDate fechaFin, String tenantId) {
        animalRepository.actualizarReposo(animalId, fechaInicio, fechaFin, tenantId);
    }

    /**
     * Registra un becerro recién nacido como un animal nuevo en el inventario.
     * Llamado automáticamente por RegistrarPartoUseCase.
     */
    @Transactional
    public Animal registrarAnimalDesdeBecerro(String numeroIdentificador, String sexo,
                                              LocalDate fechaNacimiento, String raza,
                                              String nota, String tenantId) {
        Animal becerro = Animal.builder()
                .id(UUID.randomUUID())
                .numeroIdentificador(numeroIdentificador)
                .estatus(Estatus.VIGENTE)
                .sexo(Sexo.valueOf(sexo))
                .fechaNacimiento(fechaNacimiento)
                .raza(raza)
                .nota(nota)
                .tenantId(tenantId)
                .build();
        return animalRepository.guardar(becerro);
    }
}
