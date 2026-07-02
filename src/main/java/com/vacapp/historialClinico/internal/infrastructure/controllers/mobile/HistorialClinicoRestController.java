package com.vacapp.historialClinico.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.historialClinico.internal.application.usecases.ListarHistorialPorAnimalUseCase;
import com.vacapp.historialClinico.internal.application.usecases.RegistrarHistorialClinicoUseCase;
import com.vacapp.historialClinico.internal.domain.repository.HistorialClinicoRepository;
import com.vacapp.historialClinico.internal.infrastructure.controllers.mobile.dtos.HistorialClinicoRequest;
import com.vacapp.historialClinico.internal.infrastructure.controllers.mobile.dtos.HistorialClinicoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/** Controlador REST para el historial clínico de ganado. */
@RestController
@RequestMapping("/api/v1/historial-clinico")
@RequiredArgsConstructor
public class HistorialClinicoRestController {

    private final RegistrarHistorialClinicoUseCase registrarUseCase;
    private final ListarHistorialPorAnimalUseCase listarPorAnimalUseCase;
    private final HistorialClinicoRepository historialClinicoRepository;

    /**
     * Registra un nuevo evento clínico.
     * POST /api/v1/historial-clinico
     */
    @PostMapping
    public ResponseEntity<HistorialClinicoResponse> registrar(
            @Valid @RequestBody HistorialClinicoRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        var cmd = new RegistrarHistorialClinicoUseCase.Comando(
                req.animalId(), req.vacunaId(), req.nombreVacuna(),
                req.dosis(), req.viaAdministracion(), req.lote(),
                req.fechaAplicacion(), req.proximaDosis(), req.notas(), req.aplicadoPor(), tenantId
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(HistorialClinicoResponse.desde(registrarUseCase.ejecutar(cmd)));
    }

    /**
     * Retorna el historial clínico de un animal específico.
     * GET /api/v1/historial-clinico/animal/{animalId}
     */
    @GetMapping("/animal/{animalId}")
    public ResponseEntity<List<HistorialClinicoResponse>> listarPorAnimal(
            @PathVariable UUID animalId) {
        String tenantId = TenantContext.obtenerTenant();
        List<HistorialClinicoResponse> lista = listarPorAnimalUseCase
                .ejecutar(animalId, tenantId)
                .stream().map(HistorialClinicoResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    /**
     * Retorna todos los registros con próxima dosis programada (para el calendario).
     * GET /api/v1/historial-clinico/proximas-dosis
     */
    @GetMapping("/proximas-dosis")
    public ResponseEntity<List<HistorialClinicoResponse>> listarProximasDosis() {
        String tenantId = TenantContext.obtenerTenant();
        List<HistorialClinicoResponse> lista = historialClinicoRepository
                .listarProximasDosis(tenantId)
                .stream().map(HistorialClinicoResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }
}
