package com.vacapp.historialClinico.internal.infrastructure.controllers.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

/** Controlador web para la vista de historial clínico. */
@Controller
@RequestMapping("/historial-clinico")
@RequiredArgsConstructor
public class HistorialClinicoWebController {

    @GetMapping
    public String historialClinico(Model model, Principal principal) {
        if (principal != null) {
            model.addAttribute("usuarioActivo", principal.getName());
        }
        return "historialClinico/historialClinico";
    }
}
