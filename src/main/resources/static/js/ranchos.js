// ==========================================
// RANCHOS.JS - Lógica de negocio para vista de ranchos
// ==========================================

const API_BASE = '/api/v1';

// Función para obtener el token de la cookie
function getToken() {
  const cookies = document.cookie.split(';');
  for (let cookie of cookies) {
    const [name, value] = cookie.trim().split('=');
    if (name === 'vacapp_jwt') {
      return value;
    }
  }
  return sessionStorage.getItem('vacapp_token');
}

// Función para obtener headers con autenticación
function getAuthHeaders() {
  const token = getToken();
  return {
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${token}`
  };
}

// Cargar datos iniciales
async function cargarDatos() {
  console.log('[RANCHOS] Cargando datos desde API...');
  console.log('[RANCHOS] Token disponible:', !!getToken());
  
  try {
    const response = await fetch(`${API_BASE}/ranchos`, {
      headers: getAuthHeaders()
    });
    
    console.log('[RANCHOS] Response status:', response.status);
    
    if (response.ok) {
      const ranchos = await response.json();
      console.log('[RANCHOS] Ranchos recibidos:', ranchos);
      renderizarRanchos(ranchos);
      actualizarEstadisticas(ranchos);
    } else if (response.status === 401) {
      console.error('[RANCHOS] Error de autenticación - redirigiendo a login');
      window.location.href = '/views/login.html';
    } else {
      console.error('[RANCHOS] Error en respuesta:', response.status, response.statusText);
      const errorText = await response.text();
      console.error('[RANCHOS] Error body:', errorText);
    }
  } catch (error) {
    console.error('[RANCHOS] Error al cargar ranchos:', error);
  }
}

// Renderizar ranchos con jerarquía
function renderizarRanchos(ranchos) {
  const container = document.getElementById('ranchos-container');
  if (!container) {
    console.error('[RANCHOS] No se encontró el elemento ranchos-container');
    return;
  }
  container.innerHTML = '';
  
  if (!ranchos || ranchos.length === 0) {
    container.innerHTML = '<div class="bg-white rounded-lg shadow p-6 text-center"><p class="text-gray-500">No hay ranchos registrados</p></div>';
    return;
  }
  
  ranchos.forEach(rancho => {
    const ranchoCard = crearRanchoCard(rancho);
    container.appendChild(ranchoCard);
  });
}

// Crear tarjeta de rancho con jerarquía
function crearRanchoCard(rancho) {
  const div = document.createElement('div');
  div.className = 'bg-white rounded-lg shadow mb-6';
  
  const seccionesHtml = rancho.secciones && rancho.secciones.length > 0 
    ? rancho.secciones.map(seccion => crearSeccionHtml(seccion)).join('')
    : '';
  
  const potrerosDirectosHtml = rancho.potrerosDirectos && rancho.potrerosDirectos.length > 0
    ? crearPotrerosDirectosHtml(rancho.potrerosDirectos)
    : '';
  
  div.innerHTML = `
    <div class="p-6 border-b flex items-center justify-between">
      <div class="flex items-center gap-3">
        <div class="p-2 bg-blue-100 rounded-lg">
          <svg width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="text-blue-600">
            <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
            <polyline points="9 22 9 12 15 12 15 22"></polyline>
          </svg>
        </div>
        <div>
          <h3 class="text-lg font-semibold">${rancho.nombre}</h3>
          <p class="text-sm text-gray-600">${rancho.descripcion || 'Sin descripción'}</p>
        </div>
      </div>
      <div class="flex gap-2">
        <button class="px-3 py-1 text-sm bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition"
                onclick="abrirModalEditarRancho('${rancho.id}')">Editar</button>
        <button class="px-3 py-1 text-sm bg-blue-600 text-white rounded hover:bg-blue-700 transition"
                onclick="abrirModalCrearSeccion('${rancho.id}')">+ Sección</button>
        <button class="px-3 py-1 text-sm bg-blue-600 text-white rounded hover:bg-blue-700 transition"
                onclick="abrirModalCrearPotrero('${rancho.id}', null)">+ Potrero</button>
      </div>
    </div>
    
    <div class="p-6">
      <div class="grid grid-cols-3 gap-4 mb-4">
        <div>
          <span class="text-sm text-gray-600">Hectáreas totales:</span>
          <span class="font-medium">${rancho.hectareas}</span>
        </div>
        <div>
          <span class="text-sm text-gray-600">Ubicación:</span>
          <span class="font-medium">${rancho.ubicacion || 'Sin especificar'}</span>
        </div>
        <div>
          <span class="text-sm text-gray-600">Hectáreas en uso:</span>
          <span class="font-medium text-green-600">${rancho.hectareasEnUso || 0}</span>
        </div>
      </div>
      
      ${seccionesHtml}
      ${potrerosDirectosHtml}
    </div>
  `;
  
  return div;
}

// Crear HTML de sección
function crearSeccionHtml(seccion) {
  const potrerosHtml = seccion.potreros && seccion.potreros.length > 0
    ? seccion.potreros.map(potrero => crearPotreroHtml(potrero)).join('')
    : '';
  
  return `
    <div class="ml-6 mt-4">
      <div class="bg-white border-l-4 border-blue-300 rounded-lg shadow-sm">
        <div class="p-4 flex items-center justify-between">
          <div class="flex items-center gap-3">
            <div class="p-1 bg-blue-50 rounded">
              <svg width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="text-blue-600">
                <rect x="3" y="3" width="7" height="7"></rect>
                <rect x="14" y="3" width="7" height="7"></rect>
                <rect x="14" y="14" width="7" height="7"></rect>
                <rect x="3" y="14" width="7" height="7"></rect>
              </svg>
            </div>
            <div>
              <h4 class="font-bold">${seccion.nombre}</h4>
              <p class="text-sm text-gray-600">
                ${seccion.potreros ? seccion.potreros.length : 0} potreros • 
                ${seccion.hectareasTotales || 0} ha
              </p>
            </div>
          </div>
          <div class="flex gap-2">
            <button class="px-3 py-1 text-sm bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition"
                    onclick="abrirModalEditarSeccion('${seccion.id}')">Editar</button>
            <button class="px-3 py-1 text-sm bg-blue-600 text-white rounded hover:bg-blue-700 transition"
                    onclick="abrirModalCrearPotrero('${seccion.ranchoId}', '${seccion.id}')">+ Potrero</button>
          </div>
        </div>
        ${potrerosHtml ? `<div class="ml-6 mt-4">${potrerosHtml}</div>` : ''}
      </div>
    </div>
  `;
}

// Crear HTML de potrero
function crearPotreroHtml(potrero) {
  return `
    <div class="bg-white border-l-4 border-indigo-300 rounded-lg shadow-sm mb-3">
      <div class="p-3 flex items-center justify-between">
        <div class="flex items-center gap-3">
          <div class="p-1 bg-indigo-50 rounded">
            <svg width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" class="text-indigo-600">
              <path d="M4 22h16a2 2 0 0 0 2-2V4a2 2 0 0 0-2-2H8a2 2 0 0 0-2 2v16a2 2 0 0 1-2 2Zm0 0a2 2 0 0 1-2-2v-9c0-1.1.9-2 2-2h2"></path>
            </svg>
          </div>
          <div>
            <span class="font-medium">${potrero.nombre}</span>
            <span class="text-sm text-gray-600 ml-2">
              ${potrero.hectareas} ha • ${potrero.tipoPasto || 'Sin pasto'}
            </span>
          </div>
        </div>
        <button class="px-3 py-1 text-sm bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition"
                onclick="abrirModalEditarPotrero('${potrero.id}')">Editar</button>
      </div>
    </div>
  `;
}

// Crear HTML de potreros directos
function crearPotrerosDirectosHtml(potreros) {
  const potrerosHtml = potreros.map(potrero => crearPotreroHtml({...potrero, seccionId: null})).join('');
  return `
    <div class="ml-6 mt-4">
      <h4 class="text-sm font-bold text-gray-600 mb-3">Potreros Directos</h4>
      ${potrerosHtml}
    </div>
  `;
}

// Actualizar estadísticas
function actualizarEstadisticas(ranchos) {
  if (!ranchos) return;
  
  const totalRanchos = ranchos.length;
  const conSecciones = ranchos.filter(r => r.secciones && r.secciones.length > 0).length;
  const conPotrerosDirectos = ranchos.filter(r => r.potrerosDirectos && r.potrerosDirectos.length > 0).length;
  const hectareasEnUso = ranchos.reduce((sum, r) => sum + (r.hectareasEnUso || 0), 0);
  
  const statTotal = document.getElementById('stat-total');
  const statSecciones = document.getElementById('stat-secciones');
  const statPotreros = document.getElementById('stat-potreros');
  const statHectareas = document.getElementById('stat-hectareas');
  
  if (statTotal) statTotal.textContent = totalRanchos;
  if (statSecciones) statSecciones.textContent = conSecciones;
  if (statPotreros) statPotreros.textContent = conPotrerosDirectos;
  if (statHectareas) statHectareas.textContent = hectareasEnUso.toFixed(2);
}

// ===== MODALES =====

function cerrarModal(modalId) {
  document.getElementById(modalId).classList.add('hidden');
  const formId = modalId.replace('modal-', 'formulario-');
  const form = document.getElementById(formId);
  if (form) form.reset();
}

function abrirModalCrearRancho() {
  document.getElementById('modal-rancho-titulo').textContent = 'Nuevo Rancho';
  document.getElementById('rancho-id').value = '';
  document.getElementById('rancho-nombre').value = '';
  document.getElementById('rancho-descripcion').value = '';
  document.getElementById('rancho-hectareas').value = '';
  document.getElementById('rancho-ubicacion').value = '';
  
  document.getElementById('modal-rancho').classList.remove('hidden');
}

async function abrirModalEditarRancho(ranchoId) {
  try {
    const response = await fetch(`${API_BASE}/ranchos/${ranchoId}`, {
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      const rancho = await response.json();
      document.getElementById('modal-rancho-titulo').textContent = 'Editar Rancho';
      document.getElementById('rancho-id').value = rancho.id;
      document.getElementById('rancho-nombre').value = rancho.nombre;
      document.getElementById('rancho-descripcion').value = rancho.descripcion || '';
      document.getElementById('rancho-hectareas').value = rancho.hectareas;
      document.getElementById('rancho-ubicacion').value = rancho.ubicacion || '';
      
      document.getElementById('modal-rancho').classList.remove('hidden');
    }
  } catch (error) {
    console.error('Error al cargar rancho:', error);
  }
}

function abrirModalCrearSeccion(ranchoId) {
  document.getElementById('modal-seccion-titulo').textContent = 'Nueva Sección';
  document.getElementById('seccion-id').value = '';
  document.getElementById('seccion-ranchoId').value = ranchoId;
  document.getElementById('seccion-nombre').value = '';
  
  document.getElementById('modal-seccion').classList.remove('hidden');
}

async function abrirModalEditarSeccion(seccionId) {
  try {
    const response = await fetch(`${API_BASE}/secciones/${seccionId}`, {
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      const seccion = await response.json();
      document.getElementById('modal-seccion-titulo').textContent = 'Editar Sección';
      document.getElementById('seccion-id').value = seccion.id;
      document.getElementById('seccion-nombre').value = seccion.nombre;
      
      document.getElementById('modal-seccion').classList.remove('hidden');
    }
  } catch (error) {
    console.error('Error al cargar sección:', error);
  }
}

function abrirModalCrearPotrero(ranchoId, seccionId) {
  document.getElementById('modal-potrero-titulo').textContent = 'Nuevo Potrero';
  document.getElementById('potrero-id').value = '';
  document.getElementById('potrero-ranchoId').value = ranchoId;
  document.getElementById('potrero-seccionId').value = seccionId || '';
  document.getElementById('potrero-nombre').value = '';
  document.getElementById('potrero-hectareas').value = '';
  document.getElementById('potrero-tipoPasto').value = '';
  
  document.getElementById('modal-potrero').classList.remove('hidden');
}

async function abrirModalEditarPotrero(potreroId) {
  try {
    const response = await fetch(`${API_BASE}/potreros/${potreroId}`, {
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      const potrero = await response.json();
      document.getElementById('modal-potrero-titulo').textContent = 'Editar Potrero';
      document.getElementById('potrero-id').value = potrero.id;
      document.getElementById('potrero-nombre').value = potrero.nombre;
      document.getElementById('potrero-hectareas').value = potrero.hectareas;
      document.getElementById('potrero-tipoPasto').value = potrero.tipoPasto || '';
      document.getElementById('potrero-ranchoId').value = potrero.ranchoId;
      document.getElementById('potrero-seccionId').value = potrero.seccionId || '';
      
      document.getElementById('modal-potrero').classList.remove('hidden');
    }
  } catch (error) {
    console.error('Error al cargar potrero:', error);
  }
}

// ===== GUARDAR =====

async function guardarRancho(event) {
  event.preventDefault();
  console.log('[RANCHOS] Guardando rancho...');
  
  const ranchoId = document.getElementById('rancho-id').value;
  const ranchoData = {
    nombre: document.getElementById('rancho-nombre').value,
    descripcion: document.getElementById('rancho-descripcion').value,
    hectareas: parseFloat(document.getElementById('rancho-hectareas').value),
    ubicacion: document.getElementById('rancho-ubicacion').value
  };
  
  console.log('[RANCHOS] Datos del rancho:', ranchoData);
  
  try {
    const url = ranchoId 
      ? `${API_BASE}/ranchos/${ranchoId}`
      : `${API_BASE}/ranchos`;
    
    const method = ranchoId ? 'PUT' : 'POST';
    
    console.log('[RANCHOS] Enviando', method, 'a', url);
    
    const response = await fetch(url, {
      method: method,
      headers: getAuthHeaders(),
      body: JSON.stringify(ranchoData)
    });
    
    console.log('[RANCHOS] Response status:', response.status);
    
    if (response.ok) {
      console.log('[RANCHOS] Rancho guardado exitosamente');
      cerrarModal('modal-rancho');
      // Agregar delay para permitir que la transacción se comprometa
      setTimeout(() => cargarDatos(), 500);
    } else {
      const error = await response.json();
      console.error('[RANCHOS] Error al guardar rancho:', error);
      alert('Error al guardar rancho: ' + (error.message || 'Error desconocido'));
    }
  } catch (error) {
    console.error('[RANCHOS] Error al guardar rancho:', error);
    alert('Error de conexión al guardar rancho');
  }
}

async function guardarSeccion(event) {
  event.preventDefault();
  console.log('[RANCHOS] Guardando sección...');
  
  const seccionId = document.getElementById('seccion-id').value;
  const ranchoId = document.getElementById('seccion-ranchoId').value;
  const seccionData = {
    nombre: document.getElementById('seccion-nombre').value
  };
  
  console.log('[RANCHOS] Datos de la sección:', seccionData, 'ranchoId:', ranchoId);
  
  try {
    const url = seccionId 
      ? `${API_BASE}/secciones/${seccionId}`
      : `${API_BASE}/ranchos/${ranchoId}/secciones`;
    
    const method = seccionId ? 'PUT' : 'POST';
    
    console.log('[RANCHOS] Enviando', method, 'a', url);
    
    const response = await fetch(url, {
      method: method,
      headers: getAuthHeaders(),
      body: JSON.stringify(seccionData)
    });
    
    console.log('[RANCHOS] Response status:', response.status);
    
    if (response.ok) {
      console.log('[RANCHOS] Sección guardada exitosamente');
      cerrarModal('modal-seccion');
      cargarDatos();
    } else {
      const error = await response.json();
      console.error('[RANCHOS] Error al guardar sección:', error);
      alert('Error al guardar sección: ' + (error.message || 'Error desconocido'));
    }
  } catch (error) {
    console.error('[RANCHOS] Error al guardar sección:', error);
    alert('Error de conexión al guardar sección');
  }
}

async function guardarPotrero(event) {
  event.preventDefault();
  console.log('[RANCHOS] Guardando potrero...');
  
  const potreroId = document.getElementById('potrero-id').value;
  const ranchoId = document.getElementById('potrero-ranchoId').value;
  const seccionId = document.getElementById('potrero-seccionId').value || null;
  const potreroData = {
    nombre: document.getElementById('potrero-nombre').value,
    hectareas: parseFloat(document.getElementById('potrero-hectareas').value),
    tipoPasto: document.getElementById('potrero-tipoPasto').value
  };
  
  console.log('[RANCHOS] Datos del potrero:', potreroData, 'ranchoId:', ranchoId, 'seccionId:', seccionId);
  
  try {
    let url;
    if (potreroId) {
      url = `${API_BASE}/potreros/${potreroId}`;
    } else if (seccionId) {
      url = `${API_BASE}/secciones/${seccionId}/potreros`;
    } else {
      url = `${API_BASE}/ranchos/${ranchoId}/potreros`;
    }
    
    const method = potreroId ? 'PUT' : 'POST';
    
    console.log('[RANCHOS] Enviando', method, 'a', url);
    
    const response = await fetch(url, {
      method: method,
      headers: getAuthHeaders(),
      body: JSON.stringify(potreroData)
    });
    
    console.log('[RANCHOS] Response status:', response.status);
    
    if (response.ok) {
      console.log('[RANCHOS] Potrero guardado exitosamente');
      cerrarModal('modal-potrero');
      cargarDatos();
    } else {
      const error = await response.json();
      console.error('[RANCHOS] Error al guardar potrero:', error);
      alert('Error al guardar potrero: ' + (error.message || 'Error desconocido'));
    }
  } catch (error) {
    console.error('[RANCHOS] Error al guardar potrero:', error);
    alert('Error de conexión al guardar potrero');
  }
}

// ===== EVENTOS GLOBALES =====

// Cerrar modales con tecla Escape
document.addEventListener('keydown', function(event) {
  if (event.key === 'Escape') {
    cerrarModal('modal-rancho');
    cerrarModal('modal-seccion');
    cerrarModal('modal-potrero');
  }
});

// Cerrar modales al hacer clic fuera del contenido (esperar a que el DOM esté cargado)
document.addEventListener('DOMContentLoaded', function() {
  ['modal-rancho', 'modal-seccion', 'modal-potrero'].forEach(modalId => {
    const modal = document.getElementById(modalId);
    if (modal) {
      modal.addEventListener('click', function(event) {
        if (event.target === this) {
          cerrarModal(modalId);
        }
      });
    }
  });
  
  // Cargar datos iniciales
  cargarDatos();
});
