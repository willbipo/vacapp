package com.vacapp.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador raíz de vistas Thymeleaf.
 * Gestiona las rutas principales de la aplicación web.
 */
@Controller
public class SpaFallbackController {

    /**
     * Redirige la raíz a la página de login.
     */
    @GetMapping("/")
    public String raiz() {
        return "redirect:/login";
    }

    /**
     * Muestra la página de inicio de sesión.
     */
    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    /**
     * Muestra el panel de control principal.
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard/index";
    }
}
