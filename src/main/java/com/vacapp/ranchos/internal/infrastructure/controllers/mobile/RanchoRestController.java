package com.vacapp.ranchos.internal.infrastructure.controllers.mobile;

import com.vacapp.ranchos.internal.application.usecases.*;
import com.vacapp.ranchos.internal.domain.model.Rancho;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.RanchoRequest;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.RanchoResponse;
import com.vacapp.ranchos.internal.infrastructure.controllers.mobile.dtos.MensajeResponse;
import com.vacapp.core.TenantContext;
import com.vacapp.core.UserContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para ranchos.
 * Permite a usuarios FARMER crear y administrar múltiples ranchos.
 */
@RestController
@RequestMapping("/api/v1/ranchos")
@RequiredArgsConstructor
public class RanchoRestController {
    private final RegistrarRanchoUseCase registrarRanchoUseCase;
    private final ListarRanchosUseCase listarRanchosUseCase;
    private final ActualizarRanchoUseCase actualizarRanchoUseCase;
    private final ObtenerRanchoUseCase obtenerRanchoUseCase;
    private final EliminarRanchoUseCase eliminarRanchoUseCase;

    @PostMapping
    public ResponseEntity<RanchoResponse> registrarRancho(
        @Valid @RequestBody RanchoRequest request,
        Authentication auth
    ) {
        try {
            validarPermiso(auth);
            
            String tenantId = TenantContext.obtenerTenant();
            if (tenantId == null || tenantId.isBlank()) {
                throw new IllegalArgumentException("No autenticado: tenant no encontrado");
            }
            
            String userId = UserContext.obtenerUsuario();
            if (userId == null) {
                throw new IllegalArgumentException("No autenticado: usuario no encontrado");
            }
            
            Rancho rancho = registrarRanchoUseCase.ejecutar(
                tenantId,
                userId,
                request.nombre(),
                request.descripcion(),
                request.hectareas(),
                request.ubicacion()
            );
            return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapToResponse(rancho));
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<List<RanchoResponse>> listarRanchos() {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        List<Rancho> ranchos = listarRanchosUseCase.ejecutar(userId, tenantId);
        return ResponseEntity.ok(
            ranchos.stream()
                .map(this::mapToResponse)
                .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<RanchoResponse> obtenerRancho(
        @PathVariable String id
    ) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        Rancho rancho = obtenerRanchoUseCase.ejecutar(id, tenantId);
        
        // Verificar que el usuario es propietario del rancho
        validarPropietario(userId, rancho);
        
        return ResponseEntity.ok(mapToResponse(rancho));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RanchoResponse> actualizarRancho(
        @PathVariable String id,
        @Valid @RequestBody RanchoRequest request
    ) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        Rancho rancho = obtenerRanchoUseCase.ejecutar(id, tenantId);
        
        // Verificar propietario
        validarPropietario(userId, rancho);
        
        Rancho actualizado = actualizarRanchoUseCase.ejecutar(
            id,
            tenantId,
            request.nombre(),
            request.descripcion(),
            request.hectareas(),
            request.ubicacion()
        );
        return ResponseEntity.ok(mapToResponse(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensajeResponse> eliminarRancho(
        @PathVariable String id
    ) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        Rancho rancho = obtenerRanchoUseCase.ejecutar(id, tenantId);
        
        // Verificar propietario
        validarPropietario(userId, rancho);
        
        eliminarRanchoUseCase.ejecutar(id, tenantId);
        return ResponseEntity.ok(new MensajeResponse("Rancho eliminado exitosamente"));
    }

    private RanchoResponse mapToResponse(Rancho rancho) {
        return new RanchoResponse(
            rancho.getId(),
            rancho.getNombre(),
            rancho.getDescripcion(),
            rancho.getHectareas(),
            rancho.getUbicacion(),
            rancho.getFechaRegistro(),
            rancho.getFechaActualizacion()
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
                "Solo ADMIN o FARMER pueden crear ranchos"
            );
        }
    }

    private void validarPropietario(String userId, Rancho rancho) {
        if (!rancho.getUserId().equals(userId)) {
            throw new IllegalArgumentException(
                "No tienes permiso para acceder este rancho"
            );
        }
    }
}
