package com.vacapp.ganado.internal.infrastructure.controllers.web;

import com.vacapp.core.TenantContext;
import com.vacapp.ganado.internal.application.usecases.ListarAnimalesUseCase;
import com.vacapp.ganado.internal.domain.model.Estatus;
import com.vacapp.ganado.internal.domain.model.Sexo;
import com.vacapp.ganado.internal.domain.model.Tipo;
import com.vacapp.ganado.internal.infrastructure.controllers.mobile.dtos.AnimalResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/** Controlador web que sirve la vista de inventario de ganado. */
@Controller
@RequestMapping("/inventario")
@RequiredArgsConstructor
public class GanadoWebController {

    private final ListarAnimalesUseCase listarAnimalesUseCase;

    @GetMapping
    public String inventario(Model model) {
        String tenantId = TenantContext.obtenerTenant();
        List<AnimalResponse> animales = listarAnimalesUseCase.ejecutar(tenantId)
                .stream().map(AnimalResponse::desde).toList();
        model.addAttribute("animales", animales);
        model.addAttribute("estatusOpciones", Estatus.values());
        model.addAttribute("sexoOpciones", Sexo.values());
        model.addAttribute("tipoOpciones", Tipo.values());
        return "ganado/inventario";
    }
}
