# Vacapp — Arquitectura del Proyecto

> Plataforma SaaS de gestión ganadera (Monolito Modular con Clean Architecture)

---

## 1. Stack Tecnológico

| Capa | Tecnología |
|---|---|
| **Backend** | Java 21 + Spring Boot 4.1.0 |
| **Persistencia** | Spring Data JDBC + CrudRepository |
| **Base de datos** | MySQL |
| **Seguridad** | Spring Security + JWT |
| **Validación** | Spring Validation (Bean Validation) |
| **Utilidades** | Lombok |
| **Frontend Web** | Thymeleaf + HTML + CSS vanilla + JavaScript vanilla (no React, no Tailwind) |
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

## 3. Principios Arquitectónicos Fundamentales

1. **Encapsulamiento de módulos**: Cada módulo vive de forma independiente en la raíz del paquete de la aplicación (ej. `com.vacapp.users`, `com.vacapp.cattle`).

2. **Estructura `internal/`**: Todo el código del módulo es privado bajo el subpaquete `internal/`. Solo la API pública en la raíz es accesible.

3. **API pública**: Cada módulo expone un único `ModuleService.java` en su raíz como punto de entrada para otros módulos.

4. **Regla de dependencia**: Las capas internas (`domain`) jamás importan clases de capas externas (`application`, `infrastructure`) ni frameworks (Jakarta/Spring).

5. **Records obligatorios**: DTOs, Eventos y Comandos se implementan exclusivamente con **Java Records**.

6. **Constructor injection**: Prohibido `@Autowired` en campos. Toda inyección por constructor con Lombok `@RequiredArgsConstructor` + campos `final`.

7. **Documentación OpenAPI obligatoria (API móvil)**: Todo endpoint REST en `internal/infrastructure/controllers/mobile/` debe incluir anotaciones Swagger (`@Tag`, `@Operation`, `@ApiResponses`) y los DTOs Request/Response deben incluir `@Schema` para describir campos y ejemplos.

---

## 4. Estructura de un Módulo Spring Modulith

```
src/main/java/com/vacapp/
│
├── [Module]/                           ← Raíz del módulo (ej. users, cattle, health)
│   ├── [Module]Service.java            ← ✓ API PÚBLICA: único punto de entrada
│   │
│   └── internal/                       ← PRIVADO: inaccesible para otros módulos
│       │
│       ├── domain/
│       │   ├── model/                  ← Entidades puras (sin JPA, sin Spring)
│       │   │   ├── Entity.java         ← Modelo de dominio (immutable)
│       │   │   ├── Enum.java           ← Enums de dominio
│       │   │   └── Exception.java      ← Excepciones de negocio
│       │   │
│       │   └── repository/             ← Puertos (interfaces)
│       │       ├── EntityRepository.java
│       │       └── TokenGenerator.java
│       │
│       ├── application/
│       │   └── usecases/               ← Casos de uso (orquestación)
│       │       ├── CreateEntityUseCase.java
│       │       └── AuthenticateUseCase.java
│       │
│       └── infrastructure/
│           ├── controllers/
│           │   ├── web/                ← MVC Controllers (Thymeleaf)
│           │   │   └── dtos/
│           │   │       ├── FormRequest.java
│           │   │       └── FormResponse.java
│           │   │
│           │   └── mobile/             ← REST API Controllers (JSON/JWT)
│           │       ├── EntityRestController.java
│           │       └── dtos/
│           │           ├── EntityRequest.java
│           │           └── EntityResponse.java
│           │
           ├── persistence/            ← Spring Data JDBC Adapters
           │   ├── EntityEntity.java   ← Clase de mapeo (sin @Entity)
           │   ├── EntityRepository.java ← CrudRepository<Entity, ID> con @Query
           │   ├── EntityMapper.java   ← Mapeo Entidad ↔ Dominio
│           │   └── PasswordEncoder.java
│           │
│           └── config/                 ← Configuración del módulo
│               └── ModuleConfig.java
```

---

## 5. Patrón de Refactorización: Double DTO + Mapping

### 5.1 Controlador REST (Capa de Entrada)

El controlador recibe un DTO Request con validación, lo mapea a un Comando de dominio, y ejecuta el caso de uso.

```java
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductRestController {

    private final RegisterProductUseCase registerProductUseCase;

    @PostMapping
    public ResponseEntity<Void> register(@Valid @RequestBody RegisterProductRequest request) {
        // 1. Mapping: Request DTO → Command (dominio)
        RegisterProductCommand command = new RegisterProductCommand(
            request.code(),
            request.name(),
            request.price()
        );

        // 2. Ejecución del caso de uso
        registerProductUseCase.execute(command);
        
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
```

### 5.2 UseCase (Orquestación)

El caso de uso define Comandos/Resultados como Records y no importa DTOs de presentación.

```java
@Service
@RequiredArgsConstructor
public class RegisterProductUseCase {

    private final ProductRepository productRepository;

    // Record interno del caso de uso
    public record RegisterProductCommand(String code, String name, BigDecimal price) {}
    public record ProductResult(UUID id, String code, String name) {}

    public ProductResult execute(RegisterProductCommand command) {
        Product product = Product.builder()
            .code(command.code())
            .name(command.name())
            .price(command.price())
            .build();

        Product saved = productRepository.save(product);
        return new ProductResult(saved.getId(), saved.getCode(), saved.getName());
    }
}
```

### 5.3 Entity de Dominio (Puro)

Sin JPA, sin Spring, solo lógica de negocio.

```java
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private UUID id;
    private String code;
    private String name;
    private BigDecimal price;
    private String tenantId;
}
```

### 5.4 Entity Spring Data JDBC (Persistencia)

Clase de mapeo simple (sin anotaciones JPA) que Spring Data JDBC mapea automáticamente desde la base de datos.

```java
@Builder
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProductEntity {
    // Sin @Entity, sin @Table, sin @Column
    // Solo POJO con campos que coinciden con columnas de BD
    private UUID id;
    private String code;
    private String name;
    private BigDecimal price;
    private String tenantId;
}
```

Repositorio con Spring Data JDBC y `@Query` para queries personalizadas:

```java
public interface ProductRepository extends CrudRepository<ProductEntity, UUID> {
    
    @Query("SELECT * FROM products WHERE code = :code")
    Optional<ProductEntity> findByCode(@Param("code") String code);

    @Query("SELECT * FROM products WHERE tenant_id = :tenantId")
    List<ProductEntity> findByTenantId(@Param("tenantId") String tenantId);
}
```

---

## 6. Comunicación entre Módulos

### 6.1 Comunicación Síncrona (API Pública)

Si **Módulo A** necesita datos de **Módulo B**, solo llama a su `ModuleService` público:

```java
// En Módulo A
@RequiredArgsConstructor
public class ModuleAUseCase {
    private final ModuleBService moduleBService;  // ✓ Inyección de API pública

    public void execute() {
        Optional<BEntity> entity = moduleBService.findById(id);
        // Usar los datos...
    }
}
```

**Prohibido**: Inyectar `BInternalRepository`, `BInternalUseCase`, o cualquier clase bajo `internal/`.

### 6.2 Comunicación Asíncrona (Eventos)

Para desacoplar módulos, usar eventos con Spring Modulith y Transactional Outbox:

```java
// Evento publicado por Módulo A
public record ProductRegisteredEvent(UUID productId, String code) {}

// Listener en Módulo B
@Component
public class ProductListener {
    
    @ApplicationModuleListener
    public void onProductRegistered(ProductRegisteredEvent event) {
        // Procesamiento asíncrono y resiliente
        // El Outbox de Modulith garantiza entrega
    }
}
```

---

## 7. Frontend Thymeleaf + HTML + CSS + JavaScript Vanilla

El frontend es un conjunto de vistas **server-rendered** con Thymeleaf, sin framework JavaScript (React, Vue, Angular). Solo HTML semántico, CSS vanilla y JavaScript vanilla para interactividad.

### Estructura de Vistas

```
src/main/resources/
├── templates/                  ← Vistas Thymeleaf (.html)
│   ├── auth/
│   │   ├── login.html          ← Formulario de login (username, password)
│   │   └── logout.html         ← Confirmación de cierre de sesión
│   ├── dashboard/
│   │   └── index.html          ← Dashboard principal (acceso después de auth)
│   └── fragments/              ← Componentes reutilizables (header, sidebar, footer)
│       ├── navbard.html        ← Navegación principal
│       └── sidebard.html       ← Barra lateral
│
└── static/                     ← Assets estáticos (CSS, imágenes, favicon)
    ├── css/                    ← Estilos CSS custom (no Tailwind)
    │   ├── global.css          ← Estilos globales (variables CSS, reset)
    │   ├── login.css           ← Estilos específicos de login
    │   ├── dashboard.css       ← Estilos del dashboard
    │   ├── navbard.css         ← Estilos de navegación
    │   └── sidebard.css        ← Estilos de barra lateral
    ├── images/                 ← Imágenes y logos
    └── favicon.ico             ← Ícono del sitio
```

### Características del Frontend

- **Thymeleaf**: `xmlns:th="http://www.thymeleaf.org"` para rendering server-side
- **JavaScript integrado**: Todo código JS dentro del mismo archivo HTML (`<script>`), sin carpeta `js/` separada
- **CSS vanilla**: Variables CSS custom, flexbox, grid; sin preprocesadores ni Tailwind
- **Token JWT**: Almacenado en `sessionStorage` tras login
- **Fetch API**: Comunicación con backend (`/api/v1/auth/login`, etc.)
- **Animaciones**: CSS transitions y JavaScript para UX mejorada
- **Responsive**: Media queries CSS para móvil, tablet, desktop

### Flujo de una Petición de Vista

```
1. Usuario navega a /login
2. Spring redirige a LoginWebController
3. Controlador retorna vista Thymeleaf renderizada (login.html)
4. HTML se descarga con CSS integrados
5. JavaScript vanilla intercepta form submit
6. Fetch POST a /api/v1/auth/login (JSON)
7. Si éxito, guarda token en sessionStorage y redirige a /dashboard
8. Dashboard carga con context del usuario autenticado
```

### Reglas de Desarrollo Frontend

- **Estructura de archivos**: Un archivo `.html` por vista en `templates/`
- **CSS**: Un archivo `.css` por vista (o compartido) en `static/css/`
- **JavaScript**: Integrado en el HTML con `<script>` al final de `</body>`
- **Nombres**: Todos en **español** (carpetas, archivos, variables, funciones, comments)
- **No React**: Mantener simplemente HTML + CSS + JS vanilla
- **No Tailwind**: Usar CSS custom o framework mínimo si es necesario
- **Thymeleaf helpers**: `th:href`, `th:action`, `th:text`, etc. para URLs dinámicas

---

## 8. Estructura de Paquetes del Backend (Spring Modulith)

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
│   ├── UsuariosService.java            ← API PÚBLICA del módulo
│   └── internal/
│       ├── domain/
│       │   ├── model/                  ← Usuario.java (entidad de negocio, sin JPA)
│       │   │   ├── Usuario.java
│       │   │   └── Rol.java
│       │   ├── repository/             ← Puertos (interfaces)
│       │   │   ├── UsuarioRepository.java
│       │   │   ├── GeneradorDeToken.java
│       │   │   └── VerificadorDeContrasena.java
│       │   └── exceptions/             ← Excepciones de dominio
│       │       ├── CredencialesInvalidasException.java
│       │       └── UsuarioNoEncontradoException.java
│       ├── application/
│       │   └── usecases/
│       │       ├── AutenticarUsuarioUseCase.java
│       │       └── RegistrarUsuarioUseCase.java
│       └── infrastructure/
│           ├── controllers/
│           │   └── mobile/
│           │       ├── AuthRestController.java
│           │       └── dtos/
│           │           ├── LoginRequest.java
│           │           ├── LoginResponse.java
│           │           ├── RegistroRequest.java
│           │           └── UsuarioActualResponse.java
│           ├── persistence/
│           │   ├── UsuarioEntity.java
│           │   ├── UsuarioJpaRepository.java
│           │   ├── UsuarioRepositoryImpl.java
│           │   ├── UsuarioMapper.java
│           │   └── BcryptVerificadorDeContrasena.java
│           └── config/
│               └── UsuariosConfig.java
│
└── ganado/ (ej.)                       ← Módulo: gestión del ganado (próximo)
    ├── GanadoService.java              ← API PÚBLICA del módulo
    └── internal/
        └── (misma estructura que usuarios)
```

---

## 9. Convenciones de Nomenclatura (Inglés para Backend)

| Artefacto | Patrón | Ejemplo |
|---|---|---|
| **Module** | `ModuleService.java` | `UsersService.java` |
| **Model** | `EntityName.java` | `Product.java` |
| **Exception** | `NameException.java` | `ProductNotFoundException.java` |
| **Port/Interface** | `NameRepository.java` | `ProductRepository.java` |
| **JPA Entity** | `NameEntity.java` | `ProductEntity.java` |
| **JPA Repository** | `NameJpaRepository.java` | `ProductJpaRepository.java` |
| **Impl** | `NameRepositoryImpl.java` | `ProductRepositoryImpl.java` |
| **Mapper** | `NameMapper.java` | `ProductMapper.java` |
| **UseCase** | `VerbNameUseCase.java` | `RegisterProductUseCase.java` |
| **Command** | `NameCommand.java` | `RegisterProductCommand.java` |
| **Result** | `NameResult.java` | `AuthenticationResult.java` |
| **Request DTO** | `NameRequest.java` | `RegisterProductRequest.java` |
| **Response DTO** | `NameResponse.java` | `ProductResponse.java` |
| **REST Controller** | `NameRestController.java` | `ProductRestController.java` |
| **Web Controller** | `NameWebController.java` | `AuthWebController.java` |
| **API Route** | `/api/v1/plural-name` | `/api/v1/products` |
| **Feature React (ES)** | `web/src/features/nombre/` | `web/src/features/ganado/` |

---

## 10. Reglas de Desarrollo

1. **Separación de entidades**: Nunca usar `@Entity` en `domain/model/`. Usar `Mapper` para convertir entre la entidad JPA (`infrastructure/persistence/`) y el modelo de dominio.

2. **Flujo de petición**: `HTTP Request → Controller (infrastructure/controllers/) → UseCase (application/usecases/) → Domain Model → Repository Port → JPA Impl (infrastructure/persistence/)`.

3. **Respuestas HTTP**: Los controladores siempre retornan `ResponseEntity<T>` con el código HTTP apropiado (`201 Created`, `200 OK`, `404 Not Found`, etc.).

4. **Multi-tenancy**: Todo método de repositorio JPA debe filtrar por `tenant_id` (obtenido del contexto de seguridad) para garantizar el aislamiento de datos entre clientes SaaS.

5. **Idioma Backend**: Todos los nombres de clase, interface, método, variable, paquete y archivo Java en **inglés**. Comentarios en español. Palabras clave de Java/Spring en inglés por defecto.

6. **Idioma Frontend**: Carpetas, archivos, variables JavaScript/TypeScript y comentarios en **español**.

7. **Validación**: Usar anotaciones de Bean Validation (`@NotNull`, `@Size`, etc.) **solo en DTOs de Request**, nunca en las entidades de dominio (`domain/model/`).

8. **Constructor injection**: Prohibido `@Autowired` en campos. Toda inyección por constructor con Lombok `@RequiredArgsConstructor` + campos `final`.

9. **DTOs como Records**: Todos los DTOs de Request/Response y Comandos/Resultados deben ser **Java Records** (immutable, conciso, idóneo).

10. **Frontend**: Las llamadas a la API desde React deben pasar siempre por `web/src/services/` con el interceptor Axios que adjunta el JWT automáticamente.

11. **No mezclar capas**: No importar clases de `infrastructure/` desde `application/` o `domain/`. Solo `infrastructure/` puede importar las capas internas.

---

## 11. Checklist de Implementación (Nuevos Módulos)

Para crear un nuevo módulo respetando Spring Modulith + Clean Architecture:

- [ ] Crear estructura de carpetas bajo `com.vacapp.[ModuleName]/internal/` con todas las subcarpetas
- [ ] Crear modelo de dominio en `domain/model/` (sin JPA, sin Spring)
- [ ] Crear puertos (interfaces) en `domain/repository/`
- [ ] Crear excepciones de dominio en `domain/model/` o `domain/exceptions/`
- [ ] Implementar casos de uso en `application/usecases/` con Comandos/Resultados como Records
- [ ] Crear entidades JPA en `infrastructure/persistence/`
- [ ] Implementar `NameJpaRepository` extendiendo `JpaRepository<T, ID>`
- [ ] Crear `NameRepositoryImpl` implementando puerto con mapeos y filtro por `tenant_id`
- [ ] Crear `NameMapper` para transformaciones Domain ↔ JPA
- [ ] Crear controladores REST en `infrastructure/controllers/mobile/` con DTOs de Request/Response
- [ ] Crear controladores Web en `infrastructure/controllers/web/` si se necesita Thymeleaf
- [ ] Exponer API pública en `ModuleNameService.java` en la raíz del módulo
- [ ] Validar con `./mvnw compile` que el código compila sin errores
- [ ] Crear tests unitarios y de integración
- [ ] Actualizar esta guía si se descubren nuevos patrones

---

## 12. Recursos Adicionales

- [Spring Modulith Oficial](https://spring.io/projects/spring-modulith)
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Hexagonal Architecture](https://alistair.cockburn.us/hexagonal-architecture/)