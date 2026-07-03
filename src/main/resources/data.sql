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

-- Insertar usuario FARMER (Ganadero)
INSERT IGNORE INTO usuarios (id, username, email, password, role, tenant_id) VALUES (
    '550e8400-e29b-41d4-a716-446655440002',
    'farmer',
    'farmer@vacapp.test',
    'farmer123',
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
    ('550e8400-e29b-41d4-a716-446655440009', 'ana_farmer', 'ana@vacapp.test', 'pass123', 'FARMER', 'default'),
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
--  INSUMOS (20 registros)
-- ============================================================

INSERT IGNORE INTO insumos (id, nombre, categoria, unidad_medida, cantidad, cantidad_minima, descripcion, proveedor, precio_unitario, ubicacion, tenant_id) VALUES 
    ('960e8400-e29b-41d4-a716-446655440001', 'Pienso concentrado 18%', 'ALIMENTO', 'SACO', 50, 10, 'Alimento balanceado para ganado lechero', 'Proveeduría García', 25.50, 'Bodega A', 'default'),
    ('960e8400-e29b-41d4-a716-446655440002', 'Sal mineralizada', 'ALIMENTO', 'KILOGRAMO', 100, 20, 'Sales minerales para suplementación', 'Química Rural', 5.99, 'Bodega B', 'default'),
    ('960e8400-e29b-41d4-a716-446655440003', 'Forraje seco', 'ALIMENTO', 'TONELADA', 15, 3, 'Heno de calidad premium', 'Forrajes del Sur', 150.00, 'Silos', 'default'),
    ('960e8400-e29b-41d4-a716-446655440004', 'Jeringa 10cc', 'MEDICAMENTO', 'UNIDAD', 200, 50, 'Jeringas desechables estériles', 'LabMédi', 0.85, 'Botiquín', 'default'),
    ('960e8400-e29b-41d4-a716-446655440005', 'Amoxicilina inyectable', 'MEDICAMENTO', 'UNIDAD', 30, 10, 'Antibiótico para ganado', 'LabMédi', 8.50, 'Botiquín', 'default'),
    ('960e8400-e29b-41d4-a716-446655440006', 'Marcador para ganado rojo', 'HERRAMIENTA', 'UNIDAD', 15, 3, 'Marcador de pintura para identificación', 'Herramientas Pro', 12.00, 'Taller', 'default'),
    ('960e8400-e29b-41d4-a716-446655440007', 'Tenazas de ordeño', 'EQUIPO', 'UNIDAD', 8, 2, 'Tenazas de alta resistencia', 'Equipos Agro', 45.00, 'Taller', 'default'),
    ('960e8400-e29b-41d4-a716-446655440008', 'Cuerda de nylon 50m', 'HERRAMIENTA', 'ROLLO', 20, 5, 'Cuerda resistente para amarres', 'Herramientas Pro', 35.00, 'Bodega C', 'default'),
    ('960e8400-e29b-41d4-a716-446655440009', 'Desinfectante de ubre', 'MEDICAMENTO', 'LITRO', 25, 5, 'Desinfectante para limpieza de ubres', 'HigienePro', 18.50, 'Botiquín', 'default'),
    ('960e8400-e29b-41d4-a716-446655440010', 'Vitaminas AD3E', 'MEDICAMENTO', 'LITRO', 12, 3, 'Complejo vitamínico para ganado', 'NutriBest', 42.00, 'Botiquín', 'default'),
    ('960e8400-e29b-41d4-a716-446655440011', 'Escoba para ordeño', 'HERRAMIENTA', 'UNIDAD', 10, 2, 'Escoba de fibra natural', 'Herramientas Pro', 8.50, 'Taller', 'default'),
    ('960e8400-e29b-41d4-a716-446655440012', 'Baldes de 20L', 'EQUIPO', 'UNIDAD', 30, 10, 'Balde plástico con tapa', 'Equipos Agro', 12.00, 'Bodega A', 'default'),
    ('960e8400-e29b-41d4-a716-446655440013', 'Antiséptico yodado', 'MEDICAMENTO', 'LITRO', 8, 2, 'Antiséptico para heridas', 'HigienePro', 22.00, 'Botiquín', 'default'),
    ('960e8400-e29b-41d4-a716-446655440014', 'Cartuchos para inyectora', 'HERRAMIENTA', 'UNIDAD', 50, 15, 'Cartuchos para pistola de inyección', 'Equipos Agro', 0.50, 'Taller', 'default'),
    ('960e8400-e29b-41d4-a716-446655440015', 'Suero fisiológico', 'MEDICAMENTO', 'LITRO', 20, 5, 'Suero para hidratación', 'LabMédi', 15.00, 'Botiquín', 'default'),
    ('960e8400-e29b-41d4-a716-446655440016', 'Arete numérico rojo', 'HERRAMIENTA', 'UNIDAD', 500, 100, 'Aretes numerados para identificación', 'Identificadores Rural', 0.75, 'Almacén', 'default'),
    ('960e8400-e29b-41d4-a716-446655440017', 'Almohadilla de ordeño', 'EQUIPO', 'UNIDAD', 20, 5, 'Almohadilla de goma para ordeñadora', 'Equipos Agro', 28.00, 'Taller', 'default'),
    ('960e8400-e29b-41d4-a716-446655440018', 'Guantes de ordeño', 'HERRAMIENTA', 'UNIDAD', 100, 20, 'Guantes de látex para ordeño', 'HigienePro', 2.50, 'Taller', 'default'),
    ('960e8400-e29b-41d4-a716-446655440019', 'Agua oxigenada 1L', 'MEDICAMENTO', 'LITRO', 40, 10, 'Agua oxigenada para desinfección', 'Química Rural', 4.50, 'Botiquín', 'default'),
    ('960e8400-e29b-41d4-a716-446655440020', 'Alcohol al 70%', 'MEDICAMENTO', 'LITRO', 35, 8, 'Alcohol para limpieza y desinfección', 'Química Rural', 6.00, 'Botiquín', 'default');

-- ============================================================
--  VACUNAS (20 registros)
-- ============================================================

INSERT IGNORE INTO vacunas (id, nombre, tipo, laboratorio, descripcion, dosis, via_administracion, lote, fecha_caducidad, stock, unidad_medida, temperatura_almacenamiento, intervalo_dias, tenant_id) VALUES 
    ('a60e8400-e29b-41d4-a716-446655440001', 'Triple Viral (Rinotraqueítis, DVB, IBR)', 'VIRAL', 'LabVet', 'Previene virosis respiratoria', '5ml', 'INTRAMUSCULAR', 'LV-2026-001', '2027-03-15', 200, 'UNIDAD', '2-8°C', 30, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440002', 'Brucela (Cepa B19)', 'BACTERIANA', 'LabVet', 'Previene brucelosis', '2ml', 'SUBCUTANEA', 'LB-2026-001', '2027-06-30', 150, 'UNIDAD', '2-8°C', 365, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440003', 'Tuberculina', 'BACTERIANA', 'DiagnostiVet', 'Diagnóstico de tuberculosis', '0.1ml', 'SUBCUTANEA', 'LT-2026-001', '2027-02-28', 100, 'UNIDAD', '2-8°C', 14, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440004', 'Clostridium Perfringens C+D', 'BACTERIANA', 'LabVet', 'Previene clostridiosis', '5ml', 'INTRAMUSCULAR', 'LC-2026-001', '2027-05-10', 180, 'UNIDAD', '2-8°C', 180, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440005', 'Leptospirosis Hardjo/Pomona', 'BACTERIANA', 'LabVet', 'Previene leptospirosis', '5ml', 'INTRAMUSCULAR', 'LL-2026-001', '2027-04-20', 160, 'UNIDAD', '2-8°C', 90, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440006', 'BVD + IBR + DVB + PI3', 'VIRAL', 'LabVet', 'Vacuna combinada respiratoria', '5ml', 'INTRAMUSCULAR', 'LM-2026-001', '2027-03-30', 140, 'UNIDAD', '2-8°C', 30, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440007', 'Rinotraqueítis Infecciosa', 'VIRAL', 'MedicVet', 'Previene IBR', '5ml', 'INTRAMUSCULAR', 'LR-2026-001', '2027-07-15', 120, 'UNIDAD', '2-8°C', 30, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440008', 'Diarrea Viral Bovina', 'VIRAL', 'MedicVet', 'Previene DVB', '5ml', 'INTRAMUSCULAR', 'LD-2026-001', '2027-08-10', 130, 'UNIDAD', '2-8°C', 30, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440009', 'Paratuberculosis (Johne)', 'BACTERIANA', 'DiagnostiVet', 'Diagnóstico de paratuberculosis', '0.1ml', 'SUBCUTANEA', 'LP-2026-001', '2027-01-20', 80, 'UNIDAD', '2-8°C', 21, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440010', 'Mastitis (Streptococcus agalactiae)', 'BACTERIANA', 'LabVet', 'Previene mastitis', '5ml', 'INTRAMUSCULAR', 'LEM-2026-001', '2027-05-05', 170, 'UNIDAD', '2-8°C', 60, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440011', 'Fiebre Aftosa Polyvalente', 'VIRAL', 'LabVet', 'Previene fiebre aftosa (O,A,C)', '5ml', 'INTRAMUSCULAR', 'LF-2026-001', '2027-12-31', 250, 'UNIDAD', '2-8°C', 180, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440012', 'Virus de la Diarrea Viral', 'VIRAL', 'MedicVet', 'Previene DVB aguda', '5ml', 'INTRAMUSCULAR', 'LDV-2026-001', '2027-09-20', 145, 'UNIDAD', '2-8°C', 30, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440013', 'Parainfluenza 3', 'VIRAL', 'MedicVet', 'Previene PI3 respiratoria', '5ml', 'INTRAMUSCULAR', 'LPI-2026-001', '2027-10-15', 125, 'UNIDAD', '2-8°C', 30, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440014', 'Rabia Inactivada', 'VIRAL', 'LabVet', 'Previene rabia bovina', '2ml', 'INTRAMUSCULAR', 'LRB-2026-001', '2028-02-28', 90, 'UNIDAD', '2-8°C', 365, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440015', 'Encefalitis Equina', 'VIRAL', 'DiagnostiVet', 'Previene encefalitis', '5ml', 'INTRAMUSCULAR', 'LEE-2026-001', '2027-04-10', 110, 'UNIDAD', '2-8°C', 180, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440016', 'Anaplasmosis Bovina', 'BACTERIANA', 'LabVet', 'Previene anaplasmosis', '5ml', 'INTRAMUSCULAR', 'LA-2026-001', '2027-06-15', 135, 'UNIDAD', '2-8°C', 180, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440017', 'Tripanosomosis (Murrina)', 'PARASITARIA', 'LabVet', 'Previene tripanosomosis', '5ml', 'INTRAMUSCULAR', 'LT-2026-002', '2027-07-30', 75, 'UNIDAD', '2-8°C', 90, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440018', 'Babesiosis', 'PARASITARIA', 'MedicVet', 'Previene babesiosis', '5ml', 'INTRAMUSCULAR', 'LBB-2026-001', '2027-08-25', 95, 'UNIDAD', '2-8°C', 180, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440019', 'Piroplasmosis', 'PARASITARIA', 'DiagnostiVet', 'Previene piroplasmosis', '5ml', 'INTRAMUSCULAR', 'LPP-2026-001', '2027-09-05', 85, 'UNIDAD', '2-8°C', 180, 'default'),
    ('a60e8400-e29b-41d4-a716-446655440020', 'Complejo Respiratorio Bovino', 'COMBINADA', 'LabVet', 'Previene múltiples patógenos respiratorios', '5ml', 'INTRAMUSCULAR', 'LCR-2026-001', '2027-11-30', 210, 'UNIDAD', '2-8°C', 30, 'default');

-- ============================================================
--  ANIMALES (20 registros)
-- ============================================================

INSERT IGNORE INTO animales (id, numero_identificador, estatus, sexo, raza, fecha_nacimiento, meses, fecha_aretado, tipo, arete_anterior, folio_reemo, nota, categoria, fecha_inicio_reposo, fecha_fin_reposo, tenant_id) VALUES 
    ('b60e8400-e29b-41d4-a716-446655440001', '2024-001', 'VIGENTE', 'HEMBRA', 'Holsteín', '2022-01-15', 30, '2022-02-10', 'TRASLADO', NULL, 'RF-2024-001', 'Animal de alta producción', 'Lechero', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440002', '2024-002', 'VIGENTE', 'MACHO', 'Angus', '2023-06-20', 13, '2023-07-15', 'VENTA', NULL, NULL, 'Reproductor joven', 'Carne', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440003', '2024-003', 'VIGENTE', 'HEMBRA', 'Jersey', '2021-03-10', 39, '2021-04-05', 'OTRO', 'AR-2020-456', 'RJ-2024-003', 'Vaca primípara', 'Lechero', '2025-08-01', '2025-10-15', 'default'),
    ('b60e8400-e29b-41d4-a716-446655440004', '2024-004', 'VIGENTE', 'MACHO', 'Charolés', '2023-11-05', 8, '2023-12-01', 'VENTA', NULL, NULL, 'Toro joven prometedor', 'Doble propósito', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440005', '2024-005', 'VIGENTE', 'HEMBRA', 'Brahman', '2022-05-30', 25, '2022-06-20', 'TRASLADO', NULL, 'RB-2024-005', 'Vaca resistente a clima', 'Doble propósito', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440006', '2024-006', 'BAJA', 'MACHO', 'Simmental', '2020-02-12', 48, '2020-03-10', 'OTRO', 'AR-2019-123', 'RS-2024-006', 'Animal retirado de producción', 'Carne', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440007', '2024-007', 'VIGENTE', 'HEMBRA', 'Guernsey', '2023-01-08', 19, '2023-02-05', 'VENTA', NULL, NULL, 'Vaca segunda lactancia', 'Lechero', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440008', '2024-008', 'VIGENTE', 'MACHO', 'Limousin', '2023-08-22', 11, '2023-09-15', 'TRASLADO', NULL, 'RL-2024-008', 'Toro reproductor en desarrollo', 'Carne', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440009', '2024-009', 'VIGENTE', 'HEMBRA', 'Pardo Suizo', '2021-07-14', 31, '2021-08-10', 'OTRO', NULL, 'RPS-2024-009', 'Vaca multípara', 'Doble propósito', '2025-06-15', '2025-09-30', 'default'),
    ('b60e8400-e29b-41d4-a716-446655440010', '2024-010', 'VIGENTE', 'MACHO', 'Hereford', '2024-02-03', 5, '2024-03-01', 'VENTA', NULL, NULL, 'Becerro recién identificado', 'Carne', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440011', '2024-011', 'VIGENTE', 'HEMBRA', 'Suizo Europeo', '2022-09-18', 21, '2022-10-15', 'TRASLADO', NULL, 'RSE-2024-011', 'Vaca joven prometedora', 'Lechero', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440012', '2024-012', 'VIGENTE', 'MACHO', 'Nelore', '2023-04-25', 16, '2023-05-20', 'OTRO', NULL, 'RN-2024-012', 'Toro adaptado al trópico', 'Carne', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440013', '2024-013', 'VIGENTE', 'HEMBRA', 'Holstein Negro', '2020-11-20', 43, '2020-12-15', 'VENTA', 'AR-2020-789', 'RHN-2024-013', 'Vaca de excelente producción', 'Lechero', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440014', '2024-014', 'VIGENTE', 'MACHO', 'Braford', '2023-03-05', 17, '2023-04-01', 'TRASLADO', NULL, 'RBF-2024-014', 'Toro híbrido calidad', 'Doble propósito', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440015', '2024-015', 'VIGENTE', 'HEMBRA', 'Ayrshire', '2021-12-10', 37, '2022-01-05', 'OTRO', NULL, 'RA-2024-015', 'Vaca adaptable', 'Lechero', '2025-10-01', '2025-12-20', 'default'),
    ('b60e8400-e29b-41d4-a716-446655440016', '2024-016', 'BAJA', 'MACHO', 'Piemontés', '2019-06-15', 55, '2019-07-10', 'VENTA', 'AR-2019-555', 'RP-2024-016', 'Animal vendido', 'Carne', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440017', '2024-017', 'VIGENTE', 'HEMBRA', 'Normanda', '2022-10-28', 27, '2022-11-20', 'TRASLADO', NULL, 'RN-2024-017', 'Vaca lechera-cárnica', 'Doble propósito', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440018', '2024-018', 'VIGENTE', 'MACHO', 'Gelbvieh', '2023-07-12', 12, '2023-08-08', 'OTRO', NULL, NULL, 'Toro color dorado', 'Carne', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440019', '2024-019', 'VIGENTE', 'HEMBRA', 'Fleckvieh', '2021-04-22', 34, '2021-05-18', 'VENTA', NULL, 'RF-2024-019', 'Vaca rústica', 'Doble propósito', NULL, NULL, 'default'),
    ('b60e8400-e29b-41d4-a716-446655440020', '2024-020', 'VIGENTE', 'MACHO', 'Salers', '2024-01-10', 6, '2024-02-05', 'TRASLADO', NULL, NULL, 'Becerro en destete', 'Carne', NULL, NULL, 'default');

-- ============================================================
--  CICLOS REPRODUCTIVOS (15 registros)
-- ============================================================

INSERT IGNORE INTO ciclos_reproductivos (id, vaca_id, fecha_inicio, fecha_estimada_parto, fecha_parto_real, dias_reposo, fecha_fin_reposo, estatus, notas, tenant_id) VALUES 
    ('c60e8400-e29b-41d4-a716-446655440001', 'b60e8400-e29b-41d4-a716-446655440001', '2024-09-15', '2025-06-15', NULL, 60, NULL, 'EN_CURSO', 'Vaca primípara en buen estado', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440002', 'b60e8400-e29b-41d4-a716-446655440003', '2024-08-01', '2025-05-01', '2025-05-03', 60, '2025-07-02', 'FINALIZADO', 'Parto sin complicaciones', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440003', 'b60e8400-e29b-41d4-a716-446655440005', '2024-07-10', '2025-04-10', NULL, 60, NULL, 'EN_CURSO', 'Animal gestante', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440004', 'b60e8400-e29b-41d4-a716-446655440007', '2024-06-20', '2025-03-20', '2025-03-19', 60, '2025-05-18', 'FINALIZADO', 'Parto adelantado 1 día', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440005', 'b60e8400-e29b-41d4-a716-446655440009', '2024-05-15', '2025-02-15', '2025-02-16', 60, '2025-04-16', 'FINALIZADO', 'Becerro macho viable', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440006', 'b60e8400-e29b-41d4-a716-446655440011', '2024-10-05', '2025-07-05', NULL, 60, NULL, 'EN_CURSO', 'Vaca joven bien nutrida', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440007', 'b60e8400-e29b-41d4-a716-446655440013', '2024-04-01', '2024-12-31', '2025-01-02', 60, '2025-03-02', 'FINALIZADO', 'Parto sin anomalías', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440008', 'b60e8400-e29b-41d4-a716-446655440015', '2024-09-01', '2025-06-01', NULL, 60, NULL, 'EN_CURSO', 'Seguimiento regular', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440009', 'b60e8400-e29b-41d4-a716-446655440017', '2024-08-10', '2025-05-10', NULL, 60, NULL, 'EN_CURSO', 'Vaca robusta', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440010', 'b60e8400-e29b-41d4-a716-446655440019', '2024-03-15', '2024-12-15', '2024-12-14', 60, '2025-02-12', 'FINALIZADO', 'Becerro hembra excelente', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440011', 'b60e8400-e29b-41d4-a716-446655440001', '2024-02-10', '2024-11-10', '2024-11-12', 60, '2025-01-11', 'FINALIZADO', 'Segundo parto exitoso', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440012', 'b60e8400-e29b-41d4-a716-446655440003', '2023-12-20', '2024-09-20', '2024-09-18', 60, '2024-11-17', 'FINALIZADO', 'Ciclo anterior completado', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440013', 'b60e8400-e29b-41d4-a716-446655440005', '2023-11-05', '2024-08-05', '2024-08-06', 60, '2024-10-05', 'FINALIZADO', 'Periodo de descanso completado', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440014', 'b60e8400-e29b-41d4-a716-446655440007', '2023-10-15', '2024-07-15', '2024-07-17', 60, '2024-09-15', 'FINALIZADO', 'Animal descansado', 'default'),
    ('c60e8400-e29b-41d4-a716-446655440015', 'b60e8400-e29b-41d4-a716-446655440009', '2023-09-22', '2024-06-22', '2024-06-23', 60, '2024-08-22', 'FINALIZADO', 'Vaca con buen potencial', 'default');

-- ============================================================
--  HISTORIAL CLÍNICO (20 registros)
-- ============================================================

INSERT IGNORE INTO historial_clinico (id, animal_id, vacuna_id, nombre_vacuna, dosis, via_administracion, lote, fecha_aplicacion, proxima_dosis, notas, aplicado_por, tenant_id) VALUES 
    ('d60e8400-e29b-41d4-a716-446655440001', 'b60e8400-e29b-41d4-a716-446655440001', 'a60e8400-e29b-41d4-a716-446655440001', 'Triple Viral', '5ml', 'INTRAMUSCULAR', 'LV-2026-001', '2024-01-15', '2025-01-15', 'Aplicación exitosa', 'Dr. García', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440002', 'b60e8400-e29b-41d4-a716-446655440002', 'a60e8400-e29b-41d4-a716-446655440002', 'Brucela', '2ml', 'SUBCUTANEA', 'LB-2026-001', '2024-02-10', '2025-02-10', 'Sin reacciones adversas', 'Dr. Pérez', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440003', 'b60e8400-e29b-41d4-a716-446655440003', 'a60e8400-e29b-41d4-a716-446655440003', 'Tuberculina', '0.1ml', 'SUBCUTANEA', 'LT-2026-001', '2024-03-05', '2024-03-19', 'Prueba diagnóstica', 'Dra. López', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440004', 'b60e8400-e29b-41d4-a716-446655440004', 'a60e8400-e29b-41d4-a716-446655440004', 'Clostridium C+D', '5ml', 'INTRAMUSCULAR', 'LC-2026-001', '2024-03-20', '2024-09-20', 'Animal tolera bien', 'Dr. García', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440005', 'b60e8400-e29b-41d4-a716-446655440005', 'a60e8400-e29b-41d4-a716-446655440005', 'Leptospirosis', '5ml', 'INTRAMUSCULAR', 'LL-2026-001', '2024-04-12', '2024-07-12', 'Refuerzo completado', 'Dr. Pérez', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440006', 'b60e8400-e29b-41d4-a716-446655440006', 'a60e8400-e29b-41d4-a716-446655440006', 'BVD + IBR + DVB + PI3', '5ml', 'INTRAMUSCULAR', 'LM-2026-001', '2024-05-08', '2025-05-08', 'Respuesta inmune positiva', 'Dra. López', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440007', 'b60e8400-e29b-41d4-a716-446655440007', 'a60e8400-e29b-41d4-a716-446655440007', 'IBR', '5ml', 'INTRAMUSCULAR', 'LR-2026-001', '2024-06-01', '2025-06-01', 'Control preventivo', 'Dr. García', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440008', 'b60e8400-e29b-41d4-a716-446655440008', 'a60e8400-e29b-41d4-a716-446655440008', 'DVB', '5ml', 'INTRAMUSCULAR', 'LD-2026-001', '2024-06-15', '2025-06-15', 'Animal resistente', 'Dr. Pérez', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440009', 'b60e8400-e29b-41d4-a716-446655440009', 'a60e8400-e29b-41d4-a716-446655440009', 'Paratuberculosis', '0.1ml', 'SUBCUTANEA', 'LP-2026-001', '2024-07-02', '2024-07-23', 'Diagnóstico negativo', 'Dra. López', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440010', 'b60e8400-e29b-41d4-a716-446655440010', 'a60e8400-e29b-41d4-a716-446655440010', 'Mastitis', '5ml', 'INTRAMUSCULAR', 'LEM-2026-001', '2024-07-18', '2024-09-17', 'Refuerzo preventivo', 'Dr. García', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440011', 'b60e8400-e29b-41d4-a716-446655440011', 'a60e8400-e29b-41d4-a716-446655440011', 'Fiebre Aftosa', '5ml', 'INTRAMUSCULAR', 'LF-2026-001', '2024-08-05', '2025-08-05', 'Protocolo oficial', 'Dra. López', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440012', 'b60e8400-e29b-41d4-a716-446655440012', 'a60e8400-e29b-41d4-a716-446655440012', 'DVB aguda', '5ml', 'INTRAMUSCULAR', 'LDV-2026-001', '2024-08-22', '2025-08-22', 'Seguimiento anual', 'Dr. Pérez', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440013', 'b60e8400-e29b-41d4-a716-446655440013', 'a60e8400-e29b-41d4-a716-446655440013', 'PI3', '5ml', 'INTRAMUSCULAR', 'LPI-2026-001', '2024-09-10', '2025-09-10', 'Protección respiratoria', 'Dra. López', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440014', 'b60e8400-e29b-41d4-a716-446655440014', 'a60e8400-e29b-41d4-a716-446655440014', 'Rabia', '2ml', 'INTRAMUSCULAR', 'LRB-2026-001', '2024-09-25', '2025-09-25', 'Protección contra rabia', 'Dr. García', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440015', 'b60e8400-e29b-41d4-a716-446655440015', 'a60e8400-e29b-41d4-a716-446655440015', 'Encefalitis Equina', '5ml', 'INTRAMUSCULAR', 'LEE-2026-001', '2024-10-08', '2025-10-08', 'Control anual', 'Dr. Pérez', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440016', 'b60e8400-e29b-41d4-a716-446655440016', 'a60e8400-e29b-41d4-a716-446655440016', 'Anaplasmosis', '5ml', 'INTRAMUSCULAR', 'LA-2026-001', '2024-10-20', '2025-04-20', 'Prevención de recaída', 'Dra. López', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440017', 'b60e8400-e29b-41d4-a716-446655440017', 'a60e8400-e29b-41d4-a716-446655440017', 'Tripanosomosis', '5ml', 'INTRAMUSCULAR', 'LT-2026-002', '2024-11-02', '2024-12-31', 'Refuerzo pendiente', 'Dr. García', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440018', 'b60e8400-e29b-41d4-a716-446655440018', 'a60e8400-e29b-41d4-a716-446655440018', 'Babesiosis', '5ml', 'INTRAMUSCULAR', 'LBB-2026-001', '2024-11-15', '2025-05-15', 'Susceptibilidad controlada', 'Dr. Pérez', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440019', 'b60e8400-e29b-41d4-a716-446655440019', 'a60e8400-e29b-41d4-a716-446655440019', 'Piroplasmosis', '5ml', 'INTRAMUSCULAR', 'LPP-2026-001', '2024-11-28', '2025-05-28', 'Animal resistente', 'Dra. López', 'default'),
    ('d60e8400-e29b-41d4-a716-446655440020', 'b60e8400-e29b-41d4-a716-446655440020', 'a60e8400-e29b-41d4-a716-446655440020', 'Complejo Respiratorio', '5ml', 'INTRAMUSCULAR', 'LCR-2026-001', '2024-12-10', '2025-12-10', 'Protección integral', 'Dr. García', 'default');

-- ============================================================
--  VENTAS DE GANADO (10 registros)
-- ============================================================

INSERT IGNORE INTO ventas_ganado (id, arete_id, nombre_comprador, ine, credencial_cedafod, guia_pdf, fecha_venta, tenant_id) VALUES 
    ('e60e8400-e29b-41d4-a716-446655440001', '2024-002', 'Carlos Mendoza López', '1234567890', 'CEDAFOD-2024-001', '/docs/guia-2024-002.pdf', '2024-03-15', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440002', '2024-004', 'José Antonio Ruiz', '2345678901', 'CEDAFOD-2024-002', '/docs/guia-2024-004.pdf', '2024-04-20', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440003', '2024-010', 'María del Carmen Gómez', '3456789012', 'CEDAFOD-2024-003', '/docs/guia-2024-010.pdf', '2024-05-10', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440004', '2024-013', 'Francisco Javier Díaz', '4567890123', 'CEDAFOD-2024-004', '/docs/guia-2024-013.pdf', '2024-06-05', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440005', '2024-016', 'Laura Estrada Morales', '5678901234', 'CEDAFOD-2024-005', '/docs/guia-2024-016.pdf', '2024-07-12', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440006', '2024-020', 'Roberto Castro López', '6789012345', 'CEDAFOD-2024-006', '/docs/guia-2024-020.pdf', '2024-08-08', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440007', 'AR-2024-001', 'Silvia Ponce García', '7890123456', 'CEDAFOD-2024-007', '/docs/guia-ar-2024-001.pdf', '2024-09-03', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440008', 'AR-2024-002', 'Miguel Ángel Ortiz', '8901234567', 'CEDAFOD-2024-008', '/docs/guia-ar-2024-002.pdf', '2024-09-18', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440009', 'AR-2024-003', 'Patricia Romero Herrera', '9012345678', 'CEDAFOD-2024-009', '/docs/guia-ar-2024-003.pdf', '2024-10-22', 'default'),
    ('e60e8400-e29b-41d4-a716-446655440010', 'AR-2024-004', 'Hernán Alberto Flores', '1011121314', 'CEDAFOD-2024-010', '/docs/guia-ar-2024-004.pdf', '2024-11-15', 'default');

-- ============================================================
--  EVENTOS DEL CALENDARIO (15 registros)
-- ============================================================

INSERT IGNORE INTO eventos_calendario (id, titulo, descripcion, fecha, tipo, tenant_id) VALUES 
    ('f60e8400-e29b-41d4-a716-446655440001', 'Revisión veterinaria mensual', 'Control sanitario y vacunación del hato', '2024-12-15', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440002', 'Ordeño especial de prueba', 'Análisis de composición láctea', '2024-12-20', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440003', 'Inseminación de vaca ID 2024-001', 'Reproducción controlada', '2024-12-25', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440004', 'Desparasitación del ganado', 'Antiparasitarios internos y externos', '2024-12-28', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440005', 'Cambio de pastura', 'Rotación de potreros', '2025-01-05', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440006', 'Limpieza de instalaciones', 'Desinfección de corrales', '2025-01-10', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440007', 'Revisión de ordeñadora', 'Mantenimiento preventivo', '2025-01-15', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440008', 'Aplicación de refuerzo vacunal', 'IBR y DVB refuerzo', '2025-01-20', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440009', 'Ecografía reproductiva', 'Confirmación de gestaciones', '2025-01-25', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440010', 'Asesoría nutricional', 'Revisión de dietas y suplementos', '2025-02-01', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440011', 'Prueba de tuberculina', 'Diagnóstico oficial', '2025-02-05', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440012', 'Limpieza de tanque de leche', 'Sanitización', '2025-02-10', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440013', 'Revisión de cojeras', 'Evaluación sanitaria de pezuñas', '2025-02-15', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440014', 'Capacitación en mastitis', 'Curso para personal', '2025-02-20', 'OTRO', 'default'),
    ('f60e8400-e29b-41d4-a716-446655440015', 'Evaluación de becerros', 'Crianza y desarrollo', '2025-02-25', 'OTRO', 'default');

-- ============================================================
--  BECERROS (10 registros)
-- ============================================================

INSERT IGNORE INTO becerros (id, nombre, fecha_nacimiento, sexo, nombre_padre, raza_padre, notas, madre_id, ciclo_id, tenant_id) VALUES 
    ('g60e8400-e29b-41d4-a716-446655440001', 'Becerro Negro 001', '2025-05-03', 'MACHO', 'Toro Negro Premium', 'Holsteín', 'Excelentes características', 'b60e8400-e29b-41d4-a716-446655440003', 'c60e8400-e29b-41d4-a716-446655440002', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440002', 'Becerro Blanco 002', '2025-05-05', 'HEMBRA', 'Jersey Bull', 'Jersey', 'Hembra promisoria', 'b60e8400-e29b-41d4-a716-446655440001', 'c60e8400-e29b-41d4-a716-446655440001', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440003', 'Becerro Pinto 003', '2025-03-19', 'MACHO', 'Simmental Rojo', 'Simmental', 'Crecimiento rápido', 'b60e8400-e29b-41d4-a716-446655440007', 'c60e8400-e29b-41d4-a716-446655440004', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440004', 'Becerro Café 004', '2025-02-10', 'HEMBRA', 'Brahman Gold', 'Brahman', 'Adaptable', 'b60e8400-e29b-41d4-a716-446655440005', 'c60e8400-e29b-41d4-a716-446655440003', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440005', 'Becerro Dorado 005', '2025-02-16', 'MACHO', 'Toro Brahman', 'Brahman', 'Vigía potencial', 'b60e8400-e29b-41d4-a716-446655440009', 'c60e8400-e29b-41d4-a716-446655440005', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440006', 'Becerro Crema 006', '2025-01-02', 'HEMBRA', 'Toro Suizo', 'Pardo Suizo', 'Para lechería', 'b60e8400-e29b-41d4-a716-446655440013', 'c60e8400-e29b-41d4-a716-446655440007', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440007', 'Becerro Rojo 007', '2024-12-14', 'MACHO', 'Toro Rojo', 'Hereford', 'Carne de calidad', 'b60e8400-e29b-41d4-a716-446655440019', 'c60e8400-e29b-41d4-a716-446655440010', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440008', 'Becerro Jaspe 008', '2024-11-12', 'HEMBRA', 'Toro Holstein', 'Holsteín', 'Buena conformación', 'b60e8400-e29b-41d4-a716-446655440001', 'c60e8400-e29b-41d4-a716-446655440011', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440009', 'Becerro Blond 009', '2024-09-18', 'MACHO', 'Toro Limousin', 'Limousin', 'Excelentes proporciones', 'b60e8400-e29b-41d4-a716-446655440007', 'c60e8400-e29b-41d4-a716-446655440004', 'default'),
    ('g60e8400-e29b-41d4-a716-446655440010', 'Becerro Gris 010', '2024-08-06', 'HEMBRA', 'Toro Charolés', 'Charolés', 'Ganancia de peso acelerada', 'b60e8400-e29b-41d4-a716-446655440005', 'c60e8400-e29b-41d4-a716-446655440013', 'default');

