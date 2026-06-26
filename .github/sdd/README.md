# SDD de Vacapp

Esta carpeta centraliza la documentación SDD (Spec-Driven Development) por módulo.

## Objetivo

- Mantener especificaciones funcionales y técnicas por módulo.
- Trazar cada feature desde la idea hasta su implementación.
- Facilitar mantenimiento, onboarding y evolución del scaffold.

## Estructura

- `modules/_template/`: plantilla base para nuevos módulos.
- `modules/<modulo>/`: documentación viva de cada módulo.

## Flujo recomendado

1. Copiar `modules/_template/` a `modules/<modulo>/`.
2. Completar contexto, modelo, casos de uso y contrato API.
3. Generar código con los prompts SDD.
4. Actualizar checklist y estado de implementación.
