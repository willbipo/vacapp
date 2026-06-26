---
mode: agent
description: >
  Añade un caso de uso (feature) a un módulo existente de Vacapp.
  Usa cuando el módulo ya existe y quieres agregar: un nuevo endpoint, una nueva operación de negocio,
  o lógica adicional. Ejemplos: agregar feature, nuevo endpoint, nuevo caso de uso, ampliar módulo.
---

# Crear Feature — Añadir Caso de Uso a Módulo Existente

Voy a añadir la feature **`${input:nombreFeature:Nombre descriptivo de la feature (ej: ActualizarPeso, DarDeBaja, BuscarPorEspecie)}`** al módulo **`${input:modulo:Nombre del módulo existente (ej: ganado, salud, usuarios)}`**.

---

## Especificación de la Feature

`${input:especificacion:Describe qué debe hacer esta feature: entrada (request), lógica de negocio, salida (response) y posibles errores (ej: "Actualizar el peso de un animal. Recibe el ID del animal y el nuevo pesoKg. Lanza excepción si el animal no existe o el peso es negativo.")}`

---

## Archivos a Generar / Modificar

Analiza primero el módulo existente en `src/main/java/com/vacapp/${input:modulo}/` para entender la estructura actual. Luego genera o modifica **solo lo necesario**:

### Application

- **Nuevo** `application/usecases/${input:nombreFeature}UseCase.java`
  - `@Service @RequiredArgsConstructor`
  - Inyecta los puertos necesarios por constructor.
  - Valida reglas de negocio antes de persistir.
  - Obtiene `tenantId` desde `SecurityContextHolder`.

### Presentation

- **Nuevo** `presentation/dtos/${input:nombreFeature}Request.java` *(si la feature recibe datos)*
  - Record con anotaciones Bean Validation.

- **Modificar** `presentation/controllers/${input:modulo|capitalize}Controller.java`
  - Añade el endpoint correspondiente con el verbo HTTP correcto:
    - Crear → `POST`, Leer → `GET`, Actualizar parcial → `PATCH`, Actualizar total → `PUT`, Eliminar → `DELETE`
  - Retorna `ResponseEntity<T>` con código HTTP semánticamente correcto.

### Infrastructure *(si la feature requiere nuevas consultas)*

- **Modificar** `application/ports/${input:modulo|capitalize}Repository.java`
  - Añade el método abstracto necesario.

- **Modificar** `infrastructure/persistence/${input:modulo|capitalize}JpaRepository.java`
  - Añade la query derivada o `@Query` JPQL.

- **Modificar** `infrastructure/persistence/${input:modulo|capitalize}RepositoryImpl.java`
  - Implementa el nuevo método del puerto.

---

## Reglas de Generación

- **No modificar** archivos de `domain/` a menos que la feature introduzca una nueva entidad de dominio.
- El `tenantId` siempre desde `SecurityContextHolder`, nunca del request.
- Inyección por constructor, no `@Autowired` en campos.
- Comentarios Javadoc en español.
- Si la feature puede lanzar una excepción de negocio nueva, crearla en `domain/exceptions/`.
