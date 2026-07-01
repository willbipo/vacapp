package com.vacapp.insumos.internal.infrastructure.controllers.web;

import com.vacapp.core.TenantContext;
import com.vacapp.insumos.internal.application.usecases.ListarInsumosUseCase;
import com.vacapp.insumos.internal.domain.model.CategoriaInsumo;
import com.vacapp.insumos.internal.domain.model.UnidadMedida;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/** Controlador web que sirve la vista de inventario de insumos. */
@Controller
@RequestMapping("/insumos")
@RequiredArgsConstructor
public class InsumosWebController {

    private final ListarInsumosUseCase listarInsumosUseCase;

    @GetMapping
    public String inventario(Model model) {
        String tenantId = TenantContext.obtenerTenant();
        model.addAttribute("insumos", listarInsumosUseCase.ejecutar(tenantId));
        model.addAttribute("categoriaOpciones", CategoriaInsumo.values());
        model.addAttribute("unidadOpciones", UnidadMedida.values());
        return "insumos/inventario";
    }
}
