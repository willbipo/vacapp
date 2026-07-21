-- ============================================================
--  Vacapp — Datos de Inicialización (data.sql)
--  Spring Boot ejecuta este archivo después de schema.sql
--  (spring.sql.init.mode=always en application.properties)
--
--  IMPORTANTE: Estos usuarios son para DESARROLLO/PRUEBA.
--  En PRODUCCIÓN, hashear todas las contraseñas con BCrypt
--  y usar valores seguros (UUIDs, claves fuertes, etc).
-- ============================================================

-- ============================================================
--  USUARIOS (4 por rol + 16 adicionales)
-- ============================================================

-- Insertar usuario ADMIN
INSERT IGNORE INTO usuarios (id, username, email, password, role, tenant_id) VALUES (
    '550e8400-e29b-41d4-a716-446655440001',
    'admin',
    'admin@vacapp.test',
    'admin123',
    'ADMIN',
    'default'
);

-- Insertar usuario FARMER (Ganadero) - Propietario Rancho 1
INSERT IGNORE INTO usuarios (id, username, email, password, role, tenant_id) VALUES (
    '550e8400-e29b-41d4-a716-446655440002',
    'farmer',
    'farmer@vacapp.test',
    'farmer123',
    'FARMER',
    'default'
);

-- Insertar usuario FARMER (Ganadero) - Propietario Rancho 2
INSERT IGNORE INTO usuarios (id, username, email, password, role, tenant_id) VALUES (
    '550e8400-e29b-41d4-a716-446655440009',
    'ana_farmer',
    'ana@vacapp.test',
    'pass123',
    'FARMER',
    'default'
);

-- Insertar usuario DOCTOR (Médico Veterinario)
INSERT IGNORE INTO usuarios (id, username, email, password, role, tenant_id) VALUES (
    '550e8400-e29b-41d4-a716-446655440003',
    'doctor',
    'doctor@vacapp.test',
    'doctor123',
    'DOCTOR',
    'default'
);

-- Insertar usuario WORKER (Trabajador)
INSERT IGNORE INTO usuarios (id, username, email, password, role, tenant_id) VALUES (
    '550e8400-e29b-41d4-a716-446655440004',
    'worker',
    'worker@vacapp.test',
    'worker123',
    'WORKER',
    'default'
);

-- Usuarios adicionales (16 más)
INSERT IGNORE INTO usuarios (id, username, email, password, role, tenant_id) VALUES 
    ('550e8400-e29b-41d4-a716-446655440005', 'carlos_farmer', 'carlos@vacapp.test', 'pass123', 'FARMER', 'default'),
    ('550e8400-e29b-41d4-a716-446655440006', 'maria_doctor', 'maria@vacapp.test', 'pass123', 'DOCTOR', 'default'),
    ('550e8400-e29b-41d4-a716-446655440007', 'juan_worker', 'juan@vacapp.test', 'pass123', 'WORKER', 'default'),
    ('550e8400-e29b-41d4-a716-446655440008', 'pedro_admin', 'pedro@vacapp.test', 'pass123', 'ADMIN', 'default'),
    ('550e8400-e29b-41d4-a716-446655440010', 'luis_doctor', 'luis@vacapp.test', 'pass123', 'DOCTOR', 'default'),
    ('550e8400-e29b-41d4-a716-446655440011', 'sofia_worker', 'sofia@vacapp.test', 'pass123', 'WORKER', 'default'),
    ('550e8400-e29b-41d4-a716-446655440012', 'diego_farmer', 'diego@vacapp.test', 'pass123', 'FARMER', 'default'),
    ('550e8400-e29b-41d4-a716-446655440013', 'laura_doctor', 'laura@vacapp.test', 'pass123', 'DOCTOR', 'default'),
    ('550e8400-e29b-41d4-a716-446655440014', 'miguel_worker', 'miguel@vacapp.test', 'pass123', 'WORKER', 'default'),
    ('550e8400-e29b-41d4-a716-446655440015', 'rosa_farmer', 'rosa@vacapp.test', 'pass123', 'FARMER', 'default'),
    ('550e8400-e29b-41d4-a716-446655440016', 'francisco_doctor', 'francisco@vacapp.test', 'pass123', 'DOCTOR', 'default'),
    ('550e8400-e29b-41d4-a716-446655440017', 'gloria_worker', 'gloria@vacapp.test', 'pass123', 'WORKER', 'default'),
    ('550e8400-e29b-41d4-a716-446655440018', 'javier_farmer', 'javier@vacapp.test', 'pass123', 'FARMER', 'default'),
    ('550e8400-e29b-41d4-a716-446655440019', 'elena_doctor', 'elena@vacapp.test', 'pass123', 'DOCTOR', 'default'),
    ('550e8400-e29b-41d4-a716-446655440020', 'ramon_worker', 'ramon@vacapp.test', 'pass123', 'WORKER', 'default');

-- ============================================================
--  RANCHOS (2 ranchos de ejemplo)
-- ============================================================

-- Rancho 1: 10 hectáreas - Propiedad de farmer (550e8400-e29b-41d4-a716-446655440002)
INSERT IGNORE INTO ranchos (id, tenant_id, user_id, nombre, descripcion, hectareas, ubicacion, fecha_registro, fecha_actualizacion) VALUES (
    'r60e8400-e29b-41d4-a716-446655440001',
    'default',
    '550e8400-e29b-41d4-a716-446655440002',
    'Rancho Santa María',
    'Rancho de 10 hectáreas dedicado a lechería y reproducción',
    10.5,
    'Montaña, Jalisco',
    NOW(),
    NOW()
);

-- Rancho 2: 5 hectáreas - Propiedad de ana_farmer (550e8400-e29b-41d4-a716-446655440009)
INSERT IGNORE INTO ranchos (id, tenant_id, user_id, nombre, descripcion, hectareas, ubicacion, fecha_registro, fecha_actualizacion) VALUES (
    'r60e8400-e29b-41d4-a716-446655440002',
    'default',
    '550e8400-e29b-41d4-a716-446655440009',
    'Rancho Los Altos',
    'Rancho de 5 hectáreas con ganado de doble propósito',
    5.0,
    'Altos de Jalisco',
    NOW(),
    NOW()
);

-- ============================================================
--  CATEGORÍAS DE INSUMOS
-- ============================================================

INSERT IGNORE INTO categorias_insumos (id, nombre, tenant_id) VALUES 
    ('650e8400-e29b-41d4-a716-446655440001', 'Herramientas', 'default'),
    ('650e8400-e29b-41d4-a716-446655440002', 'Alimentos', 'default'),
    ('650e8400-e29b-41d4-a716-446655440003', 'Medicamentos', 'default'),
    ('650e8400-e29b-41d4-a716-446655440004', 'Equipos', 'default'),
    ('650e8400-e29b-41d4-a716-446655440005', 'Semillas', 'default'),
    ('650e8400-e29b-41d4-a716-446655440006', 'Fertilizantes', 'default');

-- ============================================================
--  CATEGORÍAS DE VACUNAS
-- ============================================================

INSERT IGNORE INTO categorias_vacunas (id, nombre, tenant_id) VALUES 
    ('750e8400-e29b-41d4-a716-446655440001', 'Virales', 'default'),
    ('750e8400-e29b-41d4-a716-446655440002', 'Bacterianas', 'default'),
    ('750e8400-e29b-41d4-a716-446655440003', 'Parasitarias', 'default'),
    ('750e8400-e29b-41d4-a716-446655440004', 'Combinadas', 'default');

-- ============================================================
--  CATEGORÍAS DE GANADO
-- ============================================================

INSERT IGNORE INTO categorias_ganado (id, nombre, tenant_id) VALUES 
    ('850e8400-e29b-41d4-a716-446655440001', 'Lechero', 'default'),
    ('850e8400-e29b-41d4-a716-446655440002', 'Carne', 'default'),
    ('850e8400-e29b-41d4-a716-446655440003', 'Doble propósito', 'default');

-- ============================================================
--  SECCIONES Y POTREROS PARA RANCHO 1 (Santa María)
-- ============================================================

-- Secciones del Rancho Santa María
INSERT IGNORE INTO secciones (id, rancho_id, nombre, descripcion, fecha_registro, fecha_actualizacion, tenant_id) VALUES 
    ('s60e8400-e29b-41d4-a716-446655440001', 'r60e8400-e29b-41d4-a716-446655440001', 'Zona Norte', 'Área de pastoreo y reproducción', NOW(), NOW(), 'default'),
    ('s60e8400-e29b-41d4-a716-446655440002', 'r60e8400-e29b-41d4-a716-446655440001', 'Zona Sur', 'Área de ordeño y alimentación', NOW(), NOW(), 'default');

-- Potreros directos del Rancho Santa María (sin sección)
INSERT IGNORE INTO potreros (id, rancho_id, seccion_id, nombre, hectareas, tipo_pasto, fecha_registro, fecha_actualizacion, tenant_id) VALUES 
    ('p60e8400-e29b-41d4-a716-446655440001', 'r60e8400-e29b-41d4-a716-446655440001', NULL, 'Potrero Principal', 3.5, 'Grama Bermuda', NOW(), NOW(), 'default'),
    ('p60e8400-e29b-41d4-a716-446655440002', 'r60e8400-e29b-41d4-a716-446655440001', NULL, 'Potrero de Reposo', 1.5, 'Pasto Estrella', NOW(), NOW(), 'default');

-- Potreros de la Sección Norte (Rancho Santa María)
INSERT IGNORE INTO potreros (id, rancho_id, seccion_id, nombre, hectareas, tipo_pasto, fecha_registro, fecha_actualizacion, tenant_id) VALUES 
    ('p60e8400-e29b-41d4-a716-446655440003', 'r60e8400-e29b-41d4-a716-446655440001', 's60e8400-e29b-41d4-a716-446655440001', 'Potrero Norte 1', 2.0, 'Grama Rhodes', NOW(), NOW(), 'default'),
    ('p60e8400-e29b-41d4-a716-446655440004', 'r60e8400-e29b-41d4-a716-446655440001', 's60e8400-e29b-41d4-a716-446655440001', 'Potrero Norte 2', 1.8, 'Pasto Brachiaria', NOW(), NOW(), 'default');

-- Potreros de la Sección Sur (Rancho Santa María)
INSERT IGNORE INTO potreros (id, rancho_id, seccion_id, nombre, hectareas, tipo_pasto, fecha_registro, fecha_actualizacion, tenant_id) VALUES 
    ('p60e8400-e29b-41d4-a716-446655440005', 'r60e8400-e29b-41d4-a716-446655440001', 's60e8400-e29b-41d4-a716-446655440002', 'Potrero Sur 1', 1.2, 'Grama Bermuda', NOW(), NOW(), 'default');

-- ============================================================
--  SECCIONES Y POTREROS PARA RANCHO 2 (Los Altos)
-- ============================================================

-- Secciones del Rancho Los Altos
INSERT IGNORE INTO secciones (id, rancho_id, nombre, descripcion, fecha_registro, fecha_actualizacion, tenant_id) VALUES 
    ('s60e8400-e29b-41d4-a716-446655440003', 'r60e8400-e29b-41d4-a716-446655440002', 'Zona Este', 'Área de engorde', NOW(), NOW(), 'default'),
    ('s60e8400-e29b-41d4-a716-446655440004', 'r60e8400-e29b-41d4-a716-446655440002', 'Zona Oeste', 'Área de cría', NOW(), NOW(), 'default');

-- Potreros directos del Rancho Los Altos (sin sección)
INSERT IGNORE INTO potreros (id, rancho_id, seccion_id, nombre, hectareas, tipo_pasto, fecha_registro, fecha_actualizacion, tenant_id) VALUES 
    ('p60e8400-e29b-41d4-a716-446655440006', 'r60e8400-e29b-41d4-a716-446655440002', NULL, 'Potrero Central', 2.0, 'Pasto Buffel', NOW(), NOW(), 'default');

-- Potreros de la Sección Este (Rancho Los Altos)
INSERT IGNORE INTO potreros (id, rancho_id, seccion_id, nombre, hectareas, tipo_pasto, fecha_registro, fecha_actualizacion, tenant_id) VALUES 
    ('p60e8400-e29b-41d4-a716-446655440007', 'r60e8400-e29b-41d4-a716-446655440002', 's60e8400-e29b-41d4-a716-446655440003', 'Potrero Este 1', 1.5, 'Pasto Pangola', NOW(), NOW(), 'default'),
    ('p60e8400-e29b-41d4-a716-446655440008', 'r60e8400-e29b-41d4-a716-446655440002', 's60e8400-e29b-41d4-a716-446655440003', 'Potrero Este 2', 0.8, 'Grama Bermuda', NOW(), NOW(), 'default');

-- Potreros de la Sección Oeste (Rancho Los Altos)
INSERT IGNORE INTO potreros (id, rancho_id, seccion_id, nombre, hectareas, tipo_pasto, fecha_registro, fecha_actualizacion, tenant_id) VALUES 
    ('p60e8400-e29b-41d4-a716-446655440009', 'r60e8400-e29b-41d4-a716-446655440002', 's60e8400-e29b-41d4-a716-446655440004', 'Potrero Oeste 1', 0.7, 'Pasto Estrella', NOW(), NOW(), 'default');

-- ============================================================
--  EMPLEADOS PARA RANCHO 1 (Santa María)
-- ============================================================

INSERT IGNORE INTO empleados (id, nombre, email, telefono, rol, estado, fecha_registro, fecha_actualizacion, rancho_id, tenant_id) VALUES 
    ('e60e8400-e29b-41d4-a716-446655440001', 'María García López', 'maria.garcia@vacapp.test', '3001234567', 'DOCTOR', 'ACTIVO', NOW(), NOW(), 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440002', 'Juan Pérez Rodríguez', 'juan.perez@vacapp.test', '3011234567', 'WORKER', 'ACTIVO', NOW(), NOW(), 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440003', 'Carlos López Martínez', 'carlos.lopez@vacapp.test', '3012345678', 'WORKER', 'PENDIENTE', NOW(), NOW(), 'r60e8400-e29b-41d4-a716-446655440001', 'default');

-- Migración: actualizar rancho_id para empleados existentes
UPDATE empleados SET rancho_id = 'r60e8400-e29b-41d4-a716-446655440001' 
WHERE id IN ('e60e8400-e29b-41d4-a716-446655440001', 'e60e8400-e29b-41d4-a716-446655440002', 'e60e8400-e29b-41d4-a716-446655440003') 
AND rancho_id IS NULL;

-- Asignar empleados al Rancho Santa María
INSERT IGNORE INTO empleados_ranchos (id, empleado_id, rancho_id, fecha_asignacion, fecha_fin_asignacion, activo, tenant_id) VALUES 
    ('er60e8400-e29b-41d4-a716-446655440001', 'e60e8400-e29b-41d4-a716-446655440001', 'r60e8400-e29b-41d4-a716-446655440001', NOW(), NULL, TRUE, 'default'),
    ('er60e8400-e29b-41d4-a716-446655440002', 'e60e8400-e29b-41d4-a716-446655440002', 'r60e8400-e29b-41d4-a716-446655440001', NOW(), NULL, TRUE, 'default'),
    ('er60e8400-e29b-41d4-a716-446655440003', 'e60e8400-e29b-41d4-a716-446655440003', 'r60e8400-e29b-41d4-a716-446655440001', NOW(), NULL, TRUE, 'default');

-- ============================================================
--  EMPLEADOS PARA RANCHO 2 (Los Altos)
-- ============================================================

INSERT IGNORE INTO empleados (id, nombre, email, telefono, rol, estado, fecha_registro, fecha_actualizacion, rancho_id, tenant_id) VALUES 
    ('e60e8400-e29b-41d4-a716-446655440004', 'Dr. Luis Fernando Sánchez', 'luis.sanchez@vacapp.test', '3023456789', 'DOCTOR', 'ACTIVO', NOW(), NOW(), 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440005', 'Jorge Hernández García', 'jorge.hernandez@vacapp.test', '3034567890', 'WORKER', 'ACTIVO', NOW(), NOW(), 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440006', 'Patricia Estrada Morales', 'patricia.estrada@vacapp.test', '3045678901', 'WORKER', 'ACTIVO', NOW(), NOW(), 'r60e8400-e29b-41d4-a716-446655440002', 'default');

-- Migración: actualizar rancho_id para empleados existentes
UPDATE empleados SET rancho_id = 'r60e8400-e29b-41d4-a716-446655440002' 
WHERE id IN ('e60e8400-e29b-41d4-a716-446655440004', 'e60e8400-e29b-41d4-a716-446655440005', 'e60e8400-e29b-41d4-a716-446655440006') 
AND rancho_id IS NULL;

-- Asignar empleados al Rancho Los Altos
INSERT IGNORE INTO empleados_ranchos (id, empleado_id, rancho_id, fecha_asignacion, fecha_fin_asignacion, activo, tenant_id) VALUES 
    ('er60e8400-e29b-41d4-a716-446655440004', 'e60e8400-e29b-41d4-a716-446655440004', 'r60e8400-e29b-41d4-a716-446655440002', NOW(), NULL, TRUE, 'default'),
    ('er60e8400-e29b-41d4-a716-446655440005', 'e60e8400-e29b-41d4-a716-446655440005', 'r60e8400-e29b-41d4-a716-446655440002', NOW(), NULL, TRUE, 'default'),
    ('er60e8400-e29b-41d4-a716-446655440006', 'e60e8400-e29b-41d4-a716-446655440006', 'r60e8400-e29b-41d4-a716-446655440002', NOW(), NULL, TRUE, 'default');

-- ============================================================
--  INSUMOS PARA RANCHO 1 (Santa María)
-- ============================================================

INSERT IGNORE INTO insumos (id, nombre, categoria, unidad_medida, cantidad, cantidad_minima, descripcion, proveedor, precio_unitario, ubicacion, rancho_id, tenant_id) VALUES 
    ('i60e8400-e29b-41d4-a716-446655440001', 'Pienso concentrado 18%', 'ALIMENTO', 'SACO', 50, 10, 'Alimento balanceado para ganado lechero', 'Proveeduría García', 25.50, 'Bodega A - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440002', 'Sal mineralizada', 'ALIMENTO', 'KILOGRAMO', 100, 20, 'Sales minerales para suplementación', 'Química Rural', 5.99, 'Bodega B - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440003', 'Forraje seco', 'ALIMENTO', 'TONELADA', 15, 3, 'Heno de calidad premium', 'Forrajes del Sur', 150.00, 'Silos - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440004', 'Jeringa 10cc', 'MEDICAMENTO', 'UNIDAD', 200, 50, 'Jeringas desechables estériles', 'LabMédi', 0.85, 'Botiquín - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440005', 'Amoxicilina inyectable', 'MEDICAMENTO', 'UNIDAD', 30, 10, 'Antibiótico para ganado', 'LabMédi', 8.50, 'Botiquín - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440006', 'Marcador para ganado rojo', 'HERRAMIENTA', 'UNIDAD', 15, 3, 'Marcador de pintura para identificación', 'Herramientas Pro', 12.00, 'Taller - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440007', 'Tenazas de ordeño', 'EQUIPO', 'UNIDAD', 8, 2, 'Tenazas de alta resistencia', 'Equipos Agro', 45.00, 'Taller - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440008', 'Cuerda de nylon 50m', 'HERRAMIENTA', 'ROLLO', 20, 5, 'Cuerda resistente para amarres', 'Herramientas Pro', 35.00, 'Bodega C - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440009', 'Desinfectante de ubre', 'MEDICAMENTO', 'LITRO', 25, 5, 'Desinfectante para limpieza de ubres', 'HigienePro', 18.50, 'Botiquín - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440010', 'Vitaminas AD3E', 'MEDICAMENTO', 'LITRO', 12, 3, 'Complejo vitamínico para ganado', 'NutriBest', 42.00, 'Botiquín - Santa María', 'r60e8400-e29b-41d4-a716-446655440001', 'default');

-- ============================================================
--  INSUMOS PARA RANCHO 2 (Los Altos)
-- ============================================================

INSERT IGNORE INTO insumos (id, nombre, categoria, unidad_medida, cantidad, cantidad_minima, descripcion, proveedor, precio_unitario, ubicacion, rancho_id, tenant_id) VALUES 
    ('i60e8400-e29b-41d4-a716-446655440011', 'Pienso para carne 16%', 'ALIMENTO', 'SACO', 30, 8, 'Alimento balanceado para ganado de carne', 'Proveeduría García', 22.50, 'Bodega A - Los Altos', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440012', 'Pastilla sal-mineral', 'ALIMENTO', 'KILOGRAMO', 80, 15, 'Sales para ganado rústico', 'Química Rural', 4.99, 'Bodega B - Los Altos', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440013', 'Forraje mejorado', 'ALIMENTO', 'TONELADA', 8, 2, 'Forraje de alta calidad', 'Forrajes del Sur', 135.00, 'Silos - Los Altos', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440014', 'Antiparasitario pour-on', 'MEDICAMENTO', 'LITRO', 5, 2, 'Tratamiento antiparasitario', 'LabMédi', 65.00, 'Botiquín - Los Altos', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440015', 'Arete plástico azul', 'HERRAMIENTA', 'UNIDAD', 300, 50, 'Aretes para identificación', 'Identificadores Rural', 0.50, 'Almacén - Los Altos', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440016', 'Guantes de trabajo', 'HERRAMIENTA', 'UNIDAD', 50, 10, 'Guantes resistentes para trabajo', 'Herramientas Pro', 3.50, 'Taller - Los Altos', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440017', 'Agua oxigenada 1L', 'MEDICAMENTO', 'LITRO', 20, 5, 'Desinfectante multiuso', 'Química Rural', 4.50, 'Botiquín - Los Altos', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('i60e8400-e29b-41d4-a716-446655440018', 'Alcohol 70%', 'MEDICAMENTO', 'LITRO', 15, 4, 'Alcohol para limpieza', 'Química Rural', 6.00, 'Botiquín - Los Altos', 'r60e8400-e29b-41d4-a716-446655440002', 'default');

-- ============================================================
--  VACUNAS COMPARTIDAS (para ambos ranchos)
-- ============================================================

INSERT IGNORE INTO vacunas (id, nombre, tipo, laboratorio, descripcion, dosis, via_administracion, lote, fecha_caducidad, stock, unidad_medida, temperatura_almacenamiento, intervalo_dias, rancho_id, tenant_id) VALUES 
    ('v60e8400-e29b-41d4-a716-446655440001', 'Triple Viral (IBR, DVB, BVD)', 'VIRAL', 'LabVet', 'Previene virosis respiratoria', '5ml', 'INTRAMUSCULAR', 'LV-2026-001', '2027-03-15', 200, 'UNIDAD', '2-8°C', 30, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440002', 'Brucela (Cepa B19)', 'BACTERIANA', 'LabVet', 'Previene brucelosis', '2ml', 'SUBCUTANEA', 'LB-2026-001', '2027-06-30', 150, 'UNIDAD', '2-8°C', 365, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440003', 'Clostridium Perfringens C+D', 'BACTERIANA', 'LabVet', 'Previene clostridiosis', '5ml', 'INTRAMUSCULAR', 'LC-2026-001', '2027-05-10', 180, 'UNIDAD', '2-8°C', 180, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440004', 'Leptospirosis Hardjo/Pomona', 'BACTERIANA', 'LabVet', 'Previene leptospirosis', '5ml', 'INTRAMUSCULAR', 'LL-2026-001', '2027-04-20', 160, 'UNIDAD', '2-8°C', 90, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440005', 'Fiebre Aftosa Polyvalente', 'VIRAL', 'LabVet', 'Previene fiebre aftosa (O,A,C)', '5ml', 'INTRAMUSCULAR', 'LF-2026-001', '2027-12-31', 250, 'UNIDAD', '2-8°C', 180, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440006', 'Rabia Inactivada', 'VIRAL', 'LabVet', 'Previene rabia bovina', '2ml', 'INTRAMUSCULAR', 'LRB-2026-001', '2028-02-28', 90, 'UNIDAD', '2-8°C', 365, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440007', 'Tuberculina', 'BACTERIANA', 'DiagnostiVet', 'Diagnóstico de tuberculosis', '0.1ml', 'SUBCUTANEA', 'LT-2026-001', '2027-02-28', 100, 'UNIDAD', '2-8°C', 14, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440008', 'Anaplasmosis Bovina', 'BACTERIANA', 'LabVet', 'Previene anaplasmosis', '5ml', 'INTRAMUSCULAR', 'LA-2026-001', '2027-06-15', 135, 'UNIDAD', '2-8°C', 180, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440009', 'Babesiosis', 'PARASITARIA', 'MedicVet', 'Previene babesiosis', '5ml', 'INTRAMUSCULAR', 'LBB-2026-001', '2027-08-25', 95, 'UNIDAD', '2-8°C', 180, NULL, 'default'),
    ('v60e8400-e29b-41d4-a716-446655440010', 'Complejo Respiratorio Bovino', 'COMBINADA', 'LabVet', 'Previene múltiples patógenos respiratorios', '5ml', 'INTRAMUSCULAR', 'LCR-2026-001', '2027-11-30', 210, 'UNIDAD', '2-8°C', 30, NULL, 'default');

-- ============================================================
--  ANIMALES PARA RANCHO 1 (Santa María - Lechería)
-- ============================================================

INSERT IGNORE INTO animales (id, numero_identificador, estatus, sexo, raza, fecha_nacimiento, meses, fecha_aretado, tipo, arete_anterior, folio_reemo, nota, categoria, fecha_inicio_reposo, fecha_fin_reposo, rancho_id, tenant_id) VALUES 
    ('a60e8400-e29b-41d4-a716-446655440001', 'SM-2024-001', 'VIGENTE', 'HEMBRA', 'Holsteín', '2022-01-15', 30, '2022-02-10', 'TRASLADO', NULL, 'RF-2024-001', 'Animal de alta producción', 'Lechero', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440002', 'SM-2024-002', 'VIGENTE', 'HEMBRA', 'Jersey', '2021-03-10', 39, '2021-04-05', 'OTRO', 'AR-2020-456', 'RJ-2024-003', 'Vaca primípara', 'Lechero', '2025-08-01', '2025-10-15', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440003', 'SM-2024-003', 'VIGENTE', 'HEMBRA', 'Guernsey', '2023-01-08', 19, '2023-02-05', 'VENTA', NULL, NULL, 'Vaca segunda lactancia', 'Lechero', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440004', 'SM-2024-004', 'VIGENTE', 'HEMBRA', 'Pardo Suizo', '2021-07-14', 31, '2021-08-10', 'OTRO', NULL, 'RPS-2024-009', 'Vaca multípara', 'Doble propósito', '2025-06-15', '2025-09-30', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440005', 'SM-2024-005', 'VIGENTE', 'HEMBRA', 'Holstein Negro', '2020-11-20', 43, '2020-12-15', 'VENTA', 'AR-2020-789', 'RHN-2024-013', 'Vaca de excelente producción', 'Lechero', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440006', 'SM-2024-006', 'VIGENTE', 'HEMBRA', 'Ayrshire', '2021-12-10', 37, '2022-01-05', 'OTRO', NULL, 'RA-2024-015', 'Vaca adaptable', 'Lechero', '2025-10-01', '2025-12-20', 'r60e8400-e29b-41d4-a716-446655440001', 'default');

-- ============================================================
--  ANIMALES PARA RANCHO 2 (Los Altos - Carne/Doble propósito)
-- ============================================================

INSERT IGNORE INTO animales (id, numero_identificador, estatus, sexo, raza, fecha_nacimiento, meses, fecha_aretado, tipo, arete_anterior, folio_reemo, nota, categoria, fecha_inicio_reposo, fecha_fin_reposo, rancho_id, tenant_id) VALUES 
    ('a60e8400-e29b-41d4-a716-446655440007', 'LA-2024-001', 'VIGENTE', 'MACHO', 'Angus', '2023-06-20', 13, '2023-07-15', 'VENTA', NULL, NULL, 'Reproductor joven', 'Carne', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440008', 'LA-2024-002', 'VIGENTE', 'MACHO', 'Charolés', '2023-11-05', 8, '2023-12-01', 'VENTA', NULL, NULL, 'Toro joven prometedor', 'Doble propósito', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440009', 'LA-2024-003', 'VIGENTE', 'HEMBRA', 'Brahman', '2022-05-30', 25, '2022-06-20', 'TRASLADO', NULL, 'RB-2024-005', 'Vaca resistente a clima', 'Doble propósito', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440010', 'LA-2024-004', 'VIGENTE', 'MACHO', 'Limousin', '2023-08-22', 11, '2023-09-15', 'TRASLADO', NULL, 'RL-2024-008', 'Toro reproductor en desarrollo', 'Carne', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440011', 'LA-2024-005', 'VIGENTE', 'MACHO', 'Hereford', '2024-02-03', 5, '2024-03-01', 'VENTA', NULL, NULL, 'Becerro recién identificado', 'Carne', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('a60e8400-e29b-41d4-a716-446655440012', 'LA-2024-006', 'VIGENTE', 'HEMBRA', 'Normanda', '2022-10-28', 27, '2022-11-20', 'TRASLADO', NULL, 'RN-2024-017', 'Vaca lechera-cárnica', 'Doble propósito', NULL, NULL, 'r60e8400-e29b-41d4-a716-446655440002', 'default');

-- ============================================================
--  CICLOS REPRODUCTIVOS RANCHO 1 (Santa María)
-- ============================================================

INSERT IGNORE INTO ciclos_reproductivos (id, vaca_id, fecha_inicio, fecha_estimada_parto, fecha_parto_real, dias_reposo, fecha_fin_reposo, estatus, notas, rancho_id, tenant_id) VALUES 
    ('c60e8400-e29b-41d4-a716-446655440001', 'a60e8400-e29b-41d4-a716-446655440001', '2024-09-15', '2025-06-15', NULL, 60, NULL, 'EN_CURSO', 'Vaca primípara en buen estado', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440002', 'a60e8400-e29b-41d4-a716-446655440002', '2024-08-01', '2025-05-01', '2025-05-03', 60, '2025-07-02', 'FINALIZADO', 'Parto sin complicaciones', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440003', 'a60e8400-e29b-41d4-a716-446655440004', '2024-05-15', '2025-02-15', '2025-02-16', 60, '2025-04-16', 'FINALIZADO', 'Becerro macho viable', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440004', 'a60e8400-e29b-41d4-a716-446655440005', '2024-04-01', '2024-12-31', '2025-01-02', 60, '2025-03-02', 'FINALIZADO', 'Parto sin anomalías', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440005', 'a60e8400-e29b-41d4-a716-446655440006', '2024-09-01', '2025-06-01', NULL, 60, NULL, 'EN_CURSO', 'Seguimiento regular', 'r60e8400-e29b-41d4-a716-446655440001', 'default');

-- ============================================================
--  CICLOS REPRODUCTIVOS RANCHO 2 (Los Altos)
-- ============================================================

INSERT IGNORE INTO ciclos_reproductivos (id, vaca_id, fecha_inicio, fecha_estimada_parto, fecha_parto_real, dias_reposo, fecha_fin_reposo, estatus, notas, rancho_id, tenant_id) VALUES 
    ('c60e8400-e29b-41d4-a716-446655440006', 'a60e8400-e29b-41d4-a716-446655440009', '2024-07-10', '2025-04-10', NULL, 60, NULL, 'EN_CURSO', 'Animal gestante', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440007', 'a60e8400-e29b-41d4-a716-446655440012', '2024-08-10', '2025-05-10', NULL, 60, NULL, 'EN_CURSO', 'Vaca robusta', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440008', 'a60e8400-e29b-41d4-a716-446655440012', '2023-10-15', '2024-07-15', '2024-07-17', 60, '2024-09-15', 'FINALIZADO', 'Animal descansado', 'r60e8400-e29b-41d4-a716-446655440002', 'default');

-- ============================================================
--  HISTORIAL CLÍNICO RANCHO 1 (Santa María)
-- ============================================================

INSERT IGNORE INTO historial_clinico (id, animal_id, vacuna_id, nombre_vacuna, dosis, via_administracion, lote, fecha_aplicacion, proxima_dosis, notas, aplicado_por, rancho_id, tenant_id) VALUES 
    ('h60e8400-e29b-41d4-a716-446655440001', 'a60e8400-e29b-41d4-a716-446655440001', 'v60e8400-e29b-41d4-a716-446655440001', 'Triple Viral', '5ml', 'INTRAMUSCULAR', 'LV-2026-001', '2024-01-15', '2025-01-15', 'Aplicación exitosa', 'Dr. García', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440002', 'a60e8400-e29b-41d4-a716-446655440002', 'v60e8400-e29b-41d4-a716-446655440003', 'Clostridium C+D', '5ml', 'INTRAMUSCULAR', 'LC-2026-001', '2024-03-20', '2024-09-20', 'Animal tolera bien', 'Dra. López', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440003', 'a60e8400-e29b-41d4-a716-446655440003', 'v60e8400-e29b-41d4-a716-446655440005', 'Fiebre Aftosa', '5ml', 'INTRAMUSCULAR', 'LF-2026-001', '2024-08-05', '2025-08-05', 'Protocolo oficial', 'Dr. García', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440004', 'a60e8400-e29b-41d4-a716-446655440004', 'v60e8400-e29b-41d4-a716-446655440004', 'Leptospirosis', '5ml', 'INTRAMUSCULAR', 'LL-2026-001', '2024-04-12', '2024-07-12', 'Refuerzo completado', 'Dra. López', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440005', 'a60e8400-e29b-41d4-a716-446655440005', 'v60e8400-e29b-41d4-a716-446655440002', 'Brucela', '2ml', 'SUBCUTANEA', 'LB-2026-001', '2024-02-10', '2025-02-10', 'Sin reacciones adversas', 'Dr. García', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440006', 'a60e8400-e29b-41d4-a716-446655440006', 'v60e8400-e29b-41d4-a716-446655440010', 'Complejo Respiratorio', '5ml', 'INTRAMUSCULAR', 'LCR-2026-001', '2024-12-10', '2025-12-10', 'Protección integral', 'Dr. García', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440007', 'a60e8400-e29b-41d4-a716-446655440001', 'v60e8400-e29b-41d4-a716-446655440008', 'Anaplasmosis', '5ml', 'INTRAMUSCULAR', 'LA-2026-001', '2024-10-20', '2025-04-20', 'Prevención de recaída', 'Dra. López', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440008', 'a60e8400-e29b-41d4-a716-446655440004', 'v60e8400-e29b-41d4-a716-446655440009', 'Babesiosis', '5ml', 'INTRAMUSCULAR', 'LBB-2026-001', '2024-11-15', '2025-05-15', 'Susceptibilidad controlada', 'Dr. García', 'r60e8400-e29b-41d4-a716-446655440001', 'default');

-- ============================================================
--  HISTORIAL CLÍNICO RANCHO 2 (Los Altos)
-- ============================================================

INSERT IGNORE INTO historial_clinico (id, animal_id, vacuna_id, nombre_vacuna, dosis, via_administracion, lote, fecha_aplicacion, proxima_dosis, notas, aplicado_por, rancho_id, tenant_id) VALUES 
    ('h60e8400-e29b-41d4-a716-446655440009', 'a60e8400-e29b-41d4-a716-446655440007', 'v60e8400-e29b-41d4-a716-446655440002', 'Brucela', '2ml', 'SUBCUTANEA', 'LB-2026-001', '2024-02-10', '2025-02-10', 'Sin reacciones adversas', 'Dr. Pérez', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440010', 'a60e8400-e29b-41d4-a716-446655440008', 'v60e8400-e29b-41d4-a716-446655440001', 'Triple Viral', '5ml', 'INTRAMUSCULAR', 'LV-2026-001', '2024-01-15', '2025-01-15', 'Aplicación exitosa', 'Dr. Pérez', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440011', 'a60e8400-e29b-41d4-a716-446655440009', 'v60e8400-e29b-41d4-a716-446655440005', 'Fiebre Aftosa', '5ml', 'INTRAMUSCULAR', 'LF-2026-001', '2024-08-05', '2025-08-05', 'Protocolo oficial', 'Dr. Pérez', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440012', 'a60e8400-e29b-41d4-a716-446655440010', 'v60e8400-e29b-41d4-a716-446655440004', 'Leptospirosis', '5ml', 'INTRAMUSCULAR', 'LL-2026-001', '2024-04-12', '2024-07-12', 'Refuerzo completado', 'Dr. Pérez', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440013', 'a60e8400-e29b-41d4-a716-446655440011', 'v60e8400-e29b-41d4-a716-446655440003', 'Clostridium C+D', '5ml', 'INTRAMUSCULAR', 'LC-2026-001', '2024-03-20', '2024-09-20', 'Animal tolera bien', 'Dr. Pérez', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440014', 'a60e8400-e29b-41d4-a716-446655440012', 'v60e8400-e29b-41d4-a716-446655440006', 'Rabia', '2ml', 'INTRAMUSCULAR', 'LRB-2026-001', '2024-09-25', '2025-09-25', 'Protección contra rabia', 'Dr. Pérez', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('h60e8400-e29b-41d4-a716-446655440015', 'a60e8400-e29b-41d4-a716-446655440007', 'v60e8400-e29b-41d4-a716-446655440010', 'Complejo Respiratorio', '5ml', 'INTRAMUSCULAR', 'LCR-2026-001', '2024-12-10', '2025-12-10', 'Protección integral', 'Dr. Pérez', 'r60e8400-e29b-41d4-a716-446655440002', 'default');

-- ============================================================
--  VENTAS DE GANADO (5 registros por rancho)
-- ============================================================

INSERT IGNORE INTO ventas_ganado (id, arete_id, nombre_comprador, ine, credencial_cedafod, guia_pdf, rancho_id, fecha_venta, tenant_id) VALUES 
    ('v60e8400-e29b-41d4-a716-446655440001', 'SM-2024-003', 'Carlos Mendoza López', '1234567890', 'CEDAFOD-2024-001', '/docs/guia-2024-sm-003.pdf', 'r60e8400-e29b-41d4-a716-446655440001', '2024-03-15', 'default'),
    ('v60e8400-e29b-41d4-a716-446655440002', 'SM-2024-001', 'José Antonio Ruiz', '2345678901', 'CEDAFOD-2024-002', '/docs/guia-2024-sm-001.pdf', 'r60e8400-e29b-41d4-a716-446655440001', '2024-04-20', 'default'),
    ('v60e8400-e29b-41d4-a716-446655440003', 'SM-2024-005', 'María del Carmen Gómez', '3456789012', 'CEDAFOD-2024-003', '/docs/guia-2024-sm-005.pdf', 'r60e8400-e29b-41d4-a716-446655440001', '2024-05-10', 'default'),
    ('v60e8400-e29b-41d4-a716-446655440004', 'LA-2024-001', 'Francisco Javier Díaz', '4567890123', 'CEDAFOD-2024-004', '/docs/guia-2024-la-001.pdf', 'r60e8400-e29b-41d4-a716-446655440002', '2024-06-05', 'default'),
    ('v60e8400-e29b-41d4-a716-446655440005', 'LA-2024-002', 'Laura Estrada Morales', '5678901234', 'CEDAFOD-2024-005', '/docs/guia-2024-la-002.pdf', 'r60e8400-e29b-41d4-a716-446655440002', '2024-07-12', 'default');

-- ============================================================
--  EVENTOS DEL CALENDARIO (Compartidos entre ranchos)
-- ============================================================

INSERT IGNORE INTO eventos_calendario (id, titulo, descripcion, fecha, tipo, rancho_id, tenant_id) VALUES 
    ('e60e8400-e29b-41d4-a716-446655440001', 'Revisión veterinaria mensual - Rancho Santa María', 'Control sanitario y vacunación del hato lechero', '2024-12-15', 'OTRO', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440002', 'Ordeño especial de prueba - Santa María', 'Análisis de composición láctea', '2024-12-20', 'OTRO', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440003', 'Inseminación de vacas - Rancho Santa María', 'Reproducción controlada', '2024-12-25', 'OTRO', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440004', 'Desparasitación del ganado - Santa María', 'Antiparasitarios internos y externos', '2024-12-28', 'OTRO', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440005', 'Cambio de pastura - Rancho Los Altos', 'Rotación de potreros', '2025-01-05', 'OTRO', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440006', 'Limpieza de instalaciones - Los Altos', 'Desinfección de corrales', '2025-01-10', 'OTRO', 'r60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440007', 'Mantenimiento preventivo - Ambos ranchos', 'Revisión de equipos y herramientas', '2025-01-15', 'OTRO', NULL, 'default'),
    ('e60e8400-e29b-41d4-a716-446655440008', 'Aplicación de refuerzo vacunal - Rancho Santa María', 'IBR y DVB refuerzo', '2025-01-20', 'OTRO', 'r60e8400-e29b-41d4-a716-446655440001', 'default');

-- ============================================================
--  BECERROS (5 por rancho)
-- ============================================================

INSERT IGNORE INTO becerros (id, nombre, fecha_nacimiento, sexo, nombre_padre, raza_padre, notas, madre_id, ciclo_id, rancho_id, tenant_id) VALUES 
    ('b60e8400-e29b-41d4-a716-446655440001', 'Becerro Negro SM-001', '2025-05-03', 'MACHO', 'Toro Negro Premium', 'Holsteín', 'Excelentes características', 'a60e8400-e29b-41d4-a716-446655440002', 'c60e8400-e29b-41d4-a716-446655440002', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('b60e8400-e29b-41d4-a716-446655440002', 'Becerro Blanco SM-002', '2025-05-05', 'HEMBRA', 'Jersey Bull', 'Jersey', 'Hembra promisoria', 'a60e8400-e29b-41d4-a716-446655440001', 'c60e8400-e29b-41d4-a716-446655440001', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('b60e8400-e29b-41d4-a716-446655440003', 'Becerro Pinto SM-003', '2025-03-19', 'MACHO', 'Simmental Rojo', 'Simmental', 'Crecimiento rápido', 'a60e8400-e29b-41d4-a716-446655440003', 'c60e8400-e29b-41d4-a716-446655440003', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('b60e8400-e29b-41d4-a716-446655440004', 'Becerro Café SM-004', '2025-02-10', 'HEMBRA', 'Brahman Gold', 'Brahman', 'Adaptable', 'a60e8400-e29b-41d4-a716-446655440004', 'c60e8400-e29b-41d4-a716-446655440003', 'r60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('b60e8400-e29b-41d4-a716-446655440005', 'Becerro Dorado LA-001', '2025-02-16', 'MACHO', 'Toro Brahman', 'Brahman', 'Vigía potencial', 'a60e8400-e29b-41d4-a716-446655440009', 'c60e8400-e29b-41d4-a716-446655440006', 'r60e8400-e29b-41d4-a716-446655440002', 'default');


