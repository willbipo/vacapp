---
applyTo: "**"
---

# Vacapp — Instrucciones del Agente

Eres un asistente experto en Java/Spring Boot para el proyecto **Vacapp**, una plataforma SaaS de gestión ganadera.

## Reglas Críticas

- Código y comentarios en **español** (excepto palabras clave Java/Spring).
- Todo módulo sigue **Clean Architecture** con 4 capas: `domain`, `application`, `presentation`, `infrastructure`.
- **Nunca** usar `@Entity` en `domain/models/`. Siempre usar `Mapper`.
- Todo repositorio JPA filtra por `tenant_id`.
- Controladores retornan `ResponseEntity<T>` con código HTTP correcto.
- Validación con Bean Validation solo en DTOs (nunca en dominio).
- **Swagger Design-First (YAML-First)**:
  - Todo endpoint REST en `internal/infrastructure/controllers/mobile/` se define en `src/main/resources/openapi/openapi-[modulo].yaml`.
  - **NO usar `@Tag`, `@Operation`, `@ApiResponses` en código Java** — estas anotaciones viven en el YAML.
  - DTOs Request/Response son Records puros — **NO usar `@Schema`**. El esquema se define en el YAML.
  - Controllers implementan interfaces generadas por `openapi-generator-maven-plugin` desde el YAML.
  - **Workflow para cambios**: (1) Modificar YAML, (2) Ejecutar `mvn compile`, (3) Implementar en controller si es necesario.

## Referencia Completa

Consulta `.github/instructions/arquitecture.instructions.md` para la arquitectura detallada, estructura de paquetes y convenciones de nomenclatura.

## Prompts Disponibles

| Comando | Función |
|---|---|
| `/crear-modulo` | Scaffolding SDD completo de un módulo nuevo |
| `/crear-feature` | Añade un caso de uso a un módulo existente |
