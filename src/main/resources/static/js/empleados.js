// ==========================================
// EMPLEADOS.JS - Lógica de negocio para vista de empleados
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
  try {
    // Cargar ranchos para el filtro
    const ranchosResponse = await fetch(`${API_BASE}/ranchos`, {
      headers: getAuthHeaders()
    });
    if (ranchosResponse.ok) {
      const ranchos = await ranchosResponse.json();
      poblarSelectRanchos(ranchos);
    }

    // Cargar empleados
    const empleadosResponse = await fetch(`${API_BASE}/empleados`, {
      headers: getAuthHeaders()
    });
    if (empleadosResponse.ok) {
      const empleados = await empleadosResponse.json();
      renderizarTablaEmpleados(empleados);
      actualizarEstadisticas(empleados);
    }
  } catch (error) {
    console.error('Error al cargar datos:', error);
  }
}

// Poblar select de ranchos
function poblarSelectRanchos(ranchos) {
  const filtroSelect = document.getElementById('filtro-rancho');
  const modalSelect = document.getElementById('empleado-rancho');
  
  // Limpiar opciones existentes (excepto la primera que es el placeholder)
  while (filtroSelect.options.length > 1) {
    filtroSelect.remove(1);
  }
  while (modalSelect.options.length > 1) {
    modalSelect.remove(1);
  }
  
  ranchos.forEach(rancho => {
    const option = document.createElement('option');
    option.value = rancho.id;
    option.textContent = rancho.nombre;
    filtroSelect.appendChild(option);
    modalSelect.appendChild(option.cloneNode(true));
  });
}

// Renderizar tabla de empleados
function renderizarTablaEmpleados(empleados) {
  const tbody = document.getElementById('tabla-empleados');
  tbody.innerHTML = '';
  
  if (empleados.length === 0) {
    tbody.innerHTML = '<tr><td colspan="8" class="px-6 py-8 text-center text-gray-500">No hay empleados registrados</td></tr>';
    return;
  }
  
  empleados.forEach(empleado => {
    const tr = document.createElement('tr');
    tr.innerHTML = `
      <td class="px-6 py-4 whitespace-nowrap">
        <span class="font-medium">${empleado.nombre}</span>
      </td>
      <td class="px-6 py-4 whitespace-nowrap">${empleado.email}</td>
      <td class="px-6 py-4 whitespace-nowrap">${empleado.telefono || '-'}</td>
      <td class="px-6 py-4 whitespace-nowrap">
        <span class="px-2 py-1 text-xs font-semibold rounded-full bg-blue-100 text-blue-800">${empleado.rol}</span>
      </td>
      <td class="px-6 py-4 whitespace-nowrap">
        ${getBadgeEstado(empleado.estado)}
      </td>
      <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">${empleado.ranchoNombre || 'Sin asignar'}</td>
      <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">${formatearFecha(empleado.fechaRegistro)}</td>
      <td class="px-6 py-4 whitespace-nowrap text-right">
        <div class="flex justify-end gap-2">
          <button class="px-3 py-1 text-sm bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition"
                  onclick="abrirModalEditar('${empleado.id}')">Editar</button>
          <button class="px-3 py-1 text-sm bg-red-600 text-white rounded hover:bg-red-700 transition"
                  onclick="eliminarEmpleado('${empleado.id}')">Eliminar</button>
        </div>
      </td>
    `;
    tbody.appendChild(tr);
  });
}

// Obtener badge según estado
function getBadgeEstado(estado) {
  const badges = {
    'ACTIVO': '<span class="px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">Activo</span>',
    'PENDIENTE': '<span class="px-2 py-1 text-xs font-semibold rounded-full bg-yellow-100 text-yellow-800">Pendiente</span>',
    'INACTIVO': '<span class="px-2 py-1 text-xs font-semibold rounded-full bg-red-100 text-red-800">Inactivo</span>'
  };
  return badges[estado] || badges['INACTIVO'];
}

// Formatear fecha
function formatearFecha(fecha) {
  if (!fecha) return '-';
  const date = new Date(fecha);
  return date.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' });
}

// Actualizar estadísticas
function actualizarEstadisticas(empleados) {
  document.getElementById('stat-total').textContent = empleados.length;
  document.getElementById('stat-activos').textContent = empleados.filter(e => e.estado === 'ACTIVO').length;
  document.getElementById('stat-pendientes').textContent = empleados.filter(e => e.estado === 'PENDIENTE').length;
  document.getElementById('stat-inactivos').textContent = empleados.filter(e => e.estado === 'INACTIVO').length;
}

// Filtrar por rancho
function filtrarPorRancho() {
  const ranchoId = document.getElementById('filtro-rancho').value;
  cargarEmpleadosFiltrados(ranchoId);
}

// Cargar empleados filtrados
async function cargarEmpleadosFiltrados(ranchoId) {
  try {
    const url = ranchoId 
      ? `${API_BASE}/empleados?ranchoId=${ranchoId}`
      : `${API_BASE}/empleados`;
    
    const response = await fetch(url, {
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      const empleados = await response.json();
      renderizarTablaEmpleados(empleados);
      actualizarEstadisticas(empleados);
    }
  } catch (error) {
    console.error('Error al cargar empleados filtrados:', error);
  }
}

// Limpiar filtros
function limpiarFiltros() {
  document.getElementById('filtro-rancho').value = '';
  cargarDatos();
}

// Abrir modal para crear empleado
function abrirModalCrear() {
  document.getElementById('modal-titulo').textContent = 'Nuevo Empleado';
  document.getElementById('empleado-id').value = '';
  document.getElementById('empleado-nombre').value = '';
  document.getElementById('empleado-email').value = '';
  document.getElementById('empleado-telefono').value = '';
  document.getElementById('empleado-rol').value = '';
  document.getElementById('empleado-rancho').value = '';
  document.getElementById('empleado-estado').value = 'ACTIVO';
  
  document.getElementById('modal-empleado').classList.remove('hidden');
}

// Abrir modal para editar empleado
async function abrirModalEditar(empleadoId) {
  try {
    const response = await fetch(`${API_BASE}/empleados/${empleadoId}`, {
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      const empleado = await response.json();
      document.getElementById('modal-titulo').textContent = 'Editar Empleado';
      document.getElementById('empleado-id').value = empleado.id;
      document.getElementById('empleado-nombre').value = empleado.nombre;
      document.getElementById('empleado-email').value = empleado.email;
      document.getElementById('empleado-telefono').value = empleado.telefono || '';
      document.getElementById('empleado-rol').value = empleado.rol;
      document.getElementById('empleado-estado').value = empleado.estado;
      document.getElementById('empleado-rancho').value = empleado.ranchoId || '';
      
      document.getElementById('modal-empleado').classList.remove('hidden');
    }
  } catch (error) {
    console.error('Error al cargar empleado:', error);
  }
}

// Cerrar modal
function cerrarModal() {
  document.getElementById('modal-empleado').classList.add('hidden');
  document.getElementById('formulario-empleado').reset();
}

// Guardar empleado (crear o actualizar)
async function guardarEmpleado(event) {
  event.preventDefault();
  
  const empleadoId = document.getElementById('empleado-id').value;
  const empleadoData = {
    nombre: document.getElementById('empleado-nombre').value,
    email: document.getElementById('empleado-email').value,
    telefono: document.getElementById('empleado-telefono').value,
    rol: document.getElementById('empleado-rol').value,
    ranchoId: document.getElementById('empleado-rancho').value,
    estado: document.getElementById('empleado-estado').value
  };
  
  try {
    const url = empleadoId 
      ? `${API_BASE}/empleados/${empleadoId}`
      : `${API_BASE}/empleados`;
    
    const method = empleadoId ? 'PUT' : 'POST';
    
    const response = await fetch(url, {
      method: method,
      headers: getAuthHeaders(),
      body: JSON.stringify(empleadoData)
    });
    
    if (response.ok) {
      cerrarModal();
      cargarDatos();
    } else {
      const error = await response.json();
      alert('Error al guardar empleado: ' + (error.message || 'Error desconocido'));
    }
  } catch (error) {
    console.error('Error al guardar empleado:', error);
    alert('Error de conexión al guardar empleado');
  }
}

// Eliminar empleado
async function eliminarEmpleado(empleadoId) {
  if (!confirm('¿Estás seguro de eliminar este empleado? Esta acción no se puede deshacer.')) {
    return;
  }
  
  try {
    const response = await fetch(`${API_BASE}/empleados/${empleadoId}`, {
      method: 'DELETE',
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      cargarDatos();
    } else {
      const error = await response.json();
      alert('Error al eliminar empleado: ' + (error.message || 'Error desconocido'));
    }
  } catch (error) {
    console.error('Error al eliminar empleado:', error);
    alert('Error de conexión al eliminar empleado');
  }
}

// Cerrar modal con tecla Escape
document.addEventListener('keydown', function(event) {
  if (event.key === 'Escape') {
    cerrarModal();
  }
});

// Cerrar modal al hacer clic fuera del contenido (esperar a que el DOM esté cargado)
document.addEventListener('DOMContentLoaded', function() {
  const modal = document.getElementById('modal-empleado');
  if (modal) {
    modal.addEventListener('click', function(event) {
      if (event.target === this) {
        cerrarModal();
      }
    });
  }
  
  // Cargar datos iniciales
  cargarDatos();
});
