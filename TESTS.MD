## 1.Selección del servicio

EL servicio elegido es `ActividadServicios`

## 2.Identificación de métodos

### Nombre del método

- `crearTarea(tarea: Tarea)`
- `asignarUsuarioATarea(tarea: Tarea, usuario: Usuario)`
- `cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado)`

### Parámetros de entrada

- Para `crearTarea()` se ingresa un objeto del tipo `Tarea`.
- Para `asignarUsuarioATarea()` se ingresa un objeto `Tarea` y un objeto `Usuario`.
- Para `cambiarEstadoTarea()` se ingresa un objeto `Tarea` y un objeto `Estado`.


### Resultado esperado o efecto en el repositorio

- `crearTarea()` debe agregar la tarea al repositorio y devolver la tarea creada.
- `asignarUsuarioATarea()` debe asignar un usuario a la tarea, actualizar la tarea en el repositorio y devolver la tarea actualizada.
- `cambiarEstadoTarea()` debe cambiar el estado de la tarea, actualizarla en el repositorio y devolver la tarea modificada.


## 3.Diseño de casos de prueba
### Casos:

| Método                 | Caso                              | Parámetros                          | Resultado esperado                         |
|------------------------|----------------------------------|-----------------------------------|-------------------------------------------|
| `crearTarea`           | Crear tarea válida                | Tarea con descripción válida      | La tarea es agregada correctamente        |
| `crearTarea`           | Crear tarea nula                 | `null`                           | Lanza `IllegalArgumentException`          |
| `asignarUsuarioATarea` | Asignar usuario válido           | Tarea válida, Usuario válido      | Usuario asignado a la tarea correctamente |
| `asignarUsuarioATarea` | Asignar usuario nulo             | Tarea válida, `null`              | Lanza `IllegalArgumentException`          |
| `cambiarEstadoTarea`   | Cambiar estado válido             | Tarea válida, Estado válido       | Estado cambiado correctamente              |
| `cambiarEstadoTarea`   | Cambiar estado nulo              | Tarea válida, `null`              | Lanza `IllegalArgumentException`          |


## 4.Implementación de los tests

Los tests se implementaron usando Kotest y Mockk, creando mocks para el repositorio y verificando las interacciones. Se probaron tanto casos exitosos como casos que lanzan excepciones al recibir parámetros nulos.

## 5.Ejecución y reporte de resultados

Todos los tests fueron ejecutados exitosamente, comprobando el correcto funcionamiento del servicio `ActividadServicios` y la robustez ante entradas inválidas. No se reportaron errores ni excepciones inesperadas durante la ejecución.

