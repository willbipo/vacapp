package com.vacapp.ventas.internal.application.usecases;

import com.vacapp.ganado.GanadoService;
import com.vacapp.ventas.internal.domain.model.VentaGanado;
import com.vacapp.ventas.internal.domain.repository.VentaGanadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

/** Caso de uso: registrar una nueva venta de ganado. */
@Service
@RequiredArgsConstructor
public class RegistrarVentaGanadoUseCase {

    private final VentaGanadoRepository ventaGanadoRepository;
    private final GanadoService ganadoService;

    public record Comando(
        String areteId,
        String nombreComprador,
        String ine,
        String credencialCedafod,
        String guiaPdf,
        LocalDate fechaVenta,
        String tenantId
    ) {}

    @Transactional
    public VentaGanado ejecutar(Comando cmd) {
        // Buscar y dar de baja el animal vendido
        ganadoService.darDeBaja(cmd.areteId(), cmd.tenantId());

        VentaGanado venta = VentaGanado.builder()
                .id(UUID.randomUUID())
                .areteId(cmd.areteId())
                .nombreComprador(cmd.nombreComprador())
                .ine(cmd.ine())
                .credencialCedafod(cmd.credencialCedafod())
                .guiaPdf(cmd.guiaPdf())
                .fechaVenta(cmd.fechaVenta() != null ? cmd.fechaVenta() : LocalDate.now())
                .tenantId(cmd.tenantId())
                .build();
        return ventaGanadoRepository.guardar(venta);
    }
}
