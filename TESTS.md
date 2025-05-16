# 1.- Servicio escogido

Como solo disponemos de un solo servicio en todo el código, me veo en la necesidad de escoger la clase `ActividadServicios`. Esta clase es la que simula ser como la intermediaria entre las clases y la consola.
El servicio recoge los métodos de las clases y los emplea para que después la consola lo muestre y ejecute.

---

# 2.- Métodos escogidos

Este servicio, al ser el único en todo nuestro programa, posee un total de 19 métodos. Como son demasiados, y entre que unos son muy sencillos y somos 4 personas en el grupo, hemos decidido dividir los métodos
con el fin de probar en 3 métodos por persona las pruebas unitarias.

## Funciones escogidas
He decidido realizar las pruebas unitarias sobre las siguientes funciones:

### Métodos

- `filtrarPortipo(tipo: Class<out Actividad>): List<Actividad>`: Filtra, como su nombre indica, por el tipo o clase de la Actividad.
- `filtrarPorEstado(estado: Estado): List<Tarea>`: Filtra las tareas según el estado que tengan asignados.
- `obtenerTareaPorId(id: Int?): Tarea?`: Busca una tarea por su ID y la retorna.

### Parámetros de entrada

- `filtrarPortipo(tipo: Class<out Actividad>): List<Actividad>`: Recibe el tipo de la clase.
- `filtrarPorEstado(estado: Estado): List<Tarea>`: Recibe el valor de la tarea, el cual se obtiene a través de una `enum class`.
- `obtenerTareaPorId(id: Int?): Tarea?`: Recibe el ID de la tarea, el cual puede o ser un número entero o un valor nulo.

### Resultado esperado

- `filtrarPortipo(tipo: Class<out Actividad>): List<Actividad>`: Este método está diseñado por y para filtrar actividades (ya sean tareas o eventos) y en otra parte del programa se muestran los resultados.
- `filtrarPorEstado(estado: Estado): List<Tarea>`: Esta función filtra las actividades (tareas y eventos) por su `Estado`, y se muestran los resultados gracias a otra parte del programa.
- `obtenerTareaPorId(id: Int?): Tarea?`: Al recibir un ID, busca en el programa si hay actividades que tengan ese ID y la/s muestra.

---

# 3.- Casos de prueba

Para cada función/método, he tomado la decisión de asiganrle a cada uno un valor válido y por otra parte uno inválido que haga que no funcione de forma idónea en el programa:

| Método               | Caso de prueba                    | Parámetros                                 | Resultado esperado                                |
|----------------------|-----------------------------------|--------------------------------------------|---------------------------------------------------|
| `filtrarPorTipo`     | Tipo válido                       | Clase concreta que hereda de `Actividad`   | Lista con actividades del tipo indicado           |
| `filtrarPorTipo`     | Tipo nulo (no permitido)          | null (vacío)                               | Lanza `IllegalArgumentException`                  |
| `filtrarPorEstado`   | Estado válido                     | `Estado` con tareas en ese estado          | Lista con tareas que coinciden con el estado      |
| `filtrarPorEstado`   | Estado sin coincidencias          | `Estado` sin tareas asociadas              | Lista vacía                                       |
| `obtenerTareaPorId`  | ID válido                         | ID existente                               | Devuelve la tarea correspondiente                 |
| `obtenerTareaPorId`  | ID inexistente                    | ID que no corresponde a ninguna tarea      | Devuelve `null`                                   |

---

# 4.- Tests

Para poder realizar las pruebas de forma correcta, primero he tenido que agregar al archivo `build.gradle.kts` algunas dependencias para poder usar Kotest y MockK en el proyecto.

### [`build.gradle.kts`](https://github.com/moraalees/TaskManagerEntornos/blob/9ad6431727d605bffbf9635d073d2d890f957e9c/build.gradle.kts#L13C1-L24C2)

Sobre cómo he realizado los tests, he decidido crear una clase que utilice dichas dependencias (Kotest / MockK). En lugar de poner, como se hace tradicionalmente, @Tests al inicio del código, con hacer que la clase herede de `DescribeSpec` ya bastaría. En esta clase, 'mockeo' el directorio para simular el comportamiento de `IActividadRepository`. El método `mockk(relaxed = true)` hace que se pueda crear un mockK del repositorio en el que los métodos no necesitan ser definidos anteriormente para devolver valores por defecto.

### [`ActividadServiceTest`](https://github.com/moraalees/TaskManagerEntornos/blob/9ad6431727d605bffbf9635d073d2d890f957e9c/src/test/kotlin/ActividadServiceTest.kt#L10C1-L18C6)

---

# 5.- Resultados

Tras ejecutar los respectivos tests que contiene la clase `ActividadServiceTest`, hemos obtenido que las pruebas unitarias se han realizado correctamente, en un total de 8 segundos!

[Resultado](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-16%20133058.png)
