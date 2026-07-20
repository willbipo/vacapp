package com.vacapp.ranchos.internal.infrastructure.controllers.mobile;

import com.vacapp.ranchos.internal.application.usecases.*;
import com.vacapp.ranchos.internal.domain.model.PersonalRancho;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.AsignarPersonalRequest;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.PersonalRanchoResponse;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.MensajeResponse;
import com.vacapp.core.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para asignación de personal a ranchos.
 */
@RestController
@RequestMapping("/api/v1/ranchos/{ranchoId}/personal")
@RequiredArgsConstructor
public class PersonalRanchoRestController {
    private final AsignarPersonalARanchoUseCase asignarPersonalARanchoUseCase;
    private final ListarPersonalPorRanchoUseCase listarPersonalPorRanchoUseCase;
    private final DesasignarPersonalDeRanchoUseCase desasignarPersonalDeRanchoUseCase;

    @PostMapping
    public ResponseEntity<PersonalRanchoResponse> asignarPersonal(
        @PathVariable String ranchoId,
        @Valid @RequestBody AsignarPersonalRequest request
    ) {
        String tenantId = TenantContext.obtenerTenant();
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("No autenticado: tenant no encontrado");
        }
        
        PersonalRancho personalRancho = asignarPersonalARanchoUseCase.ejecutar(
            request.empleadoId(),
            ranchoId,
            tenantId
        );
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapToResponse(personalRancho));
    }

    @GetMapping
    public ResponseEntity<List<PersonalRanchoResponse>> listarPersonal(
        @PathVariable String ranchoId
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        List<PersonalRancho> personal = listarPersonalPorRanchoUseCase.ejecutar(ranchoId, tenantId);
        return ResponseEntity.ok(
            personal.stream()
                .map(this::mapToResponse)
                .toList()
        );
    }

    @DeleteMapping("/{empleadoId}")
    public ResponseEntity<MensajeResponse> desasignarPersonal(
        @PathVariable String ranchoId,
        @PathVariable String empleadoId
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        desasignarPersonalDeRanchoUseCase.ejecutar(empleadoId, ranchoId, tenantId);
        return ResponseEntity.ok(new MensajeResponse("Personal desasignado exitosamente del rancho"));
    }

    private PersonalRanchoResponse mapToResponse(PersonalRancho personalRancho) {
        return new PersonalRanchoResponse(
            personalRancho.getId(),
            personalRancho.getEmpleadoId(),
            personalRancho.getRanchoId(),
            personalRancho.getFechaAsignacion(),
            personalRancho.getFechaFinAsignacion(),
            personalRancho.isActivo()
        );
    }
}

@RestController
@RequestMapping("/api/v1/empleados/{empleadoId}/ranchos")
@RequiredArgsConstructor
class RanchosPorEmpleadoRestController {
    private final ListarRanchosPorEmpleadoUseCase listarRanchosPorEmpleadoUseCase;

    @GetMapping
    public ResponseEntity<List<PersonalRanchoResponse>> listarRanchosPorEmpleado(
        @PathVariable String empleadoId
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        List<PersonalRancho> asignaciones = listarRanchosPorEmpleadoUseCase.ejecutar(empleadoId, tenantId);
        return ResponseEntity.ok(
            asignaciones.stream()
                .map(this::mapToResponse)
                .toList()
        );
    }

    private PersonalRanchoResponse mapToResponse(PersonalRancho personalRancho) {
        return new PersonalRanchoResponse(
            personalRancho.getId(),
            personalRancho.getEmpleadoId(),
            personalRancho.getRanchoId(),
            personalRancho.getFechaAsignacion(),
            personalRancho.getFechaFinAsignacion(),
            personalRancho.isActivo()
        );
    }
}
