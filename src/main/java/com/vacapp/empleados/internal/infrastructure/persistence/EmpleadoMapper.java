package com.vacapp.empleados.internal.infrastructure.persistence;

import com.vacapp.empleados.internal.domain.model.Empleado;
import com.vacapp.empleados.internal.domain.model.Estado;
import com.vacapp.usuarios.internal.domain.model.Rol;
import org.springframework.stereotype.Component;

/**
 * Mapper: convierte entre entidad JPA y dominio.
 */
@Component
public class EmpleadoMapper {
    public Empleado toDomain(EmpleadoEntity entity) {
        return new Empleado(
            entity.getId(),
            entity.getTenantId(),
            entity.getRanchoId(),
            entity.getNombre(),
            entity.getEmail(),
            entity.getTelefono(),
            Rol.valueOf(entity.getRol()),
            Estado.valueOf(entity.getEstado()),
            entity.getFechaRegistro(),
            entity.getFechaActualizacion()
        );
    }

    public EmpleadoEntity toEntity(Empleado domain) {
        EmpleadoEntity entity = new EmpleadoEntity();
        entity.setId(domain.getId());
        entity.setTenantId(domain.getTenantId());
        entity.setRanchoId(domain.getRanchoId());
        entity.setNombre(domain.getNombre());
        entity.setEmail(domain.getEmail());
        entity.setTelefono(domain.getTelefono());
        entity.setRol(domain.getRol().name());
        entity.setEstado(domain.getEstado().name());
        entity.setFechaRegistro(domain.getFechaRegistro());
        entity.setFechaActualizacion(domain.getFechaActualizacion());
        return entity;
    }
}
