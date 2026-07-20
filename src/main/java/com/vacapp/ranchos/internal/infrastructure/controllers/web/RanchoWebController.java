package com.vacapp.ranchos.internal.infrastructure.controllers.web;

import com.vacapp.ranchos.internal.application.usecases.ListarRanchosUseCase;
import com.vacapp.core.TenantContext;
import com.vacapp.core.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controlador Web (Thymeleaf) para ranchos.
 */
@Controller
@RequestMapping("/ranchos")
@RequiredArgsConstructor
public class RanchoWebController {
    private final ListarRanchosUseCase listarRanchosUseCase;

    @GetMapping
    public String ranchos(Model model) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        var ranchos = listarRanchosUseCase.ejecutar(userId, tenantId);
        model.addAttribute("ranchos", ranchos);
        
        return "ranchos/inventario";
    }

    @GetMapping("/")
    public String ranchosRoot(Model model) {
        return ranchos(model);
    }

    @GetMapping("/inventario")
    public String ranchosInventario(Model model) {
        return ranchos(model);
    }
}
