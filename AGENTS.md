# AGENTS.md — Vacapp

> Instrucciones siempre activas para cualquier agente de IA (GitHub Copilot, Claude Code, Codex CLI).
> Complementa este archivo con la referencia de arquitectura: `.github/instructions/arquitecture.instructions.md`

---

## Identidad del Proyecto

- **Nombre**: Vacapp
- **Dominio**: Gestión ganadera SaaS
- **Tipo**: Monolito modular con Clean Architecture
- **Stack**: Java 21 + Spring Boot 4.1.0 + MySQL + Spring Security (JWT) + Lombok + Thymeleaf + Tailwind CSS

---

## Reglas Generales

1. **Idioma del código**: Variables, métodos, clases y comentarios en **español**. Las palabras clave de Java/Spring (`class`, `public`, `@Service`, etc.) permanecen en inglés.
2. **Arquitectura obligatoria**: Todo módulo nuevo debe respetar las 4 capas de Clean Architecture: `domain → application → presentation → infrastructure`. Ver detalles completos en `.github/instructions/arquitecture.instructions.md`.
3. **Sin mezclar capas**: Nunca usar `@Entity` JPA en `domain/models/`. Siempre usar `Mapper` para transformar entre capas.
4. **Multi-tenancy**: Todo repositorio JPA debe filtrar por `tenant_id` extraído del contexto de seguridad.
5. **Respuestas HTTP**: Los controladores retornan `ResponseEntity<T>` con código HTTP semánticamente correcto.
6. **Validación**: Anotaciones Bean Validation (`@NotNull`, `@Size`, etc.) solo en DTOs de Request, nunca en entidades de dominio.
7. **No sobre-ingeniería**: Solo implementar lo que se solicita explícitamente. No añadir features no pedidas.

---

## Flujo de una Petición

```
HTTP Request
  → Controller (presentation/)          ← valida DTO con @Valid
  → UseCase (application/usecases/)     ← orquesta lógica de negocio
  → Repository Port (application/ports/) ← interfaz (sin JPA)
  → JPA Impl (infrastructure/persistence/) ← filtra por tenant_id
  → Base de datos MySQL
```

## Frontend (Vistas Web)

- **Motor de plantillas**: Thymeleaf — archivos en `src/main/resources/templates/`.
- **Estilos**: Tailwind CSS vía CDN (en desarrollo); para producción generar el CSS compilado en `src/main/resources/static/css/`.
- **Estructura de templates**:
  ```
  templates/
  ├── auth/
  │   └── login.html
  ├── dashboard/
  └── layouts/
      └── base.html   ← plantilla base (cuando se necesite)
  ```
- **Interacción con API**: Las páginas consumen los endpoints `/api/v1/**` mediante `fetch` y almacenan el token JWT en `sessionStorage`.
- **Sin framework JS**: No usar React, Vue ni Angular. Solo HTML, CSS y JavaScript vanilla.

---

## Estructura de Carpetas de este Repositorio de IA

```
.github/
├── copilot-instructions.md          ← Instrucciones siempre activas para VS Code Copilot
├── instructions/
│   └── arquitecture.instructions.md ← Referencia completa de arquitectura y convenciones
├── prompts/
│   ├── crear-modulo.prompt.md       ← /crear-modulo  → Scaffolding SDD de un módulo completo
│   └── crear-feature.prompt.md      ← /crear-feature → Scaffolding SDD de un caso de uso
└── agents/
    └── (agentes personalizados futuros)

AGENTS.md                            ← Este archivo (raíz, leído por todos los agentes)
```

---

## Cómo Usar los Prompts (SDD)

1. En el chat de Copilot, escribe `/` para ver los prompts disponibles.
2. Usa `/crear-modulo` para generar el scaffolding completo de un nuevo módulo (ej. `salud`, `reproductivo`).
3. Usa `/crear-feature` para añadir un caso de uso a un módulo existente.
4. Siempre revisa y ajusta el código generado antes de hacer commit.
