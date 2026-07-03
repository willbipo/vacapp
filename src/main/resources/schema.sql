-- ============================================================
--  Vacapp — Schema MySQL
--  Spring Boot ejecuta este archivo al iniciar (spring.sql.init.mode=always).
--  Usa CREATE TABLE IF NOT EXISTS para ser idempotente.
-- ============================================================

CREATE TABLE IF NOT EXISTS usuarios (
    id         CHAR(36)     NOT NULL PRIMARY KEY,
    username   VARCHAR(100) NOT NULL UNIQUE,
    email      VARCHAR(255),
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50)  NOT NULL,
    tenant_id  VARCHAR(100) NOT NULL,
    INDEX idx_usuarios_tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS animales (
    id                    CHAR(36)     NOT NULL PRIMARY KEY,
    numero_identificador  VARCHAR(100) NOT NULL,
    estatus               VARCHAR(50),
    sexo                  VARCHAR(50),
    raza                  VARCHAR(100),
    fecha_nacimiento      DATE,
    meses                 INT,
    fecha_aretado         DATE,
    tipo                  VARCHAR(50),
    arete_anterior        VARCHAR(100),
    folio_reemo           VARCHAR(100),
    nota                  TEXT,
    categoria             VARCHAR(100),
    fecha_inicio_reposo   DATE,
    fecha_fin_reposo      DATE,
    tenant_id             VARCHAR(100) NOT NULL,
    INDEX idx_animales_tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS vacunas (
    id                         CHAR(36)     NOT NULL PRIMARY KEY,
    nombre                     VARCHAR(255) NOT NULL,
    tipo                       VARCHAR(100),
    laboratorio                VARCHAR(255),
    descripcion                TEXT,
    dosis                      VARCHAR(100),
    via_administracion         VARCHAR(100),
    lote                       VARCHAR(100),
    fecha_caducidad            DATE,
    stock                      INT,
    unidad_medida              VARCHAR(50),
    temperatura_almacenamiento VARCHAR(100),
    intervalo_dias             INT,
    tenant_id                  VARCHAR(100) NOT NULL,
    INDEX idx_vacunas_tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS insumos (
    id               CHAR(36)       NOT NULL PRIMARY KEY,
    nombre           VARCHAR(255)   NOT NULL,
    categoria        VARCHAR(100),
    unidad_medida    VARCHAR(50),
    cantidad         DOUBLE,
    cantidad_minima  DOUBLE,
    descripcion      TEXT,
    proveedor        VARCHAR(255),
    precio_unitario  DECIMAL(10,2),
    ubicacion        VARCHAR(255),
    tenant_id        VARCHAR(100)   NOT NULL,
    INDEX idx_insumos_tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS ventas_ganado (
    id                  CHAR(36)     NOT NULL PRIMARY KEY,
    arete_id            VARCHAR(100) NOT NULL,
    nombre_comprador    VARCHAR(255) NOT NULL,
    ine                 VARCHAR(100) NOT NULL,
    credencial_cedafod  VARCHAR(500),
    guia_pdf            VARCHAR(500),
    fecha_venta         DATE         NOT NULL,
    tenant_id           VARCHAR(100) NOT NULL,
    INDEX idx_ventas_tenant (tenant_id),
    INDEX idx_ventas_arete  (arete_id)
);

CREATE TABLE IF NOT EXISTS historial_clinico (
    id                  CHAR(36)     NOT NULL PRIMARY KEY,
    animal_id           CHAR(36)     NOT NULL,
    vacuna_id           CHAR(36),
    nombre_vacuna       VARCHAR(255) NOT NULL,
    dosis               VARCHAR(100),
    via_administracion  VARCHAR(100),
    lote                VARCHAR(100),
    fecha_aplicacion    DATE         NOT NULL,
    proxima_dosis       DATE,
    notas               TEXT,
    aplicado_por        VARCHAR(255),
    tenant_id           VARCHAR(100) NOT NULL,
    INDEX idx_historial_animal (animal_id),
    INDEX idx_historial_tenant (tenant_id)
);

-- Migración: agrega proxima_dosis si la tabla ya existía sin esa columna
ALTER TABLE historial_clinico
    ADD COLUMN IF NOT EXISTS proxima_dosis DATE AFTER fecha_aplicacion;

CREATE TABLE IF NOT EXISTS categorias_insumos (
    id        CHAR(36)     NOT NULL PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    tenant_id VARCHAR(100) NOT NULL,
    INDEX idx_cat_insumos_tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS categorias_vacunas (
    id        CHAR(36)     NOT NULL PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    tenant_id VARCHAR(100) NOT NULL,
    INDEX idx_cat_vacunas_tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS categorias_ganado (
    id        CHAR(36)     NOT NULL PRIMARY KEY,
    nombre    VARCHAR(100) NOT NULL,
    tenant_id VARCHAR(100) NOT NULL,
    INDEX idx_cat_ganado_tenant (tenant_id)
);

CREATE TABLE IF NOT EXISTS eventos_calendario (
    id          CHAR(36)     NOT NULL PRIMARY KEY,
    titulo      VARCHAR(255) NOT NULL,
    descripcion TEXT,
    fecha       DATE         NOT NULL,
    tipo        VARCHAR(50)  NOT NULL DEFAULT 'OTRO',
    tenant_id   VARCHAR(100) NOT NULL,
    INDEX idx_cal_tenant (tenant_id),
    INDEX idx_cal_fecha  (fecha)
);

CREATE TABLE IF NOT EXISTS ciclos_reproductivos (
    id                   CHAR(36)     NOT NULL PRIMARY KEY,
    vaca_id              CHAR(36)     NOT NULL,
    fecha_inicio         DATE         NOT NULL,
    fecha_estimada_parto DATE         NOT NULL,
    fecha_parto_real     DATE,
    dias_reposo          INT,
    fecha_fin_reposo     DATE,
    estatus              VARCHAR(20)  NOT NULL DEFAULT 'EN_CURSO',
    notas                TEXT,
    tenant_id            VARCHAR(100) NOT NULL,
    INDEX idx_ciclo_tenant (tenant_id),
    INDEX idx_ciclo_vaca   (vaca_id),
    INDEX idx_ciclo_estatus (estatus)
);

CREATE TABLE IF NOT EXISTS becerros (
    id               CHAR(36)     NOT NULL PRIMARY KEY,
    nombre           VARCHAR(100) NOT NULL,
    fecha_nacimiento DATE         NOT NULL,
    sexo             VARCHAR(20)  NOT NULL,
    nombre_padre     VARCHAR(100),
    raza_padre       VARCHAR(100),
    notas            TEXT,
    madre_id         CHAR(36)     NOT NULL,
    ciclo_id         CHAR(36)     NOT NULL,
    tenant_id        VARCHAR(100) NOT NULL,
    INDEX idx_becerro_tenant (tenant_id),
    INDEX idx_becerro_madre  (madre_id)
);
