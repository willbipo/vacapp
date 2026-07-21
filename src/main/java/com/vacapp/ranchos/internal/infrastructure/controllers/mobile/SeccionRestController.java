package com.vacapp.ranchos.internal.infrastructure.controllers.mobile;

import com.vacapp.ranchos.internal.application.usecases.*;
import com.vacapp.ranchos.internal.domain.model.Seccion;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.SeccionRequest;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.SeccionResponse;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.MensajeResponse;
import com.vacapp.core.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para secciones de ranchos.
 */
@RestController
@RequestMapping("/api/v1/ranchos/{ranchoId}/secciones")
@RequiredArgsConstructor
public class SeccionRestController {
    private final RegistrarSeccionUseCase registrarSeccionUseCase;
    private final ListarSeccionesUseCase listarSeccionesUseCase;

    @PostMapping
    public ResponseEntity<SeccionResponse> registrarSeccion(
        @PathVariable String ranchoId,
        @Valid @RequestBody SeccionRequest request
    ) {
        System.out.println("[SECCIONES] POST /api/v1/ranchos/" + ranchoId + "/secciones - Iniciando registro");
        System.out.println("[SECCIONES] Request: " + request.nombre());
        
        String tenantId = TenantContext.obtenerTenant();
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("No autenticado: tenant no encontrado");
        }
        
        System.out.println("[SECCIONES] tenantId: " + tenantId + ", ranchoId: " + ranchoId);
        
        Seccion seccion = registrarSeccionUseCase.ejecutar(
            ranchoId,
            tenantId,
            request.nombre()
        );
        
        System.out.println("[SECCIONES] Sección guardada con ID: " + seccion.getId());
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapToResponse(seccion));
    }

    @GetMapping
    public ResponseEntity<List<SeccionResponse>> listarSecciones(
        @PathVariable String ranchoId
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        List<Seccion> secciones = listarSeccionesUseCase.ejecutar(ranchoId, tenantId);
        return ResponseEntity.ok(
            secciones.stream()
                .map(this::mapToResponse)
                .toList()
        );
    }

    private SeccionResponse mapToResponse(Seccion seccion) {
        return new SeccionResponse(
            seccion.getId(),
            seccion.getRanchoId(),
            seccion.getNombre(),
            null, // fechaRegistro no está en el modelo de dominio
            null  // fechaActualizacion no está en el modelo de dominio
        );
    }
}

@RestController
@RequestMapping("/api/v1/secciones")
@RequiredArgsConstructor
class SeccionIndividualRestController {
    private final ObtenerSeccionUseCase obtenerSeccionUseCase;
    private final ActualizarSeccionUseCase actualizarSeccionUseCase;
    private final EliminarSeccionUseCase eliminarSeccionUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<SeccionResponse> obtenerSeccion(
        @PathVariable String id
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        Seccion seccion = obtenerSeccionUseCase.ejecutar(id, tenantId);
        return ResponseEntity.ok(mapToResponse(seccion));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeccionResponse> actualizarSeccion(
        @PathVariable String id,
        @Valid @RequestBody SeccionRequest request
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        Seccion actualizada = actualizarSeccionUseCase.ejecutar(
            id,
            tenantId,
            request.nombre()
        );
        return ResponseEntity.ok(mapToResponse(actualizada));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarSeccion(
        @PathVariable String id
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        eliminarSeccionUseCase.ejecutar(id, tenantId);
        return ResponseEntity.ok(new MensajeResponse("Sección eliminada exitosamente"));
    }

    private SeccionResponse mapToResponse(Seccion seccion) {
        return new SeccionResponse(
            seccion.getId(),
            seccion.getRanchoId(),
            seccion.getNombre(),
            null, // fechaRegistro no está en el modelo de dominio
            null  // fechaActualizacion no está en el modelo de dominio
        );
    }
}
