package com.vacapp.empleados.internal.application.usecases;

import com.vacapp.empleados.internal.domain.model.Empleado;
import com.vacapp.empleados.internal.domain.model.Estado;
import com.vacapp.usuarios.internal.domain.model.Rol;
import com.vacapp.empleados.internal.domain.model.EmpleadoNoEncontradoException;
import com.vacapp.empleados.internal.domain.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * Caso de uso: Actualizar un empleado existente.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ActualizarEmpleadoUseCase {
    private final EmpleadoRepository empleadoRepository;

    public Empleado ejecutar(
        String id,
        String tenantId,
        String nombre,
        String email,
        String telefono,
        Rol rol,
        Estado estado
    ) {
        Empleado empleado = empleadoRepository.obtenerPorId(id, tenantId)
            .orElseThrow(() -> new EmpleadoNoEncontradoException(id));

        empleado.setNombre(nombre);
        empleado.setEmail(email);
        empleado.setTelefono(telefono);
        empleado.setRol(rol);
        empleado.setEstado(estado);
        empleado.setFechaActualizacion(LocalDateTime.now());

        empleadoRepository.actualizar(empleado);
        return empleado;
    }
}
