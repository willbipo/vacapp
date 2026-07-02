package com.vacapp.ventas.internal.infrastructure.controllers.mobile;

import com.vacapp.core.TenantContext;
import com.vacapp.ventas.internal.application.usecases.ListarVentasGanadoUseCase;
import com.vacapp.ventas.internal.application.usecases.RegistrarVentaGanadoUseCase;
import com.vacapp.ventas.internal.domain.model.VentaGanado;
import com.vacapp.ventas.internal.infrastructure.controllers.mobile.dtos.VentaGanadoRequest;
import com.vacapp.ventas.internal.infrastructure.controllers.mobile.dtos.VentaGanadoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/** Controlador REST para el módulo de ventas de ganado. */
@RestController
@RequestMapping("/api/v1/ventas-ganado")
@RequiredArgsConstructor
public class VentasGanadoRestController {

    private final RegistrarVentaGanadoUseCase registrarVentaGanadoUseCase;
    private final ListarVentasGanadoUseCase listarVentasGanadoUseCase;

    @PostMapping
    public ResponseEntity<VentaGanadoResponse> registrar(@Valid @RequestBody VentaGanadoRequest req) {
        String tenantId = TenantContext.obtenerTenant();
        VentaGanado venta = registrarVentaGanadoUseCase.ejecutar(new RegistrarVentaGanadoUseCase.Comando(
                req.areteId(),
                req.nombreComprador(),
                req.ine(),
                req.credencialCedafod(),
                req.guiaPdf(),
                req.fechaVenta(),
                tenantId
        ));
        return ResponseEntity.status(HttpStatus.CREATED).body(VentaGanadoResponse.desde(venta));
    }

    @GetMapping
    public ResponseEntity<List<VentaGanadoResponse>> listar() {
        String tenantId = TenantContext.obtenerTenant();
        List<VentaGanadoResponse> lista = listarVentasGanadoUseCase.ejecutar(tenantId)
                .stream().map(VentaGanadoResponse::desde).toList();
        return ResponseEntity.ok(lista);
    }
}
