package com.vacapp.empleados.internal.infrastructure.controllers.web;

import com.vacapp.empleados.internal.application.usecases.ListarEmpleadosUseCase;
import com.vacapp.empleados.internal.domain.model.Empleado;
import com.vacapp.core.TenantContext;
import com.vacapp.core.UserContext;
import com.vacapp.ranchos.RanchoService;
import com.vacapp.ranchos.internal.domain.model.Rancho;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controlador Web (Thymeleaf) para la vista de empleados.
 */
@Controller
@RequestMapping("/empleados")
@RequiredArgsConstructor
public class EmpleadoWebController {
    private final ListarEmpleadosUseCase listarEmpleadosUseCase;
    private final RanchoService ranchoService;

    @GetMapping({"", "/"})
    public String listar(@RequestParam(required = false) String ranchoId, Model model) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        // Traer lista de ranchos del usuario
        List<Rancho> ranchos = ranchoService.obtenerRanchosPorUsuario(userId, tenantId);
        model.addAttribute("ranchos", ranchos);
        model.addAttribute("ranchoSeleccionadoId", ranchoId);
        
        // Filtrar empleados por rancho si se especifica
        List<Empleado> empleados;
        if (ranchoId != null && !ranchoId.isBlank()) {
            empleados = listarEmpleadosUseCase.ejecutar(ranchoId, tenantId);
        } else {
            empleados = listarEmpleadosUseCase.ejecutar(tenantId);
        }
        
        model.addAttribute("empleados", empleados);
        return "empleados/inventario";
    }

    @GetMapping("/inventario")
    public String inventario(@RequestParam(required = false) String ranchoId, Model model) {
        String tenantId = TenantContext.obtenerTenant();
        String userId = UserContext.obtenerUsuario();
        
        // Traer lista de ranchos del usuario
        List<Rancho> ranchos = ranchoService.obtenerRanchosPorUsuario(userId, tenantId);
        model.addAttribute("ranchos", ranchos);
        model.addAttribute("ranchoSeleccionadoId", ranchoId);
        
        // Filtrar empleados por rancho si se especifica
        List<Empleado> empleados;
        if (ranchoId != null && !ranchoId.isBlank()) {
            empleados = listarEmpleadosUseCase.ejecutar(ranchoId, tenantId);
        } else {
            empleados = listarEmpleadosUseCase.ejecutar(tenantId);
        }
        
        model.addAttribute("empleados", empleados);
        return "empleados/inventario";
    }
}
