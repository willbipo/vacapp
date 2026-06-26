package com.vacapp.core.multitenancy;

/**
 * Almacena el {@code tenantId} del usuario autenticado en un {@link ThreadLocal}
 * para que esté disponible durante todo el ciclo de vida de la petición HTTP.
 *
 * <p>El {@link com.vacapp.core.security.JwtAuthenticationFilter} es responsable de
 * establecer el tenant al inicio de cada petición y de limpiarlo al finalizar.</p>
 */
public final class TenantContext {

    private static final ThreadLocal<String> tenantActual = new ThreadLocal<>();

    private TenantContext() {}

    /** Establece el tenant para la petición actual. */
    public static void establecerTenant(String tenantId) {
        tenantActual.set(tenantId);
    }

    /**
     * Retorna el {@code tenantId} del usuario autenticado en el hilo actual.
     * Retorna {@code null} si no hay ningún tenant configurado.
     */
    public static String obtenerTenant() {
        return tenantActual.get();
    }

    /** Elimina el tenant del contexto del hilo actual. Llamar siempre en el bloque finally del filtro. */
    public static void limpiar() {
        tenantActual.remove();
    }
}
