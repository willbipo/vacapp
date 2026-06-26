# 01. Contexto del Módulo

## Nombre del módulo

- usuarios-auth

## Propósito

- Gestionar autenticación y registro de usuarios por tenant en Vacapp.

## Alcance

- Incluye: login, registro, emisión de JWT, roles y tenant en claims.
- No incluye: recuperación de contraseña, bloqueo por intentos, MFA.

## Actores

- ADMIN
- FARMER
- DOCTOR
- WORKER

## Reglas de negocio clave

1. Todo usuario pertenece a un tenant por tenantId.
2. La contraseña se persiste hasheada con BCrypt.
3. El token JWT debe incluir role y tenantId.

## Dependencias

- Internas: core.security, core.multitenancy.
- Externas: Spring Security, JJWT, MySQL.
