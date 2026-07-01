package com.vacapp.core.security;

import com.vacapp.core.TenantContext;
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
 * {@code Authorization: Bearer <token>}, lo valida y configura el
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
        try {
            String token = extraerTokenDeCabecera(request);

            if (StringUtils.hasText(token) && jwtTokenProvider.esValido(token)) {
                String username = jwtTokenProvider.extraerUsername(token);
                String role = jwtTokenProvider.extraerRole(token);
                String tenantId = jwtTokenProvider.extraerTenantId(token);

                // Configura el contexto de Spring Security
                var autoridades = List.of(new SimpleGrantedAuthority("ROLE_" + role));
                var autenticacion = new UsernamePasswordAuthenticationToken(username, null, autoridades);
                SecurityContextHolder.getContext().setAuthentication(autenticacion);

                // Configura el contexto de multi-tenancy
                TenantContext.establecerTenant(tenantId);
            }
        } catch (Exception e) {
            log.error("Error al procesar el token JWT: {}", e.getMessage());
        }

        try {
            filterChain.doFilter(request, response);
        } finally {
            // Limpia el TenantContext al finalizar la petición para evitar fugas entre hilos
            TenantContext.limpiar();
        }
    }

    private String extraerTokenDeCabecera(HttpServletRequest request) {
        // 1. Intentar desde el header Authorization (API / móvil)
        String cabecera = request.getHeader("Authorization");
        if (StringUtils.hasText(cabecera) && cabecera.startsWith("Bearer ")) {
            return cabecera.substring(7);
        }
        // 2. Intentar desde la cookie HttpOnly (navegador web)
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
