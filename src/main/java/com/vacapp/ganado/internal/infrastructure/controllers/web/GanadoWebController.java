package com.vacapp.ganado.internal.infrastructure.controllers.web;

import com.vacapp.core.TenantContext;
import com.vacapp.core.UserContext;
import com.vacapp.ganado.internal.application.usecases.ListarAnimalesUseCase;
import com.vacapp.ganado.internal.domain.model.Animal;
import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
import com.vacapp.ranchos.RanchoService;
import com.vacapp.ranchos.internal.domain.model.Rancho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/** Controlador web que sirve la vista de inventario de ganado. */
@Controller
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class GanadoWebController {

    private final ListarAnimalesUseCase listarAnimalesUseCase;
    private final RanchoService ranchoService;

    @GetMapping
    public String inventario(@RequestParam(required = false) String ranchoId, Model model) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        // Traer lista de ranchos del usuario
        List<Rancho> ranchos = ranchoService.obtenerRanchosPorUsuario(userId, tenantId);
        model.addAttribute("ranchos", ranchos);
        model.addAttribute("ranchoSeleccionadoId", ranchoId);
        
        // Filtrar animales por rancho si se especifica
        List<Animal> animales;
        if (ranchoId != null && !ranchoId.isBlank()) {
            animales = listarAnimalesUseCase.ejecutar(ranchoId, tenantId);
        } else {
            // Sin rancho específico, mostrar todos
            animales = listarAnimalesUseCase.ejecutar(tenantId);
        }
        
        model.addAttribute("animales", animales);
        model.addAttribute("estatusOpciones", Estatus.values());
        model.addAttribute("sexoOpciones", Sexo.values());
        model.addAttribute("tipoOpciones", Tipo.values());
        return "ganado/inventario";
    }
}
