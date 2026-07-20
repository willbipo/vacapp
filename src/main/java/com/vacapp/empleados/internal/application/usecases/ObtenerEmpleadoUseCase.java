package com.vacapp.empleados.internal.application.usecases;

import com.vacapp.empleados.internal.domain.model.Empleado;
import com.vacapp.empleados.internal.domain.model.EmpleadoNoEncontradoException;
import com.vacapp.empleados.internal.domain.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: Obtener un empleado por ID.
 */
@Service
@RequiredArgsConstructor
public class ObtenerEmpleadoUseCase {
    private final EmpleadoRepository empleadoRepository;

    public Empleado ejecutar(String id, String tenantId) {
        return empleadoRepository.obtenerPorId(id, tenantId)
            .orElseThrow(() -> new EmpleadoNoEncontradoException(id));
    }
}
