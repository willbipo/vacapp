package com.vacapp.ranchos.internal.infrastructure.persistence;

import com.vacapp.ranchos.internal.domain.model.Rancho;
import org.springframework.stereotype.Component;

/**
 * Mapper: convierte entre entidad JDBC y dominio.
 */
@Component
public class RanchoMapper {
    public Rancho toDomain(RanchoEntity entity) {
        return new Rancho(
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
    }

    public RanchoEntity toEntity(Rancho domain) {
        RanchoEntity entity = new RanchoEntity();
        entity.setId(domain.getId());
        entity.setTenantId(domain.getTenantId());
        entity.setUserId(domain.getUserId());
        entity.setNombre(domain.getNombre());
        entity.setDescripcion(domain.getDescripcion());
        entity.setHectareas(domain.getHectareas());
        entity.setUbicacion(domain.getUbicacion());
        entity.setFechaRegistro(domain.getFechaRegistro());
        entity.setFechaActualizacion(domain.getFechaActualizacion());
        return entity;
    }
}
