package com.vacapp.vacunas.internal.infrastructure.controllers.web;

import com.vacapp.core.TenantContext;
import com.vacapp.core.UserContext;
import com.vacapp.vacunas.internal.application.usecases.ListarVacunasUseCase;
import com.vacapp.vacunas.internal.domain.model.TipoVacuna;
import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import com.vacapp.ranchos.RanchoService;
import com.vacapp.ranchos.internal.domain.model.Rancho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/** Controlador web que sirve la vista de inventario de vacunas. */
@Controller
@RequestMapping("/vacunas")
@RequiredArgsConstructor
public class VacunasWebController {

    private final ListarVacunasUseCase listarVacunasUseCase;
    private final RanchoService ranchoService;

    @GetMapping
    public String inventario(@RequestParam(required = false) String ranchoId, Model model) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        // Traer lista de ranchos del usuario
        List<Rancho> ranchos = ranchoService.obtenerRanchosPorUsuario(userId, tenantId);
        model.addAttribute("ranchos", ranchos);
        model.addAttribute("ranchoSeleccionadoId", ranchoId);
        
        // Filtrar vacunas por rancho si se especifica
        if (ranchoId != null && !ranchoId.isBlank()) {
            model.addAttribute("vacunas", listarVacunasUseCase.ejecutar(ranchoId, tenantId));
        } else {
            model.addAttribute("vacunas", listarVacunasUseCase.ejecutar(tenantId));
        }
        
        model.addAttribute("tipoOpciones", TipoVacuna.values());
        model.addAttribute("viaOpciones", ViaAdministracion.values());
        return "vacunas/inventario";
    }
}
