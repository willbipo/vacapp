# 05. Infraestructura y Datos

## Tabla(s)

- usuarios

## Campos multi-tenant

- tenant_id en tabla usuarios.

## Repositorios

- UsuarioJpaRepository
- UsuarioRepositoryImpl

## Mappers

- UsuarioMapper

## Consideraciones de seguridad

- JWT firmado HS256.
- Claims: role, tenantId.
- SecurityFilterChain stateless.
