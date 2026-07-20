# Módulo de Empleados — Vacapp

## 📋 Descripción

Se ha implementado un módulo completo de gestión de empleados para la plataforma Vacapp. Este módulo permite:

- ✅ Registrar nuevos empleados
- ✅ Asignar roles (ADMIN, OPERADOR, VISUALIZADOR)
- ✅ Visualizar empleados registrados
- ✅ Editar información de empleados
- ✅ Enviar invitaciones por correo con links de descarga de app
- ✅ Gestionar estado de empleados (ACTIVO, INACTIVO, PENDIENTE)

---

## 🏗️ Estructura del Módulo

El módulo sigue **Clean Architecture** con 4 capas:

```
com.vacapp/empleados/
├── EmpleadoService.java                    ← API pública
└── internal/
    ├── domain/
    │   ├── model/
    │   │   ├── Empleado.java              ← Entidad de dominio pura
    │   │   ├── Rol.java                    ← Enum de roles
    │   │   ├── Estado.java                 ← Enum de estados
    │   │   └── EmpleadoNoEncontradoException.java
    │   └── repository/
    │       └── EmpleadoRepository.java     ← Puerto (interfaz)
    │
    ├── application/
    │   └── usecases/
    │       ├── RegistrarEmpleadoUseCase.java
    │       ├── ListarEmpleadosUseCase.java
    │       ├── ActualizarEmpleadoUseCase.java
    │       └── ObtenerEmpleadoUseCase.java
    │
    └── infrastructure/
        ├── controllers/
        │   ├── web/
        │   │   └── EmpleadoWebController.java    ← Thymeleaf
        │   ├── mobile/
        │   │   ├── EmpleadoRestController.java   ← REST API
        │   │   └── dtos/
        │   │       ├── EmpleadoRequest.java
        │   │       ├── EmpleadoResponse.java
        │   │       └── MensajeResponse.java
        │   └── EmpleadoExceptionHandler.java
        │
        ├── persistence/
        │   ├── EmpleadoEntity.java        ← @Table JPA
        │   ├── EmpleadoJpaRepository.java ← CrudRepository
        │   ├── EmpleadoMapper.java        ← Mapeo Domain ↔ Entity
        │   └── EmpleadoRepositoryImpl.java ← Implementación
        │
        └── services/
            └── EmailService.java          ← Envío de correos
```

---

## 🗄️ Base de Datos

Se ha creado la tabla `empleados` en `schema.sql`:

```sql
CREATE TABLE IF NOT EXISTS empleados (
    id                    CHAR(36)     NOT NULL PRIMARY KEY,
    nombre                VARCHAR(255) NOT NULL,
    email                 VARCHAR(255) NOT NULL,
    telefono              VARCHAR(20),
    rol                   VARCHAR(50)  NOT NULL,
    estado                VARCHAR(50)  NOT NULL DEFAULT 'PENDIENTE',
    fecha_registro        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    fecha_actualizacion   DATETIME     DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    tenant_id             VARCHAR(100) NOT NULL,
    UNIQUE KEY uk_email_tenant (email, tenant_id),
    INDEX idx_empleados_tenant (tenant_id),
    INDEX idx_empleados_estado (estado)
);
```

**Características:**
- Multi-tenant: filtrado automático por `tenant_id`
- Email único por tenant
- Auditoría: `fecha_registro` y `fecha_actualizacion`
- Estados: ACTIVO, INACTIVO, PENDIENTE

---

## 🎨 Vista Frontend

Ubicación: `src/main/resources/templates/empleados/inventario.html`

**Funcionalidades:**
- 📊 Tabla responsive con datos de empleados
- ➕ Modal para agregar nuevos empleados
- ✏️ Modal para editar empleados existentes
- 📧 Botón para enviar invitaciones por correo
- 🎯 Filtro visual por estado y rol

**Columnas mostradas:**
- Nombre
- Email
- Teléfono
- Rol (badge color-coded)
- Estado (badge color-coded)
- Fecha de registro
- Acciones (Editar, Enviar invitación)

---

## 🔌 API REST

**OpenAPI YAML:** `src/main/resources/openapi/openapi-empleados.yaml`

### Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| `POST` | `/api/v1/empleados` | Registrar nuevo empleado |
| `GET` | `/api/v1/empleados` | Listar todos los empleados |
| `PUT` | `/api/v1/empleados/{id}` | Actualizar empleado |
| `POST` | `/api/v1/empleados/{id}/enviar-invitacion` | Enviar invitación por correo |

### Ejemplo Request (Registrar)

```json
{
  "nombre": "Juan García",
  "email": "juan@example.com",
  "telefono": "31234567890",
  "rol": "OPERADOR"
}
```

### Ejemplo Response

```json
{
  "id": "1f5644fb-7ae1-4f87-8b07-8a2036e28cd1",
  "nombre": "Juan García",
  "email": "juan@example.com",
  "telefono": "31234567890",
  "rol": "OPERADOR",
  "estado": "PENDIENTE",
  "fechaRegistro": "2024-01-15T10:30:00Z"
}
```

---

## 📧 Servicio de Email

**Ubicación:** `internal/infrastructure/services/EmailService.java`

**Características actuales:**
- 📝 Registra invitaciones en logs
- 🔗 Incluye links de descarga para iOS y Android
- 📋 Información personalizada del empleado

**Cuerpo del email:**
```
Hola [nombre],

¡Bienvenido a Vacapp! Tu cuenta ha sido creada con éxito.

Tu rol: [rol]

Para comenzar a usar la aplicación, descarga Vacapp desde:
📱 iOS: https://apps.apple.com/app/vacapp
📱 Android: https://play.google.com/store/apps/details?id=com.vacapp

Una vez descargues la app, inicia sesión con:
Email: [email]

Si tienes dudas, contacta al administrador.
```

**Para produccíón:** Se puede integrar fácilmente con:
- SMTP (javax.mail)
- SendGrid
- AWS SES
- Otros proveedores

---

## 🔒 Seguridad y Multi-tenancy

- ✅ Todos los endpoints autenticados con JWT
- ✅ Filtrado automático por `TenantContext.obtenerTenant()`
- ✅ Cada tenant ve solo sus propios empleados
- ✅ Validación de roles en backend

---

## 🔗 Integración con Vista Web

La pestaña de empleados ya está disponible en el sidebar:

```html
<a href="/empleados/inventario" class="sidebar-link">
  <svg><!-- ícono --></svg>
  <span>Empleados</span>
</a>
```

---

## ✅ Testing

Para probar el módulo:

### 1. Registrar empleado (API REST)
```bash
curl -X POST http://localhost:8080/api/v1/empleados \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "nombre": "María López",
    "email": "maria@example.com",
    "telefono": "31999999999",
    "rol": "ADMIN"
  }'
```

### 2. Listar empleados
```bash
curl -X GET http://localhost:8080/api/v1/empleados \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

### 3. Enviar invitación
```bash
curl -X POST http://localhost:8080/api/v1/empleados/{id}/enviar-invitacion \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

### 4. Ver en Web
Navega a: `http://localhost:8080/empleados/inventario`

---

## 🚀 Próximos Pasos (Opcional)

1. **Email SMTP Real:**
   - Agregar dependencia `spring-boot-starter-mail`
   - Configurar SMTP en `application.properties`
   - Implementar envío real en `EmailService`

2. **Autenticación de Empleados:**
   - Endpoint para que empleados acepten invitación
   - Crear contraseña por primera vez
   - Sincronización con tabla `usuarios`

3. **Permisos y Roles:**
   - Implementar `@PreAuthorize` por rol
   - Auditoría de acciones

4. **Notificaciones:**
   - Agregar SMS o notificaciones push
   - Recordatorios de descarga de app

---

## 📝 Notas Finales

- El módulo sigue todos los patrones de Clean Architecture del proyecto
- Usa Spring Data JDBC (no JPA) según arquitectura del proyecto
- Multi-tenant desde el inicio
- OpenAPI Design-First (YAML-First) integrado
- Compilación exitosa ✅

