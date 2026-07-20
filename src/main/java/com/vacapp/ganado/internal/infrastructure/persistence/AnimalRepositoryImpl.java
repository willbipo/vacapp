package com.vacapp.ganado.internal.infrastructure.persistence;

import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.domain.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/** Implementación del puerto {@link AnimalRepository} usando Spring Data JPA. */
@Repository
@RequiredArgsConstructor
public class AnimalRepositoryImpl implements AnimalRepository {

    private final AnimalJpaRepository jpaRepository;
    private final AnimalMapper mapper;

    @Override
    @Transactional
    public Animal guardar(Animal animal) {
        AnimalEntidad entidad = mapper.aEntidad(animal);
        return mapper.aDominio(jpaRepository.save(entidad));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Animal> buscarPorId(UUID id, String tenantId) {
        return jpaRepository.findByIdAndTenantId(id, tenantId).map(mapper::aDominio);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Animal> listarPorTenant(String tenantId) {
        return jpaRepository.findAllByTenantId(tenantId).stream()
                .map(mapper::aDominio)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Animal> listarPorRancho(String ranchoId, String tenantId) {
        return jpaRepository.findByRanchoIdAndTenantId(ranchoId, tenantId).stream()
                .map(mapper::aDominio)
                .toList();
    }

    @Override
    @Transactional
    public void eliminar(UUID id, String tenantId) {
        jpaRepository.deleteByIdAndTenantId(id, tenantId);
    }
}
