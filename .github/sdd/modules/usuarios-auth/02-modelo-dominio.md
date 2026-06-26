# 02. Modelo de Dominio

## Entidades

### Usuario

- id: UUID
- username: String
- email: String
- password: String (hash)
- role: Rol
- tenantId: String

## Enumeraciones

- Rol: ADMIN, FARMER, DOCTOR, WORKER.

## Excepciones de dominio

- CredencialesInvalidasException
- UsuarioNoEncontradoException
