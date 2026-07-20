package com.vacapp.empleados.internal.infrastructure.persistence;

import com.vacapp.empleados.internal.domain.model.Empleado;
import com.vacapp.empleados.internal.domain.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Implementación del repositorio Empleado con Spring Data JDBC.
 */
@Repository
@RequiredArgsConstructor
public class EmpleadoRepositoryImpl implements EmpleadoRepository {
    private final EmpleadoJpaRepository jpaRepository;
    private final EmpleadoMapper mapper;

    @Override
    public Empleado guardar(Empleado empleado) {
        EmpleadoEntity entity = mapper.toEntity(empleado);
        EmpleadoEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Empleado> obtenerPorId(String id, String tenantId) {
        return jpaRepository.findByIdAndTenantId(id, tenantId)
            .map(mapper::toDomain);
    }

    @Override
    public List<Empleado> obtenerTodos(String tenantId) {
        return jpaRepository.findByTenantId(tenantId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

    @Override
    public List<Empleado> obtenerPorRancho(String ranchoId, String tenantId) {
        return jpaRepository.findByRanchoIdAndTenantId(ranchoId, tenantId)
            .stream()
            .map(mapper::toDomain)
            .toList();
    }

    @Override
    public Optional<Empleado> obtenerPorEmail(String email, String tenantId) {
        return jpaRepository.findByEmailAndTenantId(email, tenantId)
            .map(mapper::toDomain);
    }

    @Override
    public void actualizar(Empleado empleado) {
        EmpleadoEntity entity = mapper.toEntity(empleado);
        jpaRepository.save(entity);
    }
}
