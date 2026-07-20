package com.vacapp.empleados.internal.application.usecases;

import com.vacapp.empleados.internal.domain.model.Empleado;
import com.vacapp.empleados.internal.domain.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Caso de uso: Listar todos los empleados.
 */
@Service
@RequiredArgsConstructor
public class ListarEmpleadosUseCase {
    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public List<Empleado> ejecutar(String tenantId) {
        return empleadoRepository.obtenerTodos(tenantId);
    }

    @Transactional(readOnly = true)
    public List<Empleado> ejecutar(String ranchoId, String tenantId) {
        return empleadoRepository.obtenerPorRancho(ranchoId, tenantId);
    }
}
