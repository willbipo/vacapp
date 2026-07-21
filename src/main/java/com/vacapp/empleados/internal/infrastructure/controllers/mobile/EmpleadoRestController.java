package com.vacapp.empleados.internal.infrastructure.controllers.mobile;

import com.vacapp.empleados.internal.application.usecases.*;
import com.vacapp.empleados.internal.domain.model.Empleado;
import com.vacapp.empleados.internal.domain.model.Estado;
import com.vacapp.usuarios.internal.domain.model.Rol;
import com.vacapp.empleados.internal.infrastructure.controllers.mobile.dtos.EmpleadoRequest;
import com.vacapp.empleados.internal.infrastructure.controllers.mobile.dtos.EmpleadoResponse;
import com.vacapp.empleados.internal.infrastructure.controllers.mobile.dtos.MensajeResponse;
import com.vacapp.empleados.internal.infrastructure.services.EmailService;
import com.vacapp.core.TenantContext;
import com.vacapp.ranchos.internal.application.usecases.ObtenerRanchoUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para empleados.
 * Implementa la interfaz generada desde openapi-empleados.yaml (Design-First).
 * Sin anotaciones Swagger propias — el contrato vive en el YAML.
 */
@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
public class EmpleadoRestController {
    private final RegistrarEmpleadoUseCase registrarEmpleadoUseCase;
    private final ListarEmpleadosUseCase listarEmpleadosUseCase;
    private final ActualizarEmpleadoUseCase actualizarEmpleadoUseCase;
    private final ObtenerEmpleadoUseCase obtenerEmpleadoUseCase;
    private final EliminarEmpleadoUseCase eliminarEmpleadoUseCase;
    private final EmailService emailService;
    private final ObtenerRanchoUseCase obtenerRanchoUseCase;

    @PostMapping
    public ResponseEntity<EmpleadoResponse> registrarEmpleado(
        @Valid @RequestBody EmpleadoRequest request,
        Authentication auth
    ) {
        try {
            // Validar permisos: solo ADMIN o FARMER
            validarPermiso(auth);
            
            String tenantId = TenantContext.obtenerTenant();
            if (tenantId == null || tenantId.isBlank()) {
                throw new IllegalArgumentException("No autenticado: tenant no encontrado");
            }
            
            // Validar que NO intente crear ADMIN o FARMER
            Rol rolSolicitado = Rol.valueOf(request.rol());
            if (rolSolicitado == Rol.ADMIN || rolSolicitado == Rol.FARMER) {
                throw new IllegalArgumentException(
                    "No puedes asignar el rol " + rolSolicitado + 
                    ". Solo DOCTOR y WORKER están permitidos para empleados."
                );
            }
            
            Empleado empleado = registrarEmpleadoUseCase.ejecutar(
                tenantId,
                request.nombre(),
                request.email(),
                request.telefono(),
                rolSolicitado,
                request.ranchoId()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToResponse(empleado));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoResponse>> listarEmpleados(@RequestParam(required = false) String ranchoId) {
        String tenantId = TenantContext.obtenerTenant();
        List<Empleado> empleados;
        
        if (ranchoId != null && !ranchoId.isBlank()) {
            // Filtrar por rancho específico
            empleados = listarEmpleadosUseCase.ejecutar(ranchoId, tenantId);
        } else {
            // Listar todos del tenant
            empleados = listarEmpleadosUseCase.ejecutar(tenantId);
        }
        
        return ResponseEntity.ok(
            empleados.stream()
                .map(this::mapToResponse)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> obtenerEmpleado(@PathVariable String id) {
        String tenantId = TenantContext.obtenerTenant();
        Empleado empleado = obtenerEmpleadoUseCase.ejecutar(id, tenantId);
        return ResponseEntity.ok(mapToResponse(empleado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpleadoResponse> actualizarEmpleado(
        @PathVariable String id,
        @Valid @RequestBody EmpleadoRequest request
    ) {
        String tenantId = TenantContext.obtenerTenant();
        Empleado empleado = actualizarEmpleadoUseCase.ejecutar(
            id,
            tenantId,
            request.nombre(),
            request.email(),
            request.telefono(),
            Rol.valueOf(request.rol()),
            Estado.valueOf(request.estado()),
            request.ranchoId()
        );
        return ResponseEntity.ok(mapToResponse(empleado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEmpleado(@PathVariable String id) {
        String tenantId = TenantContext.obtenerTenant();
        eliminarEmpleadoUseCase.ejecutar(id, tenantId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/enviar-invitacion")
    public ResponseEntity<MensajeResponse> enviarInvitacion(
        @PathVariable String id
    ) {
        String tenantId = TenantContext.obtenerTenant();
        Empleado empleado = obtenerEmpleadoUseCase.ejecutar(id, tenantId);
        
        emailService.enviarInvitacion(empleado);
        
        return ResponseEntity.ok(
            new MensajeResponse("Invitación enviada exitosamente")
        );
    }

    private EmpleadoResponse mapToResponse(Empleado empleado) {
        String ranchoNombre = null;
        
        if (empleado.getRanchoId() != null && !empleado.getRanchoId().isBlank()) {
            try {
                String tenantId = TenantContext.obtenerTenant();
                var rancho = obtenerRanchoUseCase.ejecutar(empleado.getRanchoId(), tenantId);
                ranchoNombre = rancho.getNombre();
            } catch (Exception e) {
                // Si no se puede obtener el rancho, dejar como null
                ranchoNombre = null;
            }
        }
        
        return new EmpleadoResponse(
            empleado.getId(),
            empleado.getNombre(),
            empleado.getEmail(),
            empleado.getTelefono(),
            empleado.getRol().name(),
            empleado.getEstado().name(),
            empleado.getFechaRegistro(),
            empleado.getRanchoId(),
            ranchoNombre
        );
    }

    private void validarPermiso(Authentication auth) {
        if (auth == null) {
            throw new IllegalArgumentException("No autenticado");
        }
        
        boolean esAdminOFarmer = auth.getAuthorities().stream()
            .anyMatch(a -> 
                a.getAuthority().equals("ROLE_ADMIN") || 
                a.getAuthority().equals("ROLE_FARMER")
            );
        
        if (!esAdminOFarmer) {
            throw new IllegalArgumentException(
                "Solo ADMIN o FARMER pueden gestionar empleados"
            );
        }
    }
}
