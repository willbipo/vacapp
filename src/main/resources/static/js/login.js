// Función para mostrar error
function mostrarError(msg) {
  const el = document.getElementById('alerta-error');
  document.getElementById('mensaje-error').textContent = msg;
  el.classList.remove('hidden');
}

// Función para cerrar error
function cerrarError() {
  document.getElementById('alerta-error').classList.add('hidden');
}

// Alternar visibilidad de contraseña
function alternarPassword() {
  const campo = document.getElementById('password');
  campo.type = campo.type === 'password' ? 'text' : 'password';
}

// Event listener para el formulario de login
const formularioLogin = document.getElementById('formulario-login');
if (formularioLogin) {
  formularioLogin.addEventListener('submit', async (e) => {
    e.preventDefault();
    cerrarError();

    const usuario = document.getElementById('username').value.trim();
    const contrasena = document.getElementById('password').value;
    const boton = document.getElementById('btn-login');
    const texto = document.getElementById('texto-btn');
    const spinner = document.getElementById('spinner');

    if (!usuario || !contrasena) {
      mostrarError('Por favor completa todos los campos.');
      return;
    }

    boton.disabled = true;
    texto.textContent = 'Iniciando sesión...';
    spinner.classList.remove('hidden');

    try {
      const res = await fetch('/api/v1/auth/login', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: usuario, password: contrasena }),
      });

      if (!res.ok) {
        const data = await res.json().catch(() => ({}));
        mostrarError(data.mensaje || 'Credenciales inválidas. Inténtalo de nuevo.');
        return;
      }

      const data = await res.json();
      sessionStorage.setItem('vacapp_token', data.token);
      localStorage.setItem('vacapp_token', data.token);
      // Cookie para que el filtro JWT funcione en navegación del browser
      document.cookie = `vacapp_jwt=${data.token}; path=/; SameSite=Lax; max-age=86400`;
      window.location.href = '/dashboard';
    } catch {
      mostrarError('No se pudo conectar con el servidor. Verifica tu conexión.');
    } finally {
      boton.disabled = false;
      texto.textContent = 'Iniciar sesión';
      spinner.classList.add('hidden');
    }
  });
}

// Event listener para cerrar error
const btnErrorClose = document.getElementById('btn-error-close');
if (btnErrorClose) {
  btnErrorClose.addEventListener('click', cerrarError);
}

// Event listener para alternar contraseña
const btnPasswordToggle = document.getElementById('btn-password-toggle');
if (btnPasswordToggle) {
  btnPasswordToggle.addEventListener('click', alternarPassword);
}
