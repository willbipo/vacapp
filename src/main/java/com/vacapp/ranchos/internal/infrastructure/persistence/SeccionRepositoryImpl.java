package com.vacapp.ranchos.internal.infrastructure.persistence;

import com.vacapp.core.TenantContext;
import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.domain.repository.SeccionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de Secciones usando JDBC.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class SeccionRepositoryImpl implements SeccionRepository {

    private final SeccionJpaRepository seccionJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Seccion guardar(Seccion seccion) {
        String tenantId = TenantContext.obtenerTenant();
        SeccionEntity entity = toEntity(seccion);
        
        // Usar INSERT explícito con JdbcTemplate para asegurar INSERT en lugar de UPDATE
        String sql = "INSERT INTO secciones (id, rancho_id, nombre, tenant_id, fecha_registro, fecha_actualizacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            entity.getId(),
            entity.getRanchoId(),
            entity.getNombre(),
            tenantId,
            entity.getFechaRegistro(),
            entity.getFechaActualizacion()
        );
        
        log.info("[SECCIONES] Sección guardada con ID: {}, ranchoId: {}, tenantId: {}", 
            entity.getId(), entity.getRanchoId(), tenantId);
        return toDomain(entity);
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
        entity.setTenantId(TenantContext.obtenerTenant());
        entity.setFechaRegistro(LocalDateTime.now());
        entity.setFechaActualizacion(LocalDateTime.now());
        return entity;
    }
}
