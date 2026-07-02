package com.vacapp.ventas.internal.infrastructure.controllers.web;

import com.vacapp.core.TenantContext;
import com.vacapp.ventas.internal.application.usecases.ListarVentasGanadoUseCase;
import com.vacapp.ventas.internal.domain.model.VentaGanado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDate;
import java.util.List;

/** Controlador web para la vista de ventas de ganado. */
@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentasGanadoWebController {

    private final ListarVentasGanadoUseCase listarVentasGanadoUseCase;

    @GetMapping
    public String verVentas(Model model) {
        String tenantId = TenantContext.obtenerTenant();
        List<VentaGanado> ventas = listarVentasGanadoUseCase.ejecutar(tenantId);

        // Estadísticas para las 4 cards informativas
        long totalVentas = ventas.size();
        long ventasEsteMes = ventas.stream()
                .filter(v -> v.getFechaVenta() != null
                        && v.getFechaVenta().getMonth() == LocalDate.now().getMonth()
                        && v.getFechaVenta().getYear() == LocalDate.now().getYear())
                .count();
        long compradoresUnicos = ventas.stream()
                .map(VentaGanado::getNombreComprador)
                .distinct().count();
        String ultimoComprador = ventas.isEmpty() ? "—" : ventas.get(0).getNombreComprador();

        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVentas", totalVentas);
        model.addAttribute("ventasEsteMes", ventasEsteMes);
        model.addAttribute("compradoresUnicos", compradoresUnicos);
        model.addAttribute("ultimoComprador", ultimoComprador);

        return "ventas/ventasGanado";
    }
}
