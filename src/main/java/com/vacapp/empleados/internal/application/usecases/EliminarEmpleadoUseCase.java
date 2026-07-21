package com.vacapp.empleados.internal.application.usecases;

import com.vacapp.empleados.internal.domain.model.EmpleadoNoEncontradoException;
import com.vacapp.empleados.internal.domain.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Caso de uso: Eliminar empleado.
 */
@Service
@RequiredArgsConstructor
public class EliminarEmpleadoUseCase {
    private final EmpleadoRepository empleadoRepository;

    public void ejecutar(String id, String tenantId) {
        if (!empleadoRepository.obtenerPorId(id, tenantId).isPresent()) {
            throw new EmpleadoNoEncontradoException(id);
        }
        empleadoRepository.eliminar(id, tenantId);
    }
}
