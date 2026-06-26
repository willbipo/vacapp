---
mode: agent
description: >
  Scaffolding SDD completo de un módulo nuevo siguiendo Clean Architecture.
  Usa cuando quieras crear un módulo entero (domain + application + presentation + infrastructure).
  Ejemplos: crear módulo ganado, crear módulo salud, crear módulo reproductivo, nuevo módulo.
---

# Crear Módulo — Scaffolding SDD Completo

Voy a generar el scaffolding completo del módulo **`${input:nombreModulo:Nombre del módulo en singular (ej: Animal, Vacuna, Potrero)}`** para Vacapp, siguiendo Clean Architecture estrictamente.

El paquete base del módulo será `com.vacapp.${input:paqueteModulo:Nombre del paquete en minúsculas (ej: ganado, salud, potrero)}`.

---

## Especificación del Módulo

Describe brevemente las **entidades y atributos principales** del módulo:

`${input:especificacion:Describe la entidad principal: atributos, reglas de negocio clave y casos de uso iniciales (ej: "Animal con campos: nombre, especie, fechaNacimiento, pesoKg. Casos de uso: registrar animal, listar por tenant, obtener por ID")}`

---

## Archivos a Generar

Genera **todos** los siguientes archivos en orden, respetando la estructura de paquetes de `.github/instructions/arquitecture.instructions.md`:

### 1. Domain

- `domain/models/${input:nombreModulo}.java`
  - Clase POJO pura sin anotaciones JPA ni Spring.
  - Atributos inferidos de la especificación + `Long id` + `Long tenantId`.

- `domain/exceptions/${input:nombreModulo}NoEncontradoException.java`
  - Extiende `RuntimeException`.

### 2. Application

- `application/ports/${input:nombreModulo}Repository.java`
  - Interfaz con métodos: `guardar(${input:nombreModulo})`, `buscarPorId(Long id, Long tenantId)`, `listarPorTenant(Long tenantId)`.

- `application/usecases/Registrar${input:nombreModulo}UseCase.java`
  - Recibe un DTO de comando, valida reglas de negocio, llama al repositorio.

- `application/usecases/Listar${input:nombreModulo}UseCase.java`
  - Lista las entidades filtradas por `tenantId`.

- `application/usecases/Obtener${input:nombreModulo}UseCase.java`
  - Busca por ID y lanza `${input:nombreModulo}NoEncontradoException` si no existe.

### 3. Presentation

- `presentation/dtos/Registrar${input:nombreModulo}Request.java`
  - Record con anotaciones `@NotNull`, `@NotBlank`, etc. según atributos.

- `presentation/dtos/${input:nombreModulo}Response.java`
  - Record con todos los atributos del dominio.

- `presentation/controllers/${input:nombreModulo}Controller.java`
  - `@RestController @RequestMapping("/api/v1/${input:paqueteModulo}s")`
  - `POST /` → `201 Created` con `${input:nombreModulo}Response`
  - `GET /` → `200 OK` con `List<${input:nombreModulo}Response>`
  - `GET /{id}` → `200 OK` con `${input:nombreModulo}Response`

### 4. Infrastructure

- `infrastructure/persistence/${input:nombreModulo}Entidad.java`
  - `@Entity @Table(name = "${input:paqueteModulo}s")`
  - Mismos atributos que el dominio + `@Column(name = "tenant_id", nullable = false)`.

- `infrastructure/persistence/${input:nombreModulo}JpaRepository.java`
  - Extiende `JpaRepository<${input:nombreModulo}Entidad, Long>`.
  - Métodos con `findByIdAndTenantId` y `findAllByTenantId`.

- `infrastructure/persistence/${input:nombreModulo}RepositoryImpl.java`
  - `@Repository` que implementa `${input:nombreModulo}Repository` (puerto de Application).
  - Usa `${input:nombreModulo}Mapper` para convertir entre entidad JPA y dominio.

- `infrastructure/mappers/${input:nombreModulo}Mapper.java`
  - `@Component` con métodos `aDominio(${input:nombreModulo}Entidad)` y `aEntidad(${input:nombreModulo})`.

---

## Reglas de Generación

- No uses `@Autowired` en campos; inyecta por **constructor** (compatible con Lombok `@RequiredArgsConstructor`).
- El `tenantId` siempre viene del contexto de seguridad (`SecurityContextHolder`), no del request body.
- Todos los `@Service` y `@Repository` tienen `@Transactional` donde corresponda.
- Los comentarios Javadoc en español.

## Documentación SDD Obligatoria

Además del código, crea también la documentación del módulo en:

- `.github/sdd/modules/${input:paqueteModulo}/01-contexto.md`
- `.github/sdd/modules/${input:paqueteModulo}/02-modelo-dominio.md`
- `.github/sdd/modules/${input:paqueteModulo}/03-casos-uso.md`
- `.github/sdd/modules/${input:paqueteModulo}/04-contrato-api.md`
- `.github/sdd/modules/${input:paqueteModulo}/05-infra-y-datos.md`
- `.github/sdd/modules/${input:paqueteModulo}/06-frontend.md`
- `.github/sdd/modules/${input:paqueteModulo}/07-checklist.md`

Usa como base la plantilla en `.github/sdd/modules/_template/` y marca checklist según estado real.
