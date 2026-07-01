package com.vacapp.vacunas.internal.infrastructure.controllers.web;

import com.vacapp.core.TenantContext;
import com.vacapp.vacunas.internal.application.usecases.ListarVacunasUseCase;
import com.vacapp.vacunas.internal.domain.model.TipoVacuna;
import com.vacapp.vacunas.internal.domain.model.ViaAdministracion;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** Controlador web que sirve la vista de inventario de vacunas. */
@Controller
@RequestMapping("/vacunas")
@RequiredArgsConstructor
public class VacunasWebController {

    private final ListarVacunasUseCase listarVacunasUseCase;

    @GetMapping
    public String inventario(Model model) {
        String tenantId = TenantContext.obtenerTenant();
        model.addAttribute("vacunas", listarVacunasUseCase.ejecutar(tenantId));
        model.addAttribute("tipoOpciones", TipoVacuna.values());
        model.addAttribute("viaOpciones", ViaAdministracion.values());
        return "vacunas/inventario";
    }
}
