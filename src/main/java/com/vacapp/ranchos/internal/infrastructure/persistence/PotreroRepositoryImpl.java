package com.vacapp.ranchos.internal.infrastructure.persistence;

import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de Potreros usando JDBC.
 */
@Repository
@RequiredArgsConstructor
public class PotreroRepositoryImpl implements PotreroRepository {

    private final PotreroJpaRepository potreroJpaRepository;

    @Override
    public Potrero guardar(Potrero potrero) {
        PotreroEntity entity = toEntity(potrero);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
            entity.setFechaRegistro(LocalDateTime.now());
            entity.setFechaActualizacion(LocalDateTime.now());
        }
        PotreroEntity saved = potreroJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Potrero> obtenerPorId(String id, String tenantId) {
        return potreroJpaRepository.findByIdAndTenantId(id, tenantId)
                .map(this::toDomain);
    }

    @Override
    public List<Potrero> obtenerPorRancho(String ranchoId, String tenantId) {
        return potreroJpaRepository.findByRanchoIdAndTenantId(ranchoId, tenantId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Potrero> obtenerPorSeccion(String seccionId, String tenantId) {
        return potreroJpaRepository.findBySeccionIdAndTenantId(seccionId, tenantId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Potrero> obtenerPorNombre(String nombre, String ranchoId, String tenantId) {
        return potreroJpaRepository.findByNombreAndRanchoIdAndTenantId(nombre, ranchoId, tenantId)
                .map(this::toDomain);
    }

    @Override
    public void actualizar(Potrero potrero) {
        PotreroEntity entity = toEntity(potrero);
        entity.setFechaActualizacion(LocalDateTime.now());
        potreroJpaRepository.save(entity);
    }

    @Override
    public void eliminar(String id, String tenantId) {
        potreroJpaRepository.findByIdAndTenantId(id, tenantId).ifPresent(potreroJpaRepository::delete);
    }

    // Mapper: Entity -> Domain
    private Potrero toDomain(PotreroEntity entity) {
        if (entity.getSeccionId() == null) {
            return new Potrero(
                    entity.getId(),
                    entity.getRanchoId(),
                    entity.getNombre(),
                    entity.getHectareas(),
                    entity.getTipoPasto()
            );
        } else {
            return new Potrero(
                    entity.getId(),
                    entity.getRanchoId(),
                    entity.getSeccionId(),
                    entity.getNombre(),
                    entity.getHectareas(),
                    entity.getTipoPasto()
            );
        }
    }

    // Mapper: Domain -> Entity
    private PotreroEntity toEntity(Potrero potrero) {
        PotreroEntity entity = new PotreroEntity();
        entity.setId(potrero.getId());
        entity.setRanchoId(potrero.getRanchoId());
        entity.setSeccionId(potrero.getSeccionId().orElse(null));
        entity.setNombre(potrero.getNombre());
        entity.setHectareas(potrero.getHectareas());
        entity.setTipoPasto(potrero.getTipoPasto());
        entity.setFechaActualizacion(LocalDateTime.now());
        return entity;
    }
}
