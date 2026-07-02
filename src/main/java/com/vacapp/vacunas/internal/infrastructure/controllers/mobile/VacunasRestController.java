package com.vacapp.vacunas.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.vacunas.internal.application.usecases.ActualizarVacunaUseCase;
import com.vacapp.vacunas.internal.application.usecases.ListarVacunasUseCase;
import com.vacapp.vacunas.internal.application.usecases.RegistrarVacunaUseCase;
import com.vacapp.vacunas.internal.domain.model.Vacuna;
import com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos.VacunaRequest;
import com.vacapp.vacunas.internal.infrastructure.controllers.mobile.dtos.VacunaResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/**
 * Controlador REST del inventario de vacunas.
 * Implementa la interfaz generada desde openapi-vacunas.yaml (Design-First).
 * Sin anotaciones Swagger propias — el contrato vive en el YAML.
 */
@RestController
@RequiredArgsConstructor
public class VacunasRestController implements VacunasApi {

    private final RegistrarVacunaUseCase registrarVacunaUseCase;
    private final ListarVacunasUseCase listarVacunasUseCase;
    private final ActualizarVacunaUseCase actualizarVacunaUseCase;

    @Override
    public ResponseEntity<VacunaResponse> registrarVacuna(@Valid @RequestBody VacunaRequest vacunaRequest) {
        String tenantId = TenantContext.obtenerTenant();
        Vacuna vacuna = registrarVacunaUseCase.ejecutar(new RegistrarVacunaUseCase.Comando(
                vacunaRequest.nombre(), vacunaRequest.tipo(), vacunaRequest.laboratorio(),
                vacunaRequest.descripcion(), vacunaRequest.dosis(), vacunaRequest.viaAdministracion(),
                vacunaRequest.lote(), vacunaRequest.fechaCaducidad(), vacunaRequest.stock(),
                vacunaRequest.unidadMedida(), vacunaRequest.temperaturaAlmacenamiento(),
                vacunaRequest.intervaloDias(), tenantId
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(VacunaResponse.desde(vacuna));
    }

    @Override
    public ResponseEntity<List<VacunaResponse>> listarVacunas() {
        String tenantId = TenantContext.obtenerTenant();
        List<VacunaResponse> lista = listarVacunasUseCase.ejecutar(tenantId)
                .stream().map(VacunaResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    @Override
    public ResponseEntity<VacunaResponse> actualizarVacuna(@PathVariable("id") UUID id,
                                                            @Valid @RequestBody VacunaRequest vacunaRequest) {
        String tenantId = TenantContext.obtenerTenant();
        Vacuna vacuna = actualizarVacunaUseCase.ejecutar(new ActualizarVacunaUseCase.Comando(
                id, vacunaRequest.nombre(), vacunaRequest.tipo(), vacunaRequest.laboratorio(),
                vacunaRequest.descripcion(), vacunaRequest.dosis(), vacunaRequest.viaAdministracion(),
                vacunaRequest.lote(), vacunaRequest.fechaCaducidad(), vacunaRequest.stock(),
                vacunaRequest.unidadMedida(), vacunaRequest.temperaturaAlmacenamiento(),
                vacunaRequest.intervaloDias(), tenantId
        ));
        return ResponseEntity.ok(VacunaResponse.desde(vacuna));
    }
}
