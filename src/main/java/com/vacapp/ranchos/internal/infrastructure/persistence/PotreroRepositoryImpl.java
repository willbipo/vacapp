package com.vacapp.ranchos.internal.infrastructure.persistence;

import com.vacapp.core.TenantContext;
import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.domain.repository.PotreroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementación del repositorio de Potreros usando JDBC.
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PotreroRepositoryImpl implements PotreroRepository {

    private final PotreroJpaRepository potreroJpaRepository;
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Potrero guardar(Potrero potrero) {
        String tenantId = TenantContext.obtenerTenant();
        PotreroEntity entity = toEntity(potrero);
        
        // Usar INSERT explícito con JdbcTemplate para asegurar INSERT en lugar de UPDATE
        String sql = "INSERT INTO potreros (id, rancho_id, seccion_id, nombre, hectareas, tipo_pasto, tenant_id, fecha_registro, fecha_actualizacion) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql,
            entity.getId(),
            entity.getRanchoId(),
            entity.getSeccionId(),
            entity.getNombre(),
            entity.getHectareas(),
            entity.getTipoPasto(),
            tenantId,
            entity.getFechaRegistro(),
            entity.getFechaActualizacion()
        );
        
        log.info("[POTREROS] Potrero guardado con ID: {}, ranchoId: {}, seccionId: {}, tenantId: {}", 
            entity.getId(), entity.getRanchoId(), entity.getSeccionId(), tenantId);
        return toDomain(entity);
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
    public Double sumarHectareasPorRancho(String ranchoId, String tenantId) {
        String sql = "SELECT COALESCE(SUM(hectareas), 0) FROM potreros WHERE rancho_id = ? AND tenant_id = ?";
        Double resultado = jdbcTemplate.queryForObject(sql, Double.class, ranchoId, tenantId);
        return resultado != null ? resultado : 0.0;
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
        entity.setTenantId(TenantContext.obtenerTenant());
        entity.setFechaRegistro(LocalDateTime.now());
        entity.setFechaActualizacion(LocalDateTime.now());
        return entity;
    }
}
