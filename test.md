### 1.Selección del servicio
El servicio es Actividadservicio 

### 2.Lista todos los métodos públicos de ese servicio. Por cada método, anota:
### Nombre del método
### Parámetros de entrada
### Resultado esperado o efecto en el 

1. crearEvento
Parámetros de entrada:
evento: Evento? -Un objeto de tipo Evento, que puede ser nulo.
Resultado esperado / Efecto en el repositorio:
Si el evento no es nulo, se llama al método agregarActividad del repositorio, añadiendo el evento a la lista de actividades.
Si el evento es nulo, lanza una excepción IllegalArgumentException.
2. listarActividades
Parámetros de entrada:
No recibe parámetros.
Resultado esperado / Efecto en el repositorio:
Llama al método obtenerActividades() del repositorio y devuelve una lista de cadenas de texto con los detalles de cada actividad (obtenerDetalle()).
Si no hay actividades, devuelve una lista vacía.
3. obtenerTareasDeUsuario
Parámetros de entrada:
usuario: Usuario? → El usuario del que se quiere tener las tareas (puede ser nulo).
actividades: List<Actividad> → Lista de actividades en las que se puede filtrar.
Resultado esperado / Efecto en el repositorio:
Devuelve una lista de objetos Tarea que están asignados al usuario indicado.
Si el usuario es null, devuelve una lista vacía.
Este método no interactúa directamente con el repositorio, trabaja con la lista de actividades que se le pasa.

### 3.Diseño de casos de prueba.Para cada método, diseña al menos dos casos:

| Método                                         | Caso de prueba               | Estado inicial del mock                                                     | Acción                                                                   | Resultado esperado                                     |
| ---------------------------------------------- | ---------------------------- | --------------------------------------------------------------------------- | ------------------------------------------------------------------------ | ------------------------------------------------------ |
| `crearEvento(evento)`                          | Evento válido                | `mockRepo.agregarActividad()` disponible                                    | Llamar a `crearEvento(evento)`                                           | Evento agregado al repositorio, mismo objeto retornado |
| `crearEvento(evento)`                          | Evento nulo                  | No afecta al mock                                                           | Llamar a `crearEvento(null)`                                             | Lanza `IllegalArgumentException`                       |
| `listarActividades()`                          | Actividades disponibles      | `mockRepo.obtenerActividades()` devuelve lista de 2 actividades con detalle | Llamar a `listarActividades()`                                           | Lista con 2 strings devuelta con los detalles          |
| `listarActividades()`                          | No hay actividades           | `mockRepo.obtenerActividades()` devuelve lista vacía                        | Llamar a `listarActividades()`                                           | Devuelve lista vacía                                   |
| `obtenerTareasDeUsuario(usuario, actividades)` | Usuario con tareas asignadas | No requiere mock directo (usa lista local)                                  | Llamar a `obtenerTareasDeUsuario(usuario, lista con 2 tareas asignadas)` | Devuelve lista con esas 2 tareas                       |
| `obtenerTareasDeUsuario(usuario, actividades)` | Usuario nulo                 | No requiere mock directo                                                    | Llamar a `obtenerTareasDeUsuario(null, lista con 1 tarea)`               | Devuelve lista vacía                                   |


### 4.Implementación de los tests

https://github.com/moraalees/TaskManagerEntornos/blob/0e0049a5ecb197b6551ec5d6f110db7b05ac4c38/src/test/kotlin/ActividadServiciosTest.kt#L12-L20

Estoy preparando los objetos necesarios para hacer las pruebas unitarias. Uso un mock para simular el repositorio y creo una instancia del servicio pasándole ese mock. Todo esto se ejecuta antes de que corran los tests.


https://github.com/moraalees/TaskManagerEntornos/blob/0e0049a5ecb197b6551ec5d6f110db7b05ac4c38/src/test/kotlin/ActividadServiciosTest.kt#L22-L40

Este bloque de código prueba el método crearEvento del servicio: primero valida que se agrega bien un evento válido usando mocks, y luego que lanza un error si se intenta crear un evento nulo. Usa Kotest para hacer las comprobaciones y MockK para simular el comportamiento .

https://github.com/moraalees/TaskManagerEntornos/blob/0e0049a5ecb197b6551ec5d6f110db7b05ac4c38/src/test/kotlin/ActividadServiciosTest.kt#L42-L64

Este bloque prueba que el servicio listarActividades transforma correctamente las actividades del repositorio en una lista de detalles. También asegura que si no hay actividades, devuelve una lista vacía en lugar de fallar o devolver null.


https://github.com/moraalees/TaskManagerEntornos/blob/0e0049a5ecb197b6551ec5d6f110db7b05ac4c38/src/test/kotlin/ActividadServiciosTest.kt#L66-L93

Estas pruebas verifican que obtenerTareasDeUsuario filtra bien las tareas asignadas a un usuario en concreto, ignorando otras actividades como eventos, y también que devuelve una lista vacía si no se proporciona un usuario.

### 5.Ejecución y reporte de resultados

[Kotests hechos correctamente](https://github.com/moraalees/TaskManagerEntornos/blob/Bruno/images/debug1/kotestHechos.png)

