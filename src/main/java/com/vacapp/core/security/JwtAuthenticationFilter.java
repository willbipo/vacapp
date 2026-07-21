package com.vacapp.core.security;

import com.vacapp.core.TenantContext;
import com.vacapp.core.UserContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro que intercepta cada petición HTTP, extrae el JWT del header
 * {@code Authorization: Bearer <token>} o cookie, lo valida y configura el
 * {@link SecurityContextHolder} y el {@link TenantContext}.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {
        String uri = request.getRequestURI();
        log.debug("[JWT] Procesando petición: {}", uri);
        
        try {
            String token = extraerTokenDeCabecera(request);
            log.debug("[JWT] Token extraído: {}", token != null ? "presente" : "ausente");

            if (StringUtils.hasText(token) && jwtTokenProvider.esValido(token)) {
                String userId = jwtTokenProvider.extraerUserId(token);
                String username = jwtTokenProvider.extraerUsername(token);
                String role = jwtTokenProvider.extraerRole(token);
                String tenantId = jwtTokenProvider.extraerTenantId(token);

                log.debug("[JWT] Token válido - userId: {}, username: {}, role: {}, tenantId: {}", 
                    userId, username, role, tenantId);

                // Configura el contexto de Spring Security
                var autoridades = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                var autenticacion = new UsernamePasswordAuthenticationToken(username, null, autoridades);
                SecurityContextHolder.getContext().setAuthentication(autenticacion);

                // Configura el contexto de multi-tenancy
                TenantContext.establecerTenant(tenantId);
                
                // Configura el contexto del usuario (userId UUID como identificador)
                UserContext.establecerUsuario(userId);
                UserContext.establecerRol(role);
            } else {
                log.debug("[JWT] Token no válido o ausente, continuando sin autenticación");
            }
        } catch (Exception e) {
            log.error("[JWT] Error al procesar el token JWT: {}", e.getMessage(), e);
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // Limpia los contextos al finalizar la petición para evitar fugas entre hilos
            TenantContext.limpiar();
            UserContext.limpiar();
        }
    }

    private String extraerTokenDeCabecera(HttpServletRequest request) {
        // 1. Intentar desde el header Authorization (API / móvil)
        String cabecera = request.getHeader("Authorization");
        if (StringUtils.hasText(cabecera) && cabecera.startsWith("Bearer ")) {
            return cabecera.substring(7);
        }
        // 2. Intentar desde la cookie (navegador web)
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("vacapp_jwt".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
