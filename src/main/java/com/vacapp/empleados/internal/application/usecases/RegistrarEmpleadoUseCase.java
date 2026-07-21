package com.vacapp.empleados.internal.application.usecases;

import com.vacapp.empleados.internal.domain.model.Empleado;
import com.vacapp.empleados.internal.domain.model.Estado;
import com.vacapp.usuarios.internal.domain.model.Rol;
import com.vacapp.empleados.internal.domain.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Caso de uso: Registrar un nuevo empleado.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class RegistrarEmpleadoUseCase {
    private final EmpleadoRepository empleadoRepository;

    public Empleado ejecutar(
        String tenantId,
        String nombre,
        String email,
        String telefono,
        Rol rol,
        String ranchoId
    ) {
        Empleado empleado = new Empleado(
            UUID.randomUUID().toString(),
            tenantId,
            ranchoId,
            nombre,
            email,
            telefono,
            rol,
            Estado.PENDIENTE,
            LocalDateTime.now(),
            LocalDateTime.now()
        );
        return empleadoRepository.guardar(empleado);
    }
}
