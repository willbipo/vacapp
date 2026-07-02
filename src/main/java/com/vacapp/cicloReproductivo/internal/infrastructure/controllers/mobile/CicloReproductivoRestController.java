package com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile;

import com.vacapp.cicloReproductivo.internal.application.usecases.IniciarGestacionUseCase;
import com.vacapp.cicloReproductivo.internal.application.usecases.ListarBecerrosUseCase;
import com.vacapp.cicloReproductivo.internal.application.usecases.ListarCiclosUseCase;
import com.vacapp.cicloReproductivo.internal.application.usecases.RegistrarPartoUseCase;
import com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile.dtos.BecerroResponse;
import com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile.dtos.CicloReproductivoResponse;
import com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile.dtos.IniciarGestacionRequest;
import com.vacapp.cicloReproductivo.internal.infrastructure.controllers.mobile.dtos.RegistrarPartoRequest;
import com.vacapp.core.TenantContext;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

/** Controlador REST del ciclo reproductivo. */
@RestController
@RequestMapping("/api/v1/ciclo-reproductivo")
@RequiredArgsConstructor
public class CicloReproductivoRestController {

    private final IniciarGestacionUseCase iniciarUseCase;
    private final RegistrarPartoUseCase partoUseCase;
    private final ListarCiclosUseCase listarCiclosUseCase;
    private final ListarBecerrosUseCase listarBecerrosUseCase;

    /** POST /api/v1/ciclo-reproductivo — iniciar gestación */
    @PostMapping
    public ResponseEntity<CicloReproductivoResponse> iniciar(@Valid @RequestBody IniciarGestacionRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        var cmd = new IniciarGestacionUseCase.Comando(req.vacaId(), req.fechaInicio(), req.notas(), tenantId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CicloReproductivoResponse.desde(iniciarUseCase.ejecutar(cmd)));
    }

    /** GET /api/v1/ciclo-reproductivo?activos=true|false */
    @GetMapping
    public ResponseEntity<List<CicloReproductivoResponse>> listar(
            @RequestParam(defaultValue = "true") boolean activos) {
        String tenantId = TenantContext.obtenerTenant();
        List<CicloReproductivoResponse> lista = (activos
                ? listarCiclosUseCase.ejecutarActivos(tenantId)
                : listarCiclosUseCase.ejecutarTodos(tenantId))
                .stream().map(CicloReproductivoResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }

    /** GET /api/v1/ciclo-reproductivo/vaca/{vacaId} — historial de una vaca */
    @GetMapping("/vaca/{vacaId}")
    public ResponseEntity<List<CicloReproductivoResponse>> listarPorVaca(@PathVariable UUID vacaId) {
        String tenantId = TenantContext.obtenerTenant();
        return ResponseEntity.ok(
                listarCiclosUseCase.ejecutarPorVaca(vacaId, tenantId)
                        .stream().map(CicloReproductivoResponse::desde).toList());
    }

    /** POST /api/v1/ciclo-reproductivo/{id}/parto — registrar parto */
    @PostMapping("/{id}/parto")
    public ResponseEntity<CicloReproductivoResponse> registrarParto(
            @PathVariable UUID id,
            @Valid @RequestBody RegistrarPartoRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        var cmd = new RegistrarPartoUseCase.Comando(
                id, req.fechaPartoReal(), req.diasReposo(),
                req.nombreBecerro(), req.fechaNacimientoBecerro(), req.sexoBecerro(),
                req.nombrePadre(), req.razaPadre(), req.notasBecerro(), tenantId);
        var resultado = partoUseCase.ejecutar(cmd);
        return ResponseEntity.ok(CicloReproductivoResponse.desde(resultado.ciclo()));
    }

    /** GET /api/v1/ciclo-reproductivo/becerros?madreId=... */
    @GetMapping("/becerros")
    public ResponseEntity<List<BecerroResponse>> listarBecerros(
            @RequestParam(required = false) UUID madreId) {
        String tenantId = TenantContext.obtenerTenant();
        List<BecerroResponse> lista = (madreId != null
                ? listarBecerrosUseCase.ejecutarPorMadre(madreId, tenantId)
                : listarBecerrosUseCase.ejecutarTodos(tenantId))
                .stream().map(BecerroResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }
}
