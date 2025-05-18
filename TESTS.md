# 1.- Servicio escogido

Al igual que en el código original, contamos únicamente con un servicio, por lo que he optado por trabajar con la clase `ActividadServicios`. Esta clase actúa como una especie de intermediaria entre la lógica interna (repositorios y entidades) y la interfaz que se comunica con el usuario, como la consola.

La finalidad del servicio es recoger métodos de las distintas clases y utilizarlos para que después se puedan ejecutar y mostrar adecuadamente desde la parte visible del programa.

---

# 2.- Métodos escogidos

Este servicio, que funciona como el núcleo funcional de la aplicación, contiene una buena cantidad de métodos. Por razones de organización, y dado que somos varios integrantes en el grupo, hemos repartido el trabajo de testing unitario. En mi caso, me he encargado de tres métodos concretos.

## Funciones escogidas

He seleccionado los siguientes tres métodos del servicio para realizar pruebas unitarias:

### Métodos

- `filtrarPorTipo(tipo: Class<out Actividad>?): List<Actividad>`: Encargado de filtrar las actividades por su tipo o clase.
- `filtrarPorEstado(estado: Estado): List<Tarea>`: Filtra tareas basándose en el estado en el que se encuentran.
- `filtrarPorUsuario(nombreUsuario: String): List<Tarea>`: Devuelve tareas asignadas a un usuario determinado.

### Parámetros de entrada

- `filtrarPorTipo(tipo: Class<out Actividad>?)`: Recibe la clase que define el tipo de actividad. No acepta valores nulos.
- `filtrarPorEstado(estado: Estado)`: Toma como entrada un valor del enumerado `Estado`, que representa el estado actual de una tarea.
- `filtrarPorUsuario(nombreUsuario: String)`: Acepta un `String` que representa el nombre del usuario asignado.

### Resultado esperado

- `filtrarPorTipo`: Debe devolver una lista de actividades que sean del tipo indicado. Si el tipo es `null`, debe lanzar una `IllegalArgumentException`.
- `filtrarPorEstado`: Devuelve una lista de tareas cuyo estado coincida con el proporcionado.
- `filtrarPorUsuario`: Retorna una lista con las tareas asignadas al usuario dado. Si no existe dicho usuario, devuelve una lista vacía.

---

# 3.- Casos de prueba

Para cada uno de estos métodos he definido al menos dos pruebas: una con entrada válida y otra que represente un escenario límite o inválido.

| Método                | Caso de prueba                  | Parámetros                                  | Resultado esperado                                |
|-----------------------|---------------------------------|---------------------------------------------|---------------------------------------------------|
| `filtrarPorTipo`      | Tipo válido                     | Clase concreta que hereda de `Actividad`    | Lista con actividades del tipo especificado       |
| `filtrarPorTipo`      | Tipo nulo                       | `null`                                      | Lanza `IllegalArgumentException`                  |
| `filtrarPorEstado`    | Estado con coincidencias        | Valor `Estado` con tareas asociadas         | Lista con tareas que coinciden con el estado      |
| `filtrarPorEstado`    | Estado sin coincidencias        | `Estado` sin tareas                         | Lista vacía                                       |
| `filtrarPorUsuario`   | Usuario válido                  | Nombre de un usuario existente              | Lista con tareas asignadas a ese usuario          |
| `filtrarPorUsuario`   | Usuario inexistente             | Nombre que no corresponde a ningún usuario  | Lista vacía                                       |

---

# 4.- Tests

Para poder ejecutar correctamente las pruebas unitarias, fue necesario añadir las dependencias correspondientes a `Kotest` y `MockK` en el archivo `build.gradle.kts`.

### [`build.gradle.kts`](https://github.com/moraalees/TaskManagerEntornos/blob/Fran/build.gradle.kts)

En cuanto a la implementación de las pruebas, he utilizado una clase basada en `DescribeSpec`, que permite estructurar los tests de forma ordenada y clara. Gracias a `mockk(relaxed = true)`, se crea un repositorio simulado que no necesita definir comportamientos concretos para cada método.

### [`ActividadServiceTest`](https://github.com/moraalees/TaskManagerEntornos/blob/Fran/src/test/ActividadServiceTest.kt)

Para `filtrarPorTipo`, la prueba comprueba que el servicio devuelva únicamente las actividades que coinciden con el tipo proporcionado. También se incluye un test que verifica que pasar un valor `null` lanza la excepción correspondiente.

### [`filtrarPorTipo`](https://github.com/moraalees/TaskManagerEntornos/blob/Fran/src/test/ActividadServiceTest.kt)

En `filtrarPorEstado`, se valida que solo se devuelvan las tareas cuyo estado coincide exactamente con el estado dado. Además, se asegura que cuando no hay coincidencias, el método retorna una lista vacía sin fallos.

### [`filtrarPorEstado`](https://github.com/moraalees/TaskManagerEntornos/blob/Fran/src/test/ActividadServiceTest.kt)

Para el caso de `filtrarPorUsuario`, los tests comprueban si el servicio devuelve correctamente las tareas cuando el nombre del usuario existe en el sistema. También se incluye una prueba para verificar que si no se encuentra el usuario, el método retorna una lista vacía.

### [`filtrarPorUsuario`](https://github.com/moraalees/TaskManagerEntornos/blob/Fran/src/test/ActividadServiceTest.kt)

---
