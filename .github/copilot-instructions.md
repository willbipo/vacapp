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
- Todo endpoint REST nuevo en `internal/infrastructure/controllers/mobile/` debe incluir documentación OpenAPI/Swagger con `@Tag`, `@Operation` y `@ApiResponses`; además, sus DTOs Request/Response deben usar `@Schema` en campos relevantes.

## Referencia Completa

Consulta `.github/instructions/arquitecture.instructions.md` para la arquitectura detallada, estructura de paquetes y convenciones de nomenclatura.

## Prompts Disponibles

| Comando | Función |
|---|---|
| `/crear-modulo` | Scaffolding SDD completo de un módulo nuevo |
| `/crear-feature` | Añade un caso de uso a un módulo existente |
