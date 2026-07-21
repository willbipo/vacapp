package com.vacapp.ranchos.internal.infrastructure.persistence;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.domain.repository.RanchoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio Rancho con Spring Data JDBC.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class RanchoRepositoryImpl implements RanchoRepository {
    private final RanchoJpaRepository jpaRepository;
    private final RanchoMapper mapper;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Rancho guardar(Rancho rancho) {
        RanchoEntity entity = mapper.toEntity(rancho);
        
        // Usar INSERT explícito con JdbcTemplate para asegurar INSERT en lugar de UPDATE
        String sql = "INSERT INTO ranchos (id, tenant_id, user_id, nombre, descripcion, hectareas, ubicacion, fecha_registro, fecha_actualizacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            entity.getId(),
            entity.getTenantId(),
            entity.getUserId(),
            entity.getNombre(),
            entity.getDescripcion(),
            entity.getHectareas(),
            entity.getUbicacion(),
            entity.getFechaRegistro(),
            entity.getFechaActualizacion()
        );
        
        log.info("[RANCHOS] RanchoRepositoryImpl.guardar - ID guardado: {}, userId: {}, tenantId: {}", 
            entity.getId(), entity.getUserId(), entity.getTenantId());
        return mapper.toDomain(entity);
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
        log.info("[RANCHOS] RanchoRepositoryImpl.obtenerPorUsuario - userId: {}, tenantId: {}", userId, tenantId);
        
        List<RanchoEntity> entities = jpaRepository.findByUserIdAndTenantId(userId, tenantId);
        log.info("[RANCHOS] RanchoRepositoryImpl.obtenerPorUsuario - entidades encontradas: {}", entities.size());
        entities.forEach(e -> log.info("[RANCHOS] Rancho encontrado - ID: {}, userId: {}, nombre: {}", 
            e.getId(), e.getUserId(), e.getNombre()));
        
        return entities.stream()
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
