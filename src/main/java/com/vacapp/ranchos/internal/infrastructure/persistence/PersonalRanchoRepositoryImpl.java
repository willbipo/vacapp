package com.vacapp.ranchos.internal.infrastructure.persistence;

import com.vacapp.ranchos.internal.domain.model.PersonalRancho;
import com.vacapp.ranchos.internal.domain.repository.PersonalRanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de PersonalRancho usando JDBC.
 */
@Repository
@RequiredArgsConstructor
public class PersonalRanchoRepositoryImpl implements PersonalRanchoRepository {

    private final PersonalRanchoJpaRepository personalRanchoJpaRepository;

    @Override
    public PersonalRancho guardar(PersonalRancho personalRancho) {
        PersonalRanchoEntity entity = toEntity(personalRancho);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
        }
        PersonalRanchoEntity saved = personalRanchoJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<PersonalRancho> obtenerPorId(String id, String tenantId) {
        return personalRanchoJpaRepository.findByIdAndTenantId(id, tenantId)
                .map(this::toDomain);
    }

    @Override
    public List<PersonalRancho> obtenerPorRancho(String ranchoId, String tenantId) {
        return personalRanchoJpaRepository.findByRanchoIdAndTenantId(ranchoId, tenantId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<PersonalRancho> obtenerPorEmpleado(String empleadoId, String tenantId) {
        return personalRanchoJpaRepository.findByEmpleadoIdAndTenantId(empleadoId, tenantId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<PersonalRancho> obtenerAsignacionActiva(String empleadoId, String ranchoId, String tenantId) {
        return personalRanchoJpaRepository.findActivaByEmpleadoAndRancho(empleadoId, ranchoId, tenantId)
                .map(this::toDomain);
    }

    @Override
    public void actualizar(PersonalRancho personalRancho) {
        PersonalRanchoEntity entity = toEntity(personalRancho);
        personalRanchoJpaRepository.save(entity);
    }

    @Override
    public void eliminar(String id, String tenantId) {
        personalRanchoJpaRepository.findByIdAndTenantId(id, tenantId).ifPresent(personalRanchoJpaRepository::delete);
    }

    // Mapper: Entity -> Domain
    private PersonalRancho toDomain(PersonalRanchoEntity entity) {
        return new PersonalRancho(
                entity.getId(),
                entity.getEmpleadoId(),
                entity.getRanchoId(),
                entity.getFechaAsignacion(),
                entity.getFechaFinAsignacion(),
                entity.getActivo() != null ? entity.getActivo() : false
        );
    }

    // Mapper: Domain -> Entity
    private PersonalRanchoEntity toEntity(PersonalRancho personalRancho) {
        PersonalRanchoEntity entity = new PersonalRanchoEntity();
        entity.setId(personalRancho.getId());
        entity.setEmpleadoId(personalRancho.getEmpleadoId());
        entity.setRanchoId(personalRancho.getRanchoId());
        entity.setFechaAsignacion(personalRancho.getFechaAsignacion());
        entity.setFechaFinAsignacion(personalRancho.getFechaFinAsignacion());
        entity.setActivo(personalRancho.isActivo());
        return entity;
    }
}
