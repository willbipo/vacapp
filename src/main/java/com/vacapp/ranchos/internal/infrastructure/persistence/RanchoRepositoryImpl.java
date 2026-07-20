package com.vacapp.ranchos.internal.infrastructure.persistence;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio Rancho con Spring Data JDBC.
 */
@Repository
@RequiredArgsConstructor
public class RanchoRepositoryImpl implements RanchoRepository {
    private final RanchoJpaRepository jpaRepository;
    private final RanchoMapper mapper;

    @Override
    public Rancho guardar(Rancho rancho) {
        RanchoEntity entity = mapper.toEntity(rancho);
        RanchoEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Rancho> obtenerPorId(String id, String tenantId) {
        return jpaRepository.findByIdAndTenantId(id, tenantId)
            .map(mapper::toDomain);
    }

    @Override
    public List<Rancho> obtenerTodosPorTenant(String tenantId) {
        return jpaRepository.findByTenantId(tenantId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

    @Override
    public List<Rancho> obtenerPorUsuario(String userId, String tenantId) {
        return jpaRepository.findByUserIdAndTenantId(userId, tenantId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Rancho> obtenerPorNombre(String nombre, String tenantId) {
        return jpaRepository.findByNombreAndTenantId(nombre, tenantId)
            .map(mapper::toDomain);
    }

    @Override
    public void actualizar(Rancho rancho) {
        RanchoEntity entity = mapper.toEntity(rancho);
        jpaRepository.save(entity);
    }

    @Override
    public void eliminar(String id, String tenantId) {
        jpaRepository.findByIdAndTenantId(id, tenantId)
            .ifPresent(entity -> jpaRepository.deleteById(entity.getId()));
    }
}
