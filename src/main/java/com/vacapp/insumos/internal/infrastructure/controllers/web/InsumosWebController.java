package com.vacapp.insumos.internal.infrastructure.controllers.web;

import com.vacapp.core.TenantContext;
import com.vacapp.core.UserContext;
import com.vacapp.insumos.internal.application.usecases.ListarInsumosUseCase;
import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import com.vacapp.ranchos.RanchoService;
import com.vacapp.ranchos.internal.domain.model.Rancho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/** Controlador web que sirve la vista de inventario de insumos. */
@Controller
@RequestMapping("/insumos")
@RequiredArgsConstructor
public class InsumosWebController {

    private final ListarInsumosUseCase listarInsumosUseCase;
    private final RanchoService ranchoService;

    @GetMapping
    public String inventario(@RequestParam(required = false) String ranchoId, Model model) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        // Traer lista de ranchos del usuario
        List<Rancho> ranchos = ranchoService.obtenerRanchosPorUsuario(userId, tenantId);
        model.addAttribute("ranchos", ranchos);
        model.addAttribute("ranchoSeleccionadoId", ranchoId);
        
        // Filtrar insumos por rancho si se especifica
        if (ranchoId != null && !ranchoId.isBlank()) {
            model.addAttribute("insumos", listarInsumosUseCase.ejecutar(ranchoId, tenantId));
        } else {
            model.addAttribute("insumos", listarInsumosUseCase.ejecutar(tenantId));
        }
        
        model.addAttribute("categoriaOpciones", CategoriaInsumo.values());
        model.addAttribute("unidadOpciones", UnidadMedida.values());
        return "insumos/inventario";
    }
}
