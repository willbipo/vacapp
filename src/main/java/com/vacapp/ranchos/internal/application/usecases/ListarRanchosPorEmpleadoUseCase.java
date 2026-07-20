package com.vacapp.ranchos.internal.application.usecases;

import com.vacapp.ranchos.internal.domain.model.PersonalRancho;
import com.vacapp.ranchos.internal.domain.repository.PersonalRanchoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Caso de uso: Listar los ranchos asignados a un empleado.
 */
@Service
@RequiredArgsConstructor
public class ListarRanchosPorEmpleadoUseCase {
    private final PersonalRanchoRepository personalRanchoRepository;

    public List<PersonalRancho> ejecutar(String empleadoId, String tenantId) {
        return personalRanchoRepository.obtenerPorEmpleado(empleadoId, tenantId);
    }
}
