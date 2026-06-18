# Nombre del Proyecto
Sistema de gestión de ventas e inventario para la Panaderia Marcela

## Descripción
Sistema desarrollado en Java como proyecto universitario.


## Tecnologías
- Java con Apache Ant
- MySQL (JDBC)
- Swing (IGU)

## Equipo
- Bryan Alexander Luque Serna — Líder / GitHub
- Ayay Palomino, Diego Alvaro
- Segura Pizarro, Isabella
- Alarcon Maza, Misael
- Moquillaza Gonzales, David Akira


## Requisitos para correr el proyecto
- NetBeans
- JDK 8 o superior
- MySQL instalado
- Ejecutar el script `sql/esquema.sql` para crear la base de datos

## Estructura del proyecto
- `src/` → código fuente Java
- `lib/` → librerías externas (conector MySQL)
- `sql/` → scripts de base de datos
- `docs/` → documentación del equipo

# Panadería App — Estructura del Proyecto

Sistema de escritorio para gestión de ventas e inventario de una panadería, desarrollado en **Java + Swing + JDBC**.

Este documento explica qué hace cada carpeta (`package`) del proyecto, para que todo el equipo sepa dónde ubicar su código y evitemos mezclar responsabilidades.

## Arquitectura general

El proyecto sigue una arquitectura en capas. La idea es que cada parte del código tenga una sola responsabilidad y no se mezcle con las demás:

```
Vista (Swing)  →  Controlador  →  DAO  →  Base de datos
```

- La **Vista** solo muestra pantallas y captura lo que hace el usuario.
- El **Controlador** valida que esos datos tengan sentido (reglas de negocio).
- El **DAO** es el único que habla con la base de datos (SQL puro).
- El **Modelo** son las clases que representan nuestras tablas como objetos Java.

Ninguna capa debería saltarse a otra. Por ejemplo, la Vista nunca debe ejecutar SQL directamente, y el DAO nunca debe validar reglas de negocio.

## Estructura de carpetas

```
PanaderiaApp/
└── src/
    └── panaderia/
        ├── modelo/
        ├── dao/
        ├── conexion/
        ├── factory/
        ├── vista/
        ├── controlador/
        └── util/
```

### `modelo/`

Clases que representan cada tabla de la base de datos como un objeto Java (también llamados POJOs). Solo tienen atributos, constructores, getters y setters. No contienen lógica ni SQL.

Aquí también vive la jerarquía de herencia de `Producto`:

```
Producto (abstracta)
├── Pan
├── Bocadito
├── Bebida
└── Pastel
```

Archivos: `Empleado.java`, `Cliente.java`, `Caja.java`, `Venta.java`, `DetalleVenta.java`, `Producto.java`, `Pan.java`, `Bebida.java`, `Bocadito.java`, `Pastel.java`.

**Regla:** si estás escribiendo un `INSERT` o un `JOptionPane` dentro de una clase de este package, está en el lugar equivocado.

### `dao/`

DAO significa *Data Access Object*. Cada clase aquí se encarga únicamente de ejecutar SQL (SELECT, INSERT, UPDATE, DELETE) para una tabla específica, y de convertir el resultado en objetos del package `modelo`.

Archivos: `EmpleadoDAO.java`, `ClienteDAO.java`, `CajaDAO.java`, `VentaDAO.java`, `DetalleVentaDAO.java`, `ProductoDAO.java`.

**Regla:** un DAO no valida si un precio es negativo ni decide si un login es correcto en términos de negocio; solo ejecuta la consulta y devuelve el resultado. Esa validación va en el `controlador`.

### `conexion/`

Contiene la clase `Conexion.java`, que maneja la conexión a MySQL usando el patrón **Singleton** (una sola instancia de conexión reutilizada en toda la app, en lugar de abrir una conexión nueva cada vez).

Todos los DAO usan esta clase para obtener la conexión:

```java
Connection conn = Conexion.getInstancia().getConexion();
```

**Regla:** nadie fuera de este package debería llamar a `DriverManager.getConnection(...)` directamente.

### `factory/`

Contiene `ProductoFactory.java`, encargada de decidir qué subclase de `Producto` instanciar (`Pan`, `Bebida`, `Bocadito`, `Pastel`) según el campo `categoria` que viene de la base de datos.

Esto evita tener un `switch` repetido en varias partes del código; el DAO le delega esa decisión a la Factory.

### `vista/`

Todos los formularios Swing (`JFrame`, `JPanel`, etc.) que el usuario ve e interactúa directamente.

Archivos: `LoginForm.java`, `MenuPrincipalForm.java`, `EmpleadoForm.java`, `ClienteForm.java`, `ProductoForm.java`, `VentaForm.java`, `CajaForm.java`, `HistorialForm.java`, `ReportesForm.java`.

**Regla:** una clase de este package no debería escribir SQL ni instanciar un DAO directamente para guardar datos sin pasar antes por el `controlador`. Sí puede llamar al controlador y mostrar el resultado (éxito, error, lista de datos, etc.).

### `controlador/`

Capa intermedia entre la `vista` y el `dao`. Aquí va la lógica de negocio: validar campos vacíos, verificar que un total no sea negativo, comprobar que el login sea correcto, etc.

Archivos: `EmpleadoController.java`, `VentaController.java`, `ProductoController.java`, etc.

**Regla:** si la vista necesita saber "¿puedo guardar esto?", la respuesta la da el controlador, no el DAO ni la vista misma.

### `util/`

Clases de utilidad reutilizables en cualquier parte del proyecto, que no pertenecen a ninguna entidad específica.

Archivos:
- `Negocio.java` — constantes fijas del negocio (nombre de la panadería, RUC, dirección). No van en la base de datos porque no cambian.
- `PasswordUtil.java` — funciones para hashear contraseñas (SHA-256) antes de guardarlas o compararlas en el login.
- `ValidacionUtil.java` — validaciones genéricas (campo vacío, formato de DNI, etc.) que se repiten en varios formularios.

## Resumen rápido: ¿dónde pongo mi código?

| Si estás haciendo... | Va en... |
|---|---|
| Una clase que representa una fila de una tabla | `modelo` |
| Un método que ejecuta SQL | `dao` |
| Una pantalla con botones, tablas o campos de texto | `vista` |
| Una validación antes de guardar algo | `controlador` |
| Decidir si crear un Pan, Bebida, etc. | `factory` |
| Algo que se usa en todos lados y no es de una tabla en particular | `util` |


Cada uno trabaja en su propia rama (`feature/nombre-modulo`) siguiendo el flujo de Git ya definido, y al terminar abre un Pull Request hacia `develop` para revisión del equipo.