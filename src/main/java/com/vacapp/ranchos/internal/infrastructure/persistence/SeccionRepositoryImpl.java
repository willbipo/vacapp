package com.vacapp.ranchos.internal.infrastructure.persistence;

import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.domain.repository.SeccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de Secciones usando JDBC.
 */
@Repository
@RequiredArgsConstructor
public class SeccionRepositoryImpl implements SeccionRepository {

    private final SeccionJpaRepository seccionJpaRepository;

    @Override
    public Seccion guardar(Seccion seccion) {
        SeccionEntity entity = toEntity(seccion);
        if (entity.getId() == null) {
            entity.setId(UUID.randomUUID().toString());
            entity.setFechaRegistro(LocalDateTime.now());
            entity.setFechaActualizacion(LocalDateTime.now());
        }
        SeccionEntity saved = seccionJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Seccion> obtenerPorId(String id, String tenantId) {
        return seccionJpaRepository.findByIdAndTenantId(id, tenantId)
                .map(this::toDomain);
    }

    @Override
    public List<Seccion> obtenerPorRancho(String ranchoId, String tenantId) {
        return seccionJpaRepository.findByRanchoIdAndTenantId(ranchoId, tenantId)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Seccion> obtenerPorNombre(String nombre, String ranchoId, String tenantId) {
        return seccionJpaRepository.findByNombreAndRanchoIdAndTenantId(nombre, ranchoId, tenantId)
                .map(this::toDomain);
    }

    @Override
    public void actualizar(Seccion seccion) {
        SeccionEntity entity = toEntity(seccion);
        entity.setFechaActualizacion(LocalDateTime.now());
        seccionJpaRepository.save(entity);
    }

    @Override
    public void eliminar(String id, String tenantId) {
        seccionJpaRepository.findByIdAndTenantId(id, tenantId).ifPresent(seccionJpaRepository::delete);
    }

    // Mapper: Entity -> Domain
    private Seccion toDomain(SeccionEntity entity) {
        return new Seccion(
                entity.getId(),
                entity.getRanchoId(),
                entity.getNombre()
        );
    }

    // Mapper: Domain -> Entity
    private SeccionEntity toEntity(Seccion seccion) {
        SeccionEntity entity = new SeccionEntity();
        entity.setId(seccion.getId());
        entity.setRanchoId(seccion.getRanchoId());
        entity.setNombre(seccion.getNombre());
        entity.setFechaActualizacion(LocalDateTime.now());
        return entity;
    }
}
