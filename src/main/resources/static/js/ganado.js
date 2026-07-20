// ==========================================
// GANADO.JS - Lógica de negocio para vista de ganado
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

let animalesCache = [];
let ranchosCache = [];

// Cargar datos iniciales
async function cargarDatos() {
  try {
    // Cargar ranchos para el filtro
    const ranchosResponse = await fetch(`${API_BASE}/ranchos`, {
      headers: getAuthHeaders()
    });
    if (ranchosResponse.ok) {
      ranchosCache = await ranchosResponse.json();
      poblarSelectRanchos(ranchosCache);
    }

    // Cargar animales
    const animalesResponse = await fetch(`${API_BASE}/animales`, {
      headers: getAuthHeaders()
    });
    if (animalesResponse.ok) {
      animalesCache = await animalesResponse.json();
      renderizarTablaGanado(animalesCache);
      actualizarEstadisticas(animalesCache);
    }
  } catch (error) {
    console.error('Error al cargar datos:', error);
  }
}

// Poblar select de ranchos
function poblarSelectRanchos(ranchos) {
  const filtroSelect = document.getElementById('filtro-rancho');
  const modalSelect = document.getElementById('animal-rancho');
  
  ranchos.forEach(rancho => {
    const option = document.createElement('option');
    option.value = rancho.id;
    option.textContent = rancho.nombre;
    filtroSelect.appendChild(option);
    modalSelect.appendChild(option.cloneNode(true));
  });
}

// Renderizar tabla de ganado
function renderizarTablaGanado(animales) {
  const tbody = document.getElementById('tabla-ganado');
  tbody.innerHTML = '';
  
  if (animales.length === 0) {
    tbody.innerHTML = '<tr><td colspan="9" class="px-6 py-8 text-center text-gray-500">No hay animales registrados</td></tr>';
    return;
  }
  
  animales.forEach(animal => {
    const tr = document.createElement('tr');
    tr.setAttribute('data-estatus', animal.estatus);
    tr.setAttribute('data-sexo', animal.sexo);
    tr.setAttribute('data-tipo', animal.tipo);
    tr.innerHTML = `
      <td class="px-6 py-4 whitespace-nowrap">
        <span class="font-medium">${animal.numeroIdentificador}</span>
      </td>
      <td class="px-6 py-4 whitespace-nowrap">${animal.raza}</td>
      <td class="px-6 py-4 whitespace-nowrap">
        ${getBadgeSexo(animal.sexo)}
      </td>
      <td class="px-6 py-4 whitespace-nowrap">
        ${getBadgeEstatus(animal.estatus)}
      </td>
      <td class="px-6 py-4 whitespace-nowrap">${animal.tipo}</td>
      <td class="px-6 py-4 whitespace-nowrap">${animal.meses || '-'}</td>
      <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">${formatearFecha(animal.fechaNacimiento)}</td>
      <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-600">${animal.ranchoNombre || 'Sin asignar'}</td>
      <td class="px-6 py-4 whitespace-nowrap text-right">
        <div class="flex justify-end gap-2">
          <button class="px-3 py-1 text-sm bg-gray-200 text-gray-700 rounded hover:bg-gray-300 transition"
                  onclick="abrirModalEditar('${animal.id}')">Editar</button>
          <button class="px-3 py-1 text-sm bg-red-600 text-white rounded hover:bg-red-700 transition"
                  onclick="eliminarAnimal('${animal.id}')">Eliminar</button>
        </div>
      </td>
    `;
    tbody.appendChild(tr);
  });
}

// Obtener badge según sexo
function getBadgeSexo(sexo) {
  const badges = {
    'HEMBRA': '<span class="px-2 py-1 text-xs font-semibold rounded-full bg-indigo-100 text-indigo-800">Hembra</span>',
    'MACHO': '<span class="px-2 py-1 text-xs font-semibold rounded-full bg-yellow-100 text-yellow-800">Macho</span>'
  };
  return badges[sexo] || badges['MACHO'];
}

// Obtener badge según estatus
function getBadgeEstatus(estatus) {
  const badges = {
    'VIGENTE': '<span class="px-2 py-1 text-xs font-semibold rounded-full bg-green-100 text-green-800">Vigente</span>',
    'BAJA': '<span class="px-2 py-1 text-xs font-semibold rounded-full bg-red-100 text-red-800">Baja</span>'
  };
  return badges[estatus] || badges['BAJA'];
}

// Formatear fecha
function formatearFecha(fecha) {
  if (!fecha) return '-';
  const date = new Date(fecha);
  return date.toLocaleDateString('es-ES', { day: '2-digit', month: '2-digit', year: 'numeric' });
}

// Actualizar estadísticas
function actualizarEstadisticas(animales) {
  document.getElementById('stat-total').textContent = animales.length;
  document.getElementById('stat-vigentes').textContent = animales.filter(a => a.estatus === 'VIGENTE').length;
  document.getElementById('stat-baja').textContent = animales.filter(a => a.estatus === 'BAJA').length;
  document.getElementById('stat-hembras').textContent = animales.filter(a => a.sexo === 'HEMBRA').length;
}

// Filtrar por rancho
function filtrarPorRancho() {
  const ranchoId = document.getElementById('filtro-rancho').value;
  cargarAnimalesFiltrados(ranchoId);
}

// Cargar animales filtrados
async function cargarAnimalesFiltrados(ranchoId) {
  try {
    const url = ranchoId 
      ? `${API_BASE}/animales?ranchoId=${ranchoId}`
      : `${API_BASE}/animales`;
    
    const response = await fetch(url, {
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      const animales = await response.json();
      animalesCache = animales;
      renderizarTablaGanado(animales);
      actualizarEstadisticas(animales);
    }
  } catch (error) {
    console.error('Error al cargar animales filtrados:', error);
  }
}

// Limpiar filtros
function limpiarFiltros() {
  document.getElementById('filtro-rancho').value = '';
  document.getElementById('filtro-estatus').value = '';
  document.getElementById('filtro-sexo').value = '';
  document.getElementById('filtro-tipo').value = '';
  cargarDatos();
}

// Filtrar tabla por estatus, sexo, tipo
function filtrarTabla() {
  const estatus = document.getElementById('filtro-estatus').value;
  const sexo = document.getElementById('filtro-sexo').value;
  const tipo = document.getElementById('filtro-tipo').value;
  
  const filas = document.querySelectorAll('#tabla-ganado tr');
  filas.forEach(fila => {
    const filaEstatus = fila.getAttribute('data-estatus');
    const filaSexo = fila.getAttribute('data-sexo');
    const filaTipo = fila.getAttribute('data-tipo');
    
    const mostrar = (!estatus || filaEstatus === estatus) &&
                   (!sexo || filaSexo === sexo) &&
                   (!tipo || filaTipo === tipo);
    
    fila.style.display = mostrar ? '' : 'none';
  });
}

// Abrir modal para crear animal
function abrirModalCrear() {
  document.getElementById('modal-titulo').textContent = 'Nuevo Animal';
  document.getElementById('animal-id').value = '';
  document.getElementById('animal-numeroIdentificador').value = '';
  document.getElementById('animal-raza').value = '';
  document.getElementById('animal-sexo').value = '';
  document.getElementById('animal-estatus').value = 'VIGENTE';
  document.getElementById('animal-tipo').value = '';
  document.getElementById('animal-meses').value = '';
  document.getElementById('animal-fechaNacimiento').value = '';
  document.getElementById('animal-fechaAretado').value = '';
  document.getElementById('animal-rancho').value = '';
  document.getElementById('animal-areteAnterior').value = '';
  document.getElementById('animal-folioReemo').value = '';
  document.getElementById('animal-nota').value = '';
  
  document.getElementById('modal-animal').classList.remove('hidden');
}

// Abrir modal para editar animal
async function abrirModalEditar(animalId) {
  try {
    const response = await fetch(`${API_BASE}/animales/${animalId}`, {
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      const animal = await response.json();
      document.getElementById('modal-titulo').textContent = 'Editar Animal';
      document.getElementById('animal-id').value = animal.id;
      document.getElementById('animal-numeroIdentificador').value = animal.numeroIdentificador;
      document.getElementById('animal-raza').value = animal.raza;
      document.getElementById('animal-sexo').value = animal.sexo;
      document.getElementById('animal-estatus').value = animal.estatus;
      document.getElementById('animal-tipo').value = animal.tipo;
      document.getElementById('animal-meses').value = animal.meses || '';
      document.getElementById('animal-fechaNacimiento').value = animal.fechaNacimiento || '';
      document.getElementById('animal-fechaAretado').value = animal.fechaAretado || '';
      document.getElementById('animal-rancho').value = animal.ranchoId || '';
      document.getElementById('animal-areteAnterior').value = animal.areteAnterior || '';
      document.getElementById('animal-folioReemo').value = animal.folioReemo || '';
      document.getElementById('animal-nota').value = animal.nota || '';
      
      document.getElementById('modal-animal').classList.remove('hidden');
    }
  } catch (error) {
    console.error('Error al cargar animal:', error);
  }
}

// Cerrar modal
function cerrarModal() {
  document.getElementById('modal-animal').classList.add('hidden');
  document.getElementById('formulario-animal').reset();
}

// Guardar animal (crear o actualizar)
async function guardarAnimal(event) {
  event.preventDefault();
  
  const animalId = document.getElementById('animal-id').value;
  const animalData = {
    numeroIdentificador: document.getElementById('animal-numeroIdentificador').value,
    raza: document.getElementById('animal-raza').value,
    sexo: document.getElementById('animal-sexo').value,
    estatus: document.getElementById('animal-estatus').value,
    tipo: document.getElementById('animal-tipo').value,
    meses: parseInt(document.getElementById('animal-meses').value) || null,
    fechaNacimiento: document.getElementById('animal-fechaNacimiento').value || null,
    fechaAretado: document.getElementById('animal-fechaAretado').value || null,
    ranchoId: document.getElementById('animal-rancho').value,
    areteAnterior: document.getElementById('animal-areteAnterior').value || null,
    folioReemo: document.getElementById('animal-folioReemo').value || null,
    nota: document.getElementById('animal-nota').value || null
  };
  
  try {
    const url = animalId 
      ? `${API_BASE}/animales/${animalId}`
      : `${API_BASE}/animales`;
    
    const method = animalId ? 'PUT' : 'POST';
    
    const response = await fetch(url, {
      method: method,
      headers: getAuthHeaders(),
      body: JSON.stringify(animalData)
    });
    
    if (response.ok) {
      cerrarModal();
      cargarDatos();
    } else {
      const error = await response.json();
      alert('Error al guardar animal: ' + (error.message || 'Error desconocido'));
    }
  } catch (error) {
    console.error('Error al guardar animal:', error);
    alert('Error de conexión al guardar animal');
  }
}

// Eliminar animal
async function eliminarAnimal(animalId) {
  if (!confirm('¿Estás seguro de eliminar este animal? Esta acción no se puede deshacer.')) {
    return;
  }
  
  try {
    const response = await fetch(`${API_BASE}/animales/${animalId}`, {
      method: 'DELETE',
      headers: getAuthHeaders()
    });
    
    if (response.ok) {
      cargarDatos();
    } else {
      const error = await response.json();
      alert('Error al eliminar animal: ' + (error.message || 'Error desconocido'));
    }
  } catch (error) {
    console.error('Error al eliminar animal:', error);
    alert('Error de conexión al eliminar animal');
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
  const modal = document.getElementById('modal-animal');
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
