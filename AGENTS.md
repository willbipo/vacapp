# AGENTS.md — Vacapp

> Instrucciones siempre activas para cualquier agente de IA (GitHub Copilot, Claude Code, Codex CLI).
> Complementa este archivo con la referencia de arquitectura: `.github/instructions/arquitecture.instructions.md`

---

## Identidad del Proyecto

- **Nombre**: Vacapp
- **Dominio**: Gestión ganadera SaaS
- **Tipo**: Monolito Modular — Spring Modulith + Clean Architecture
- **Stack**: Java 21 + Spring Boot 4.1.0 + Spring Modulith + MySQL + Spring Security (JWT) + Lombok + Thymeleaf + Tailwind CSS

---

## Reglas Generales

1. **Idioma — Backend**: Nombres de clases, interfaces, métodos, variables, campos, paquetes y archivos Java en **inglés**. Comentarios en español. Las palabras clave de Java/Spring (`class`, `public`, `@Service`, etc.) permanecen en inglés por defecto.
2. **Idioma — Frontend**: Carpetas, archivos HTML/CSS, variables JavaScript y comentarios en **español**. No aplica la regla de inglés.
3. **Arquitectura obligatoria**: Todo módulo nuevo sigue la estructura Spring Modulith. Cada módulo expone una única **API pública** (`ModuleService.java`) en su raíz y oculta todo el resto bajo `internal/`.
4. **Encapsulamiento `internal/`**: Ningún otro módulo puede importar clases de `internal/`. Solo se puede usar la API pública del módulo.
5. **Sin mezclar capas**: Nunca usar `@Entity` JPA en `internal/domain/model/`. Siempre usar `Mapper` en `infrastructure/persistence/` para transformar entre capas.
6. **Sin `@Autowired` en campos**: Toda inyección de dependencias por constructor (Lombok `@RequiredArgsConstructor` + campos `final`).
7. **DTOs como Records**: Todos los DTOs de Request/Response deben ser Java Records.
8. **Multi-tenancy**: Todo repositorio JPA debe filtrar por `tenant_id` extraído del contexto de seguridad.
9. **Respuestas HTTP**: Los controladores retornan `ResponseEntity<T>` con código HTTP semánticamente correcto.
10. **Validación**: Anotaciones Bean Validation (`@NotNull`, `@Size`, etc.) solo en DTOs de Request (en `infrastructure/controllers/*/dtos/`), nunca en entidades de dominio.
11. **No sobre-ingeniería**: Solo implementar lo que se solicita explícitamente. No añadir features no pedidas.

---

## Estructura de un Módulo (Spring Modulith)

```
com.vacapp/
│
├── [Module]/                          ← Raíz del módulo (ej. users, cattle, health)
│   ├── [Module]Service.java           ← API PÚBLICA: único punto de entrada para otros módulos
│   │
│   └── internal/                      ← PRIVADO: inaccesible para otros módulos
│       ├── domain/
│       │   ├── model/                 ← Entidades de negocio puras (sin JPA, sin Spring)
│       │   └── repository/            ← Puertos de salida (interfaces)
│       │
│       ├── application/
│       │   └── usecases/              ← Casos de uso (orquestación, sin DTOs de infraestructura)
│       │
│       └── infrastructure/
│           ├── controllers/
│           │   ├── web/               ← Controladores MVC (HTML/Thymeleaf)
│           │   │   └── dtos/          ← Form DTOs (Records)
│           │   └── mobile/            ← Controladores REST API (JSON/JWT)
│           │       └── dtos/          ← Request/Response Records
│           ├── persistence/           ← @Entity JPA, JpaRepository, Impl, Mapper
│           └── config/                ← Beans de configuración del módulo
```

## Flujo de una Petición

```
HTTP Request
  → Controller (internal/infrastructure/controllers/mobile/ o web/)   ← valida DTO con @Valid, mapea a comando
  → UseCase    (internal/application/usecases/)                       ← orquesta lógica de negocio
  → Repository Port (internal/domain/repository/)                     ← interfaz pura
  → JPA Impl   (internal/infrastructure/persistence/)                 ← filtra por tenant_id
  → Base de datos MySQL
```

---

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
- **JavaScript integrado**: Todo el código JS de una vista debe estar dentro del mismo archivo HTML (`<script>`). No crear carpetas `js/` ni archivos `.js` independientes por vista.

---

## Convenciones de Nomenclatura (Backend — en inglés)

| Artefacto | Convención | Ejemplo |
|---|---|---|
| Módulo raíz | `ModuleService.java` (API pública) | `UsersService.java` |
| Entidad de dominio | `ModelName.java` | `Animal.java` |
| Excepción de dominio | `NameException.java` | `InvalidCredentialsException.java` |
| Puerto (interfaz) | `NameRepository.java` | `AnimalRepository.java` |
| Entidad JPA | `NameEntity.java` | `AnimalEntity.java` |
| Repositorio JPA | `NameJpaRepository.java` | `AnimalJpaRepository.java` |
| Caso de uso | `VerbNameUseCase.java` | `RegisterAnimalUseCase.java` |
| Comando (record) | `NameCommand.java` | `RegisterAnimalCommand.java` |
| Resultado (record) | `NameResult.java` | `AuthResult.java` |
| DTO entrada | `NameRequest.java` | `RegisterAnimalRequest.java` |
| DTO salida | `NameResponse.java` | `AnimalResponse.java` |
| Mapper | `NameMapper.java` | `AnimalMapper.java` |
| Controlador REST | `NameRestController.java` | `AnimalRestController.java` |
| Controlador Web | `NameWebController.java` | `AuthWebController.java` |
| Ruta base API | `/api/v1/plural-name` | `/api/v1/animals` |

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
4. Siempre revisa y ajusta el código generado antes de hacer commit.

## Frontend (Vistas Web)

- **Motor de plantillas**: Thymeleaf — archivos en `src/main/resources/templates/`.
- **Estilos**: Tailwind CSS vía CDN (en desarrollo); para producción generar el CSS compilado en `src/main/resources/static/css/`.
- **Estructura de vistas**: Cada vista/página estará compuesta de forma acoplada por su archivo HTML y su CSS, siguiendo estas reglas estrictas:
  - **JavaScript integrado**: Todo el código JavaScript de una vista debe estar **dentro del mismo archivo HTML** (utilizando la etiqueta `<script>`). **No** se debe crear una carpeta `js/` ni archivos `.js` independientes por cada vista.
  - **CSS independiente**: El archivo CSS sí puede mantenerse por separado o compilado según las necesidades de producción.
- **Estructura de templates**:
