// ==========================================
// DASHBOARD.JS - Lógica de negocio para dashboard
// ==========================================

// Verificar autenticación al cargar
document.addEventListener('DOMContentLoaded', function() {
  const token = sessionStorage.getItem('vacapp_token');
  
  if (!token) {
    // Si no hay token, redirigir al login
    window.location.href = '/login';
  }
  
  // Opcional: Validar token con el backend
  // validarToken();
});

// Función para validar token con el backend (opcional)
async function validarToken() {
  const token = sessionStorage.getItem('vacapp_token');
  
  try {
    const response = await fetch('/api/v1/auth/me', {
      method: 'GET',
      headers: {
        'Authorization': `Bearer ${token}`
      }
    });
    
    if (!response.ok) {
      // Token inválido, redirigir al login
      sessionStorage.removeItem('vacapp_token');
      window.location.href = '/login';
    }
  } catch (error) {
    console.error('Error al validar token:', error);
  }
}
