package com.vacapp.ranchos.internal.infrastructure.controllers.mobile;

import com.vacapp.ranchos.internal.application.usecases.*;
import com.vacapp.ranchos.internal.domain.model.Potrero;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.PotreroRequest;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.PotreroResponse;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.MensajeResponse;
import com.vacapp.core.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para potreros de ranchos.
 */
@RestController
@RequestMapping("/api/v1/ranchos/{ranchoId}/potreros")
@RequiredArgsConstructor
public class PotreroRestController {
    private final RegistrarPotreroUseCase registrarPotreroUseCase;
    private final ListarPotrerosUseCase listarPotrerosUseCase;

    @PostMapping
    public ResponseEntity<PotreroResponse> registrarPotrero(
        @PathVariable String ranchoId,
        @Valid @RequestBody PotreroRequest request
    ) {
        System.out.println("[POTREROS] POST /api/v1/ranchos/" + ranchoId + "/potreros - Iniciando registro");
        System.out.println("[POTREROS] Request: " + request.nombre() + ", seccionId: " + request.seccionId());
        
        String tenantId = TenantContext.obtenerTenant();
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("No autenticado: tenant no encontrado");
        }
        
        System.out.println("[POTREROS] tenantId: " + tenantId + ", ranchoId: " + ranchoId);
        
        Potrero potrero = registrarPotreroUseCase.ejecutar(
            ranchoId,
            request.seccionId(),
            tenantId,
            request.nombre(),
            request.hectareas(),
            request.tipoPasto()
        );
        
        System.out.println("[POTREROS] Potrero guardado con ID: " + potrero.getId());
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapToResponse(potrero));
    }

    @GetMapping
    public ResponseEntity<List<PotreroResponse>> listarPotreros(
        @PathVariable String ranchoId
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        List<Potrero> potreros = listarPotrerosUseCase.ejecutar(ranchoId, tenantId);
        return ResponseEntity.ok(
            potreros.stream()
                .map(this::mapToResponse)
                .toList()
        );
    }

    private PotreroResponse mapToResponse(Potrero potrero) {
        return new PotreroResponse(
            potrero.getId(),
            potrero.getRanchoId(),
            potrero.getSeccionId().orElse(null),
            potrero.getNombre(),
            potrero.getHectareas(),
            potrero.getTipoPasto(),
            null, // fechaRegistro no está en el modelo de dominio
            null  // fechaActualizacion no está en el modelo de dominio
        );
    }
}

@RestController
@RequestMapping("/api/v1/secciones/{seccionId}/potreros")
@RequiredArgsConstructor
class PotreroPorSeccionRestController {
    private final RegistrarPotreroUseCase registrarPotreroUseCase;
    private final ListarPotrerosPorSeccionUseCase listarPotrerosPorSeccionUseCase;

    @PostMapping
    public ResponseEntity<PotreroResponse> registrarPotreroEnSeccion(
        @PathVariable String seccionId,
        @Valid @RequestBody PotreroRequest request
    ) {
        System.out.println("[POTREROS] POST /api/v1/secciones/" + seccionId + "/potreros - Iniciando registro");
        System.out.println("[POTREROS] Request: " + request.nombre());
        
        String tenantId = TenantContext.obtenerTenant();
        if (tenantId == null || tenantId.isBlank()) {
            throw new IllegalArgumentException("No autenticado: tenant no encontrado");
        }
        
        System.out.println("[POTREROS] tenantId: " + tenantId + ", seccionId: " + seccionId);
        
        // Obtener el ranchoId de la sección primero
        // Por ahora, asumimos que el request incluye el ranchoId
        Potrero potrero = registrarPotreroUseCase.ejecutar(
            request.ranchoId(),
            seccionId,
            tenantId,
            request.nombre(),
            request.hectareas(),
            request.tipoPasto()
        );
        
        System.out.println("[POTREROS] Potrero guardado con ID: " + potrero.getId());
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(mapToResponse(potrero));
    }

    @GetMapping
    public ResponseEntity<List<PotreroResponse>> listarPotrerosPorSeccion(
        @PathVariable String seccionId
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        List<Potrero> potreros = listarPotrerosPorSeccionUseCase.ejecutar(seccionId, tenantId);
        return ResponseEntity.ok(
            potreros.stream()
                .map(this::mapToResponse)
                .toList()
        );
    }

    private PotreroResponse mapToResponse(Potrero potrero) {
        return new PotreroResponse(
            potrero.getId(),
            potrero.getRanchoId(),
            potrero.getSeccionId().orElse(null),
            potrero.getNombre(),
            potrero.getHectareas(),
            potrero.getTipoPasto(),
            null,
            null
        );
    }
}

@RestController
@RequestMapping("/api/v1/potreros")
@RequiredArgsConstructor
class PotreroIndividualRestController {
    private final ObtenerPotreroUseCase obtenerPotreroUseCase;
    private final ActualizarPotreroUseCase actualizarPotreroUseCase;
    private final EliminarPotreroUseCase eliminarPotreroUseCase;

    @GetMapping("/{id}")
    public ResponseEntity<PotreroResponse> obtenerPotrero(
        @PathVariable String id
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        Potrero potrero = obtenerPotreroUseCase.ejecutar(id, tenantId);
        return ResponseEntity.ok(mapToResponse(potrero));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PotreroResponse> actualizarPotrero(
        @PathVariable String id,
        @Valid @RequestBody PotreroRequest request
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        Potrero actualizado = actualizarPotreroUseCase.ejecutar(
            id,
            tenantId,
            request.nombre(),
            request.hectareas(),
            request.tipoPasto()
        );
        return ResponseEntity.ok(mapToResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarPotrero(
        @PathVariable String id
    ) {
        String tenantId = TenantContext.obtenerTenant();
        
        eliminarPotreroUseCase.ejecutar(id, tenantId);
        return ResponseEntity.ok(new MensajeResponse("Potrero eliminado exitosamente"));
    }

    private PotreroResponse mapToResponse(Potrero potrero) {
        return new PotreroResponse(
            potrero.getId(),
            potrero.getRanchoId(),
            potrero.getSeccionId().orElse(null),
            potrero.getNombre(),
            potrero.getHectareas(),
            potrero.getTipoPasto(),
            null,
            null
        );
    }
}
