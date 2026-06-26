# 03. Casos de Uso

## Lista de casos de uso

1. RegistrarUsuarioUseCase
2. AutenticarUsuarioUseCase

## Detalle por caso de uso

### RegistrarUsuarioUseCase

- Entrada: username, email, password, role, tenantId.
- Validaciones: username único.
- Flujo principal: hashear contraseña, crear usuario, guardar.
- Errores: IllegalArgumentException por username duplicado.
- Salida: Usuario persistido.

### AutenticarUsuarioUseCase

- Entrada: username, password.
- Validaciones: existencia de usuario y match de contraseña.
- Flujo principal: validar, generar JWT, retornar payload login.
- Errores: CredencialesInvalidasException.
- Salida: token, username, role, tenantId.
