# Guía de Migración: Monolito Modular con Spring Modulith y Arquitectura Limpia

Este documento sirve como contexto y regla de negocio estricta para que el Agente de IA realice la migración del código existente hacia una estructura de Monolito Modular basada en **Spring Modulith**, **Arquitectura Limpia (Clean Architecture)** y el patrón de **Doble DTO / Mapeo Estricto**.

---

## 1. Principios Arquitectónicos Estrictos (Reglas para el Agente)

1. **Encapsulamiento de Módulos:** Cada módulo principal debe vivir de forma independiente en la raíz del paquete de la aplicación (ej. `com.tuempresa.myapp.modulo`).
2. **Uso de `internal`:** Todo el código del módulo debe ser privado y oculto para otros módulos, por lo que debe vivir dentro del subpaquete `internal/`.
3. **API Pública del Módulo:** El único componente accesible por otros módulos es una interfaz o servicio ubicado exactamente en la raíz del paquete del módulo (fuera de `internal/`).
4. **Regla de Dependencia:** Las capas internas (`domain`) jamás deben importar clases de las capas externas (`application`, `infrastructure`, o frameworks como Jakarta/Spring).
5. **Inmutabilidad y Records:** Todos los DTOs, Eventos y Entidades de dominio que actúen como agregados inmutables deben implementarse utilizando **Java Records**.
6. **Inyección por Constructor:** Queda estrictamente prohibido el uso de `@Autowired` en campos. Toda inyección de dependencias debe ser por constructor implícito (aprovechando `final`).

---

## 2. Mapa de la Estructura de Carpetas Objetivo

El agente debe mover y refactorizar los archivos existentes para encajar exactamente en este árbol de directorios:

```text
src/main/java/com/tuempresa/myapp/
│
├── [Modulo]/                      <-- Raíz del Módulo (ej. pedidos, usuarios, inventario)
│   ├── [Modulo]Service.java       <-- API PÚBLICA: Único punto de entrada síncrono para otros módulos.
│   │
│   └── internal/                  <-- ENCAPSULAMIENTO: Oculto para el resto del monolito.
│       │
│       ├── domain/                <-- CAPA DE DOMINIO (Reglas de negocio puras, sin frameworks)
│       │   ├── model/             <-- Entidades o Records de Dominio puro.
│       │   └── repository/        <-- Puertos de salida (Interfaces de Repositorios).
│       │
│       ├── application/           <-- CAPA DE APLICACIÓN (Casos de Uso)
│       │   └── usecases/          <-- Clases de orquestación (ej. CrearPedidoUseCase).
│       │
│       └── infrastructure/        <-- CAPA DE INFRAESTRUCTURA (Detalles técnicos y frameworks)
│           ├── controllers/       <-- Adaptadores de Entrada (Múltiples endpoints)
│           │   ├── web/           <-- Controladores MVC (HTML / Thymeleaf / Cookies)
│           │   │   └── dtos/      <-- Form DTOs específicos de la Web.
│           │   └── mobile/        <-- Controladores REST API (JSON / JWT)
│           │       └── dtos/      <-- Request/Response Records específicos de la App móvil.
│           │
│           ├── persistence/       <-- Adaptador de Salida (Base de datos)
│           │   ├── [Modulo]Record.java <-- Mapeo de tabla (Spring Data JDBC Record)
│           │   └── SpringData[Modulo]Repository.java <-- Interfaz que extiende de ListCrudRepository
│           │
│           └── config/            <-- Configuraciones específicas del módulo.