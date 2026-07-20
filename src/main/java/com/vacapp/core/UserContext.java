package com.vacapp.core;

/**
 * Almacena el {@code userId} del usuario autenticado en un {@link ThreadLocal}
 * para que esté disponible durante todo el ciclo de vida de la petición HTTP.
 *
 * <p>El {@link com.vacapp.core.security.JwtAuthenticationFilter} es responsable de
 * establecer el userId al inicio de cada petición y de limpiarlo al finalizar.</p>
 */
public final class UserContext {

    private static final ThreadLocal<String> usuarioActual = new ThreadLocal<>();

    private UserContext() {}

    /** Establece el userId para la petición actual. */
    public static void establecerUsuario(String userId) {
        usuarioActual.set(userId);
    }

    /**
     * Retorna el {@code userId} del usuario autenticado en el hilo actual.
     * Retorna {@code null} si no hay ningún usuario configurado.
     */
    public static String obtenerUsuario() {
        return usuarioActual.get();
    }

    /** Elimina el usuario del contexto del hilo actual. Llamar siempre en el bloque finally del filtro. */
    public static void limpiar() {
        usuarioActual.remove();
    }
}
