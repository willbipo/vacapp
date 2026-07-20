package com.vacapp.core.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador raíz de vistas HTML estáticas.
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
        return "forward:/views/auth/login.html";
    }

    /**
     * Muestra el panel de control principal.
     */
    @GetMapping("/dashboard")
    public String dashboard() {
        return "forward:/views/dashboard.html";
    }

    /**
     * Muestra la página de despedida tras cerrar sesión.
     */
    @GetMapping("/salida")
    public String adios() {
        return "forward:/views/auth/logout.html";
    }
}
