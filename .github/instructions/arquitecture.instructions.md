# Vacapp — Arquitectura del Proyecto

> Plataforma SaaS de gestión ganadera (Monolito Modular con Clean Architecture)

---

## 1. Stack Tecnológico

| Capa | Tecnología |
|---|---|
| **Backend** | Java 21 + Spring Boot 4.1.0 |
| **ORM / Persistencia** | Spring Data JPA + Hibernate |
| **Base de datos** | MySQL |
| **Seguridad** | Spring Security + JWT |
| **Validación** | Spring Validation (Bean Validation) |
| **Utilidades** | Lombok |
| **Frontend Dashboard** | React (TypeScript) — carpeta `web/`, servido como SPA embebida en Spring Boot |
| **Frontend Móvil** | Flutter — consume la API REST |
| **Multi-tenancy** | Columna `tenant_id` en todas las tablas; extraído del JWT |

---

## 2. Arquitectura: Clean Architecture (por módulo)

El proyecto es un **monolito modular**. Cada módulo de negocio (ej. `ganado`, `usuarios`, `salud`) es autónomo y sigue estrictamente cuatro capas. Las dependencias fluyen **siempre de afuera hacia adentro**: `Infrastructure → Presentation → Application → Domain`.

```
┌─────────────────────────────────────────────┐
│            Infrastructure                   │  ← Adaptadores de salida (JPA, APIs externas)
│  ┌──────────────────────────────────────┐   │
│  │          Presentation                │   │  ← Adaptadores de entrada (Controllers, DTOs)
│  │  ┌───────────────────────────────┐   │   │
│  │  │        Application            │   │   │  ← Casos de uso (orquestación)
│  │  │  ┌────────────────────────┐   │   │   │
│  │  │  │       Domain           │   │   │   │  ← Núcleo puro (entidades, excepciones)
│  │  │  └────────────────────────┘   │   │   │
│  │  └───────────────────────────────┘   │   │
│  └──────────────────────────────────────┘   │
└─────────────────────────────────────────────┘
```

### Descripción de capas

| Capa | Paquete | Responsabilidad | Dependencias permitidas |
|---|---|---|---|
| **Domain** | `domain/models/`, `domain/exceptions/` | Entidades de negocio puras y excepciones de dominio | **Ninguna** (sin Spring, sin JPA) |
| **Application** | `application/usecases/`, `application/ports/` | Casos de uso y puertos (interfaces de repositorios) | Solo `domain/` |
| **Presentation** | `presentation/controllers/`, `presentation/dtos/` | Controladores REST y objetos de transferencia (Request/Response) | Solo `application/` |
| **Infrastructure** | `infrastructure/persistence/`, `infrastructure/mappers/` | Implementaciones JPA, entidades `@Entity`, mappers | `application/` (implementa sus puertos) |

---

## 3. Frontend React (web/)

La aplicación React se mantiene en la carpeta `web/` en la raíz del proyecto, desacoplada del backend para un desarrollo ágil.

### Estructura de `web/`

```
web/
├── src/
│   ├── assets/                 ← Recursos estáticos (imágenes, logos)
│   ├── components/             ← Componentes reutilizables (Botones, Inputs, Tablas)
│   ├── features/               ← Módulos del UI organizados por dominio (auth, ganado, salud)
│   │   └── ganado/
│   │       ├── components/     ← Componentes específicos del módulo
│   │       ├── hooks/          ← Hooks del módulo (useAnimales, etc.)
│   │       └── pages/          ← Páginas/vistas del módulo
│   ├── hooks/                  ← Custom Hooks globales (useAuth, useNFC)
│   ├── routes/                 ← Configuración de React Router y rutas protegidas
│   ├── services/               ← Clientes Axios con interceptor JWT automático
│   ├── store/                  ← Estado global (Zustand o Redux Toolkit)
│   └── App.tsx
├── index.html
├── package.json
└── vite.config.ts              ← Proxy /api → http://localhost:8080
```

### Ciclo de desarrollo e integración

| Etapa | Descripción |
|---|---|
| **Desarrollo local** | Backend en `:8080`, frontend Vite en `:5173`. El proxy de Vite redirige `/api/*` al backend de forma transparente. |
| **Build** | `npm run build` genera el bundle estático en `web/dist/`. |
| **Empaquetado** | Maven copia `web/dist/` a `src/main/resources/static/` antes del empaquetado final del JAR. |
| **Enrutamiento SPA** | Un controlador de fallback en Spring redirige todas las rutas no-API (sin prefijo `/api/`) al `index.html` para soportar React Router. |

---

## 4. Estructura de Paquetes del Backend

```
com.vacapp
│
├── VacappApplication.java              ← Punto de entrada Spring Boot
│
├── core/                               ← Configuraciones transversales
│   ├── security/                       ← Filtro JWT, config CORS, UserDetailsService
│   └── multitenancy/                   ← Extrae tenant_id del JWT y lo propaga
│
├── usuarios/                           ← Módulo: gestión de usuarios y autenticación
│   ├── domain/
│   │   ├── models/                     ← Usuario.java (entidad de negocio, sin JPA)
│   │   └── exceptions/                 ← UsuarioNoEncontradoException.java, etc.
│   ├── application/
│   │   ├── usecases/                   ← RegistrarUsuarioUseCase.java, etc.
│   │   └── ports/                      ← UsuarioRepository.java (interfaz)
│   ├── presentation/
│   │   ├── controllers/                ← UsuarioController.java (@RestController)
│   │   └── dtos/                       ← RegistrarUsuarioRequest.java, UsuarioResponse.java
│   └── infrastructure/
│       ├── persistence/                ← UsuarioJpaRepository.java, UsuarioEntidad.java
│       └── mappers/                    ← UsuarioMapper.java
│
└── ganado/                             ← Módulo: gestión del ganado
    ├── domain/
    │   ├── models/                     ← Animal.java (entidad de negocio, sin JPA)
    │   └── exceptions/                 ← AnimalNoEncontradoException.java, etc.
    ├── application/
    │   ├── usecases/                   ← RegistrarAnimalUseCase.java, etc.
    │   └── ports/                      ← AnimalRepository.java (interfaz)
    ├── presentation/
    │   ├── controllers/                ← AnimalController.java (@RestController)
    │   └── dtos/                       ← RegistrarAnimalRequest.java, AnimalResponse.java
    └── infrastructure/
        ├── persistence/                ← AnimalJpaRepository.java, AnimalEntidad.java
        └── mappers/                    ← AnimalMapper.java
```

---

## 5. Convenciones de Nomenclatura

| Artefacto | Convención | Ejemplo |
|---|---|---|
| Entidad de dominio | `NombreModelo.java` | `Animal.java` |
| Entidad JPA | `NombreEntidad.java` | `AnimalEntidad.java` |
| Puerto (interfaz) | `NombreRepository.java` | `AnimalRepository.java` |
| Repositorio JPA | `NombreJpaRepository.java` | `AnimalJpaRepository.java` |
| Caso de uso | `VerboCosaUseCase.java` | `RegistrarAnimalUseCase.java` |
| DTO entrada | `VerboCosaRequest.java` | `RegistrarAnimalRequest.java` |
| DTO salida | `NombreResponse.java` | `AnimalResponse.java` |
| Mapper | `NombreMapper.java` | `AnimalMapper.java` |
| Controlador | `NombreController.java` | `AnimalController.java` |
| Ruta base API | `/api/v1/nombre-plural` | `/api/v1/animales` |
| Feature React | `web/src/features/nombre/` | `web/src/features/ganado/` |

---

## 6. Reglas de Desarrollo

1. **Separación de entidades**: Nunca usar `@Entity` en `domain/models/`. Usar `Mapper` para convertir entre la entidad JPA (`infrastructure`) y el modelo de dominio.
2. **Flujo de petición**: `HTTP Request → Controller (Presentation) → UseCase (Application) → Domain → Repository Port → JPA Impl (Infrastructure)`.
3. **Respuestas HTTP**: Los controladores siempre retornan `ResponseEntity<T>` con el código HTTP apropiado (`201 Created`, `200 OK`, `404 Not Found`, etc.).
4. **Multi-tenancy**: Todo método de repositorio JPA debe filtrar por `tenant_id` (obtenido del contexto de seguridad) para garantizar el aislamiento de datos entre clientes SaaS.
5. **Idioma**: Código, variables y comentarios en **español**, excepto palabras clave de Java/Spring y anotaciones.
6. **Validación**: Usar anotaciones de Bean Validation (`@NotNull`, `@Size`, etc.) en los DTOs de Request, nunca en las entidades de dominio.
7. **Frontend**: Las llamadas a la API desde React deben pasar siempre por `web/src/services/` con el interceptor Axios que adjunta el JWT automáticamente.