# Contexto del Proyecto
Actualmente tengo la lógica del backend estructurada para las entidades de "Empleados" y "Ranchos". Necesito construir las vistas (frontend) correspondientes, asegurando que se integren correctamente con mi backend actual. 

Actúa como un desarrollador Full-Stack experto en Java (Spring Boot) y diseño frontend responsivo.

# Stack Tecnológico
Genera las vistas utilizando estrictamente las siguientes tecnologías:
*   HTML5 (HTML puro, sin motor de plantillas)
*   Tailwind CSS vía CDN (utiliza clases de utilidad para un diseño limpio, moderno y responsivo)
*   JavaScript Vanilla (solo para interactividad esencial del lado del cliente)
*   Fetch API para consumir la API REST del backend (sin Thymeleaf)

# Arquitectura y Estructura de Archivos (Obligatorio)
Es fundamental mantener una estricta separación de responsabilidades siguiendo el estándar de Spring Boot. Todo el código que generes debe estar pensado para la siguiente estructura:

*   **`src/main/resources/static/views/`**: Aquí *exclusivamente* irán los archivos `.html` puros (sin Thymeleaf). No debe haber CSS en línea ni scripts extensos en estos archivos.
*   **`src/main/resources/static/css/`**: 
    *   Crea un archivo **`global.css`** *solo* si hay estilos personalizados que Tailwind no cubra.
    *   Crea archivos CSS individuales para cada vista (ej. `usuarios.css`, `ranchos.css`, `ganado.css`) *solo* si hay estilos personalizados que Tailwind no cubra.
*   **`src/main/resources/static/js/`**: 
    *   Crea archivos JS separados por cada vista (ej. `usuarios.js`, `ranchos.js`, `ganado.js`) para manejar la lógica de negocio del frontend, modales o interactividad compleja.
    *   Los datos se cargan dinámicamente usando Fetch API desde los endpoints REST del backend.

# Archivos de Entrada (Adjuntos)
A continuación, te proporcionaré el código de mi backend (Controladores y Modelos) y mi archivo `pom.xml`. 
1.  **Analiza el `pom.xml`:** Verifica que no haya dependencias de Thymeleaf (ya no se usa).
2.  **Analiza el Backend:** Revisa los endpoints REST de los controladores para que las llamadas Fetch API hagan *match* exacto con las rutas.

# Requerimientos de Vistas
Necesito que generes el código para las siguientes vistas, usando HTML puro con Tailwind CSS vía CDN:

## 1. Vista de Usuarios (Gestión de Roles)
*   Debe manejar los siguientes roles: `Admin`, `Farmer`, `Doctor`, y `Worker`.
*   *Instrucción:* La lógica de renderizado condicional se maneja en JavaScript, verificando el rol del usuario desde la API REST y mostrando/ocultando elementos del DOM según corresponda (ej. solo el Admin puede eliminar).

## 2. Vista de Ranchos (Jerarquía)
*   El modelo de negocio dicta que un Rancho tiene una estructura jerárquica: **Rancho > Sección > Potrero**.
*   *Instrucción:* Diseña la vista para que esta jerarquía sea fácil de navegar (por ejemplo, tarjetas anidadas, tablas expansibles o menús desplegables en cascada). Los datos se cargan dinámicamente desde la API REST.

## 3. Vista de Ganado
*   Una vista dedicada a la gestión y visualización del ganado.
*   *Instrucción:* Crea una tabla responsiva con Tailwind para listar los animales, e incluye los botones de acción estándar (Ver detalles, Editar, Eliminar). Los datos se cargan dinámicamente desde la API REST.

# Entregables Esperados
1.  **Recomendaciones del `pom.xml`:** Un resumen de ajustes necesarios en las dependencias (eliminar Thymeleaf si existe).
2.  **Estructura de Directorios:** Un pequeño árbol visual de cómo quedarán los archivos.
3.  **Código por Vista:** Por cada vista (Usuarios, Ranchos, Ganado), entrega por separado:
    *   El archivo `.html` (HTML puro con Tailwind CSS vía CDN).
    *   El archivo `.css` específico (si aplica).
    *   El archivo `.js` específico (con Fetch API para cargar datos desde la API REST).

