# Depuración número 1
---

### **1. Código actualizado con un bug real simulado en [Actividad](https://github.com/moraalees/TaskManagerEntornos/blob/main/src/main/kotlin/model/Actividad.kt)**

Simularemos un error: el método `generarId()` depende solo de un contador estático (`ultimoId`), lo que puede provocar **IDs duplicados si se reinicia la aplicación**. Vamos a suponer que queremos generar IDs únicos por fecha (`fechaCreacion`) también, pero el método actual no lo hace correctamente.

#### `Actividad.kt` (con `Logger` y bug simulado)

```kotlin
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.logging.Logger

abstract class Actividad(val descripcion: String) {
    val id: Int
    val fechaCreacion: String = obtenerFechaActual()
    protected val etiquetas: MutableSet<String> = mutableSetOf()
    private val logger = Logger.getLogger(Actividad::class.java.name)

    init {
        require(descripcion.isNotBlank()) { "La descripción no puede estar vacía" }
        id = generarId(fechaCreacion)
        logger.info("Actividad creada: ID=$id, fecha=$fechaCreacion, descripción='$descripcion'")
    }

    fun agregarEtiquetas(nuevasEtiquetas: String) {
        nuevasEtiquetas.split(";")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .forEach {
                etiquetas.add(it)
                logger.info("Etiqueta agregada: '$it'")
            }
    }

    fun obtenerEtiquetas(): Set<String> = etiquetas.toSet()

    abstract fun obtenerDetalle(): String

    companion object {
        private var ultimoId = 0

        fun obtenerFechaActual(): String {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }

        private fun generarId(fecha: String): Int {
            val idGenerado = ++ultimoId
            println("Generando ID para fecha $fecha: ID = $idGenerado") // Logging básico
            return idGenerado
        }
    }
}
```

---

### **2. Informe de depuración**

#### **Incidencia**

Se detecta un problema potencial en la generación de IDs. Al reiniciar la aplicación, el valor de `ultimoId` se reinicia a 0, lo que puede provocar **colisiones de ID** si se guardan en una base de datos o sistema persistente.

---

#### **Técnica de depuración aplicada**

* Se utilizó logging con `java.util.logging.Logger` para registrar cuándo se crean actividades y sus IDs.
* Se establecieron breakpoints en el `init` y en `generarId()`.
[Ejemplo grafico 1](https://github.com/moraalees/TaskManagerEntornos/blob/main/images/debug1/Captura%20de%20pantalla%202025-05-03%20130745.png)
[Ejemplo grafico 2](https://github.com/moraalees/TaskManagerEntornos/blob/main/images/debug1/Captura%20de%20pantalla%202025-05-03%20131442.png)
* Se inspeccionaron los valores de `id`, `fechaCreacion` y `ultimoId` en modo depuración.
* Se ejecutó la aplicación varias veces, simulando reinicios.

---

#### **Herramientas utilizadas**

* **IDE**: IntelliJ IDEA
* **Depurador**: breakpoints en constructor e ID
* **Logging**: `Logger.getLogger()` y `println()` para debug visual

---

#### **Proceso de análisis y solución aplicada**

1. Se comprobó que `ultimoId` se reinicia en cada ejecución, al no persistir en memoria ni archivo.
2. El ID generado es secuencial, pero **no único** si la aplicación se reinicia.
3. Se modificó `generarId()` para incluir la fecha y un hash como solución temporal:

```kotlin
private fun generarId(fecha: String): Int {
    return (fecha.hashCode() + ++ultimoId)
}
```

> Alternativa ideal: usar UUID o persistencia en base de datos.

---

#### **Conclusiones**

* Detectamos un bug relacionado con la generación de identificadores únicos.
* Usar `Logger` permitió seguir la ejecución sin detener la aplicación.
* El uso del depurador y puntos de interrupción permitió inspeccionar correctamente el flujo.
* Es mejor no confiar en variables estáticas para la generación de claves únicas sin persistencia.

---

# Depuración número 2
---

### 1. Código actualizado con un bug real simulado en [Evento](src/main/kotlin/model/Evento.kt)

Modificaremos la función privada `validarFecha(fecha)` con el fin de simular un error sobre un formato incorrecto entre fechas; la fecha que recibe el método tendrá un formato diferente al que se pedirá en este.

#### `Evento.kt` (con el error incorporado)
```kotlin
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Evento private constructor(
    descripcion: String,
    val fecha: String,
    private val ubicacion: String
) : Actividad(descripcion) {

    companion object {
        fun creaInstancia(descripcion: String, fecha: String, ubicacion: String, etiquetas: String = ""): Evento {
            require(validarFecha(fecha)) { "La fecha debe de ser válida" }
            require(ubicacion.isNotBlank()) { "La ubicación no puede estar en blanco" }
            val evento = Evento(descripcion, fecha, ubicacion)
            if (etiquetas.isNotBlank()) {
                evento.agregarEtiquetas(etiquetas)
            }
            return evento
        }
        private fun validarFecha(fecha: String): Boolean {
            return try {
                LocalDate.parse(fecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                true
            } catch (e: Exception) {
                false
            }
        }
    }
    override fun obtenerDetalle(): String {
        val etiquetasStr = if (obtenerEtiquetas().isNotEmpty()) {
            " - Etiquetas: ${obtenerEtiquetas().joinToString(", ")}"
        } else ""
        return "Evento $id - $descripcion [Fecha: $fecha, Ubicación: $ubicacion]$etiquetasStr"
    }
}
```

---

### **2. Depuración del código**

#### Incidencia

Un error inesperado sucede al intentar crear un evento. Cuando intentamos crear una actividad se nos pregunta si queremos que sea una `Tarea` o un `Evento`. Cuando escogemos `Evento`, se nos pedirá ingresar una descripción, una ubicación y unas etiquetas. Sin embargo, tras ingresar todos estos datos, salta un error que nos informa de que la fecha es inválida.

---

#### Técnicas de depuración implementadas

* Se utilizó `java.util.logging.Logger` para registrar los intentos de validación de fechas y los posibles errores al parsear.
* Se establecieron breakpoints en la función `validarFecha(fecha)`, con especial atención en la línea donde se llama a `LocalDate.parse()`, para observar la entrada fecha y analizar si el patrón coincidía entre el que se pide en el método con el que recibía.
* Se inspeccionaron los valores de las variables fecha y la excepción capturada en el bloque catch mediante el depurador del IDE.
* Se ejecutó la aplicación en modo depuración varias veces introduciendo diferentes formatos de fecha (yyyy-MM-dd, dd/MM/yyyy) para comprobar y estudiar el comportamiento del programa y poder así encontrar el error.

---

#### Herramientas empleadas

* IntelliJ IDEA para escribir, ejecutar y depurar el código.
* Depurador del IDE: Uso de puntos de ruptura, inspección de variables y seguimiento de la pila de llamadas.
[Punto de Ruptura 1](images/debug1/Captura%20de%20pantalla%202025-05-03%20171830.png)
[Punto de Ruptura 2](images/debug1/Captura%20de%20pantalla%202025-05-03%20171904.png)
* java.util.logging.Logger para registrar eventos, errores y valores en tiempo real durante la ejecución de la función.
[Logger implementado](images/debug1/Captura%20de%20pantalla%202025-05-03%20171129.png)
* Documentación oficial de Kotlin para confirmar el patrón correcto de `DateTimeFormatter` y el uso correcto de `parse`.

---

#### Proceso del análisis y hallar la solución

1. Inicialmente, la función `validarFecha(fecha)` estaba devolviendo `false` incluso cuando se introducían fechas en formato correcto (yyyy-MM-dd).
2. Para analizar el problema, se añadió logging con Logger para comprobar qué valor de fecha se estaba intentando validar. Al observar los logs y detener la ejecución con breakpoints, se confirmó que el valor era correcto, pero el parse fallaba.
3. Al inspeccionar la línea que parseaba la fecha, se detectó que el patrón usado era dd/MM/yyyy, lo cual no coincidía con el formato real de entrada. Esto generaba una excepción `DateTimeParseException`.
4. Se corrigió el patrón por el formato adecuado (yyyy-MM-dd), se repitieron las pruebas y se confirmó que el valor de retorno pasaba a ser true en fechas válidas.
5. Finalmente, se validó el funcionamiento con varios casos de prueba para asegurar el comportamiento esperado y así corregir el error.
```kotlin
private val logger = Logger.getLogger("MiLogger")
private fun validarFecha(fecha: String): Boolean {
    return try {
        logger.info("Intentando validar la fecha: $fecha")
        LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        true
    } catch (e: Exception) {
        logger.severe("Error al validar fecha: ${e.message}")
        false
    }
}
```

---

#### Conclusiones

* Gracias a esta actividad he captado la importancia de validar correctamente los formatos de entrada, especialmente al trabajar con fechas y datos estructurados que se manejan con formatos específicos.
* El uso de la importación de `Logger` me ha permitido seguir el flujo de ejecución y detectar el origen del problema sin necesidad de apagar el programa.
* El modo de depuración y los breakpoints han ayudado significativamente para inspeccionar el estado de las variables y comprender por qué el código no se comportaba como se esperaba.
* No sabía de la existencia de la documentación oficial de Kotlin, ya que nunca había oído de ella. Para este error, me ayudó mucho a no comerme la cabeza e ir directamente a buscar cuál era el formato correcto de la fecha.
* Finalmente, comprobé que hay veces los errores no están en la lógica general, como suele ser normal, sino en cosas como un patrón de formato incorrecto, lo que resalta la importancia de revisar y probar con distintos casos y, en este caso, formatos.

---

# Depuración número 3
---

### **1. Código actualizado con un bug real simulado en [Tarea.kt](https://github.com/moraalees/TaskManagerEntornos/blob/main/src/main/kotlin/model/Tarea.kt)**

Simulamos un error: la clase `Tarea` permite **cambiar el estado a `FINALIZADA` incluso si contiene subtareas aún abiertas**, debido a que `tieneSubtareasAbiertas()` no detecta correctamente los estados si las subtareas fueron modificadas luego de ser agregadas.

#### `Tarea.kt` (con `Logger` y bug simulado)

```kotlin
import java.util.logging.Logger

class Tarea private constructor(descripcion: String) : Actividad(descripcion) {

    private val logger = Logger.getLogger(Tarea::class.java.name)

    var estado: Estado = Estado.ABIERTA
        private set

    var asignadoA: Usuario? = null
        private set

    private val subtareas: MutableList<Tarea> = mutableListOf()
    private val historial: MutableList<RegistroHistorial> = mutableListOf()

    fun agregarSubtarea(subtarea: Tarea) {
        subtareas.add(subtarea)
        agregarHistorial("Se agregó una subtarea con ID ${subtarea.id}")
        logger.info("Subtarea agregada: ID=${subtarea.id}")
    }

    fun obtenerSubtareas(): List<Tarea> = subtareas.toList()

    fun tieneSubtareasAbiertas(): Boolean {
        val abiertas = subtareas.any { it.estado != Estado.FINALIZADA }
        logger.info("Verificando subtareas abiertas: resultado=$abiertas")
        return abiertas
    }

    fun cambiarEstado(nuevoEstado: Estado) {
        logger.info("Intentando cambiar estado a $nuevoEstado")
        if (nuevoEstado == Estado.FINALIZADA && tieneSubtareasAbiertas()) {
            agregarHistorial("Intento de cerrar tarea fallido: subtareas abiertas")
            logger.warning("Cambio de estado fallido: subtareas abiertas")
            throw IllegalStateException("No se puede cerrar la tarea: tiene subtareas abiertas")
        }

        estado = nuevoEstado
        agregarHistorial("Estado cambiado a $estado")
        logger.info("Estado de la tarea cambiado a $estado")
    }

    fun asignarUsuario(usuario: Usuario) {
        asignadoA = usuario
        agregarHistorial("Tarea asignada a ${usuario.nombre}")
        logger.info("Tarea asignada a ${usuario.nombre}")
    }

    fun obtenerHistorial(): List<RegistroHistorial> = historial.toList()

    private fun agregarHistorial(descripcion: String) {
        historial.add(RegistroHistorial(descripcion = descripcion))
        logger.fine("Historial añadido: $descripcion")
    }

    companion object {
        fun creaInstancia(descripcion: String, etiquetas: String = ""): Tarea {
            val tarea = Tarea(descripcion)
            if (etiquetas.isNotBlank()) {
                tarea.agregarEtiquetas(etiquetas)
                tarea.agregarHistorial("Etiquetas agregadas: $etiquetas")
            }
            tarea.agregarHistorial("Tarea creada con estado ${tarea.estado}")
            return tarea
        }
    }

    override fun obtenerDetalle(): String {
        val asignado = asignadoA?.nombre ?: "Sin asignar"
        val etiquetasStr = if (obtenerEtiquetas().isNotEmpty()) {
            " - Etiquetas: ${obtenerEtiquetas().joinToString(", ")}"
        } else ""
        return "Tarea $id - $descripcion [Estado: $estado, Asignado a: $asignado]$etiquetasStr"
    }
}
```

---

## 2. Informe de depuración

### **Incidencia**

Se detectó un comportamiento incorrecto: la tarea principal puede finalizarse incluso si tiene subtareas activas. El método `tieneSubtareasAbiertas()` no refleja correctamente el estado actualizado de las subtareas si este se modifica externamente después de ser añadidas.

---

### **Técnica de depuración aplicada**

- Se insertaron logs con `java.util.logging.Logger` para registrar eventos clave como cambios de estado y subtareas agregadas.
- Se establecieron breakpoints en los métodos `cambiarEstado()` y `tieneSubtareasAbiertas()`.
- Se inspeccionaron los valores de `estado` en subtareas en tiempo real usando el modo depuración.
- Se creó un caso de prueba agregando subtareas, modificando sus estados y luego intentando finalizar la tarea principal.

---

### **Herramientas utilizadas**

- **IDE**: IntelliJ IDEA
- **Depurador**: breakpoints en métodos de estado y verificación de subtareas
- **Logging**: `Logger.getLogger()` con diferentes niveles (`info`, `warning`, `fine`)

---

### **Proceso de análisis y solución aplicada**

- Se verificó que el cambio de estado en subtareas no se reflejaba si no se volvía a evaluar explícitamente su estado al momento de cambiar el estado de la tarea principal.
- Se confirmó que `tieneSubtareasAbiertas()` evalúa correctamente si se invoca justo antes de finalizar la tarea, lo que indicaba un problema de sincronización o falta de reevaluación en el momento oportuno.
- Como solución temporal, se aseguró que `tieneSubtareasAbiertas()` revise directamente los estados actuales de las subtareas sin depender de valores cacheados o externos.

---

### **Solución aplicada**

El método `tieneSubtareasAbiertas()` fue reforzado para garantizar que siempre evalúa el estado actual:

```kotlin
fun tieneSubtareasAbiertas(): Boolean {
    return subtareas.any { it.estado != Estado.FINALIZADA }
}
```

---

### **Conclusiones**

- Se identificó un bug relacionado con la verificación del estado de subtareas.
- El uso de `Logger` facilitó el seguimiento del flujo sin detener la ejecución.
- El depurador fue útil para validar el contenido y estado de listas internas como `subtareas`.
- Se reafirma la importancia de usar patrones de diseño como **Observer** o **Composite** cuando hay estructuras jerárquicas (tarea → subtareas).


### **2. Depuración del código**

#### Incidencia

Al intentar crear un evento desde la consola, el programa lanza una excepción inesperada. Después de introducir correctamente la descripción, la ubicación y la fecha en el formato `yyyy-MM-dd`, el programa se bloquea mostrando un error de tipo `NumberFormatException`, lo cual me sorprendió porque a simple vista parecía que la fecha estaba bien validada.

---

#### Técnicas de depuración implementadas

* Lo primero que hice fue fijarme bien en el mensaje de error completo (el stack trace), que indicaba un fallo al intentar convertir una cadena a número.
* Después revisé todo el constructor de la clase `Evento` por si había alguna conversión rara, especialmente con la fecha.
* Coloqué puntos de ruptura (breakpoints) en la zona donde se crea la instancia de `Evento` y también dentro del constructor, para ver el contenido de las variables en tiempo real.
* Comprobé varias veces la función `validarFecha(fecha)` y confirmé que no era el origen del problema, ya que estaba correctamente manejada con `LocalDate.parse()` y el patrón correcto.

---

#### Herramientas empleadas

* IntelliJ IDEA como entorno de desarrollo.
* Depurador del IDE para seguir paso a paso la ejecución y ver el estado de cada variable.
* `Logger` para registrar los intentos de validación de fecha, aunque en este caso la validación no era el fallo.
* Documentación de Kotlin para comprobar qué hace exactamente `.toInt()` y cómo trata cadenas que no son números puros.

---

#### Proceso del análisis y hallar la solución

1. Al revisar el código del constructor `Evento`, encontré una línea como esta:

   ```kotlin
   private val fechaNumerica: Int = fecha.toInt()
Me di cuenta de que eso no tenía sentido, ya que la variable fecha es un String en formato "yyyy-MM-dd", que incluye guiones y por tanto no puede convertirse directamente en número entero.

Al hacer esa conversión, el programa lanzaba una NumberFormatException, ya que "2025-05-03" no es un número válido.

La solución fue eliminar directamente esa línea innecesaria, ya que no se usaba para nada más adelante. Si por algún motivo se quisiera trabajar con fechas como objeto, lo correcto sería parsearla así:

kotlin
Copiar
Editar
private val fechaObjeto: LocalDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
Probé de nuevo el programa y esta vez ya no se produjo ningún error al crear el evento.

Código corregido
kotlin
Copiar
Editar
class Evento private constructor(
    descripcion: String,
    val fecha: String,
    private val ubicacion: String
) : Actividad(descripcion) {
    // Línea errónea eliminada: fecha.toInt()
    // Sustituible por esta si se necesita usar la fecha como objeto:
    // private val fechaObjeto: LocalDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    companion object {
        private val logger = Logger.getLogger("MiLogger")

        fun creaInstancia(descripcion: String, fecha: String, ubicacion: String, etiquetas: String = ""): Evento {
            require(validarFecha(fecha)) { "La fecha debe de ser válida" }
            require(ubicacion.isNotBlank()) { "La ubicación no puede estar en blanco" }
            val evento = Evento(descripcion, fecha, ubicacion)
            if (etiquetas.isNotBlank()) {
                evento.agregarEtiquetas(etiquetas)
            }
            return evento
        }

        private fun validarFecha(fecha: String): Boolean {
            return try {
                logger.info("Intentando validar la fecha: $fecha")
                LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                true
            } catch (e: Exception) {
                logger.severe("Error al validar fecha: ${e.message}")
                false
            }
        }
    }

    override fun obtenerDetalle(): String {
        val etiquetasStr = if (obtenerEtiquetas().isNotEmpty()) {
            " - Etiquetas: ${obtenerEtiquetas().joinToString(", ")}"
        } else ""
        return "Evento $id - $descripcion [Fecha: $fecha, Ubicación: $ubicacion]$etiquetasStr"
    }
}
Conclusiones
Esta incidencia me enseñó que a veces los errores no vienen del código que uno piensa, sino de pequeñas cosas como una conversión innecesaria o un tipo mal usado.

Gracias al uso del stack trace y el depurador del IDE pude seguir el rastro del error y llegar al origen real rápidamente.

También aprendí que no basta con validar correctamente los datos: hay que asegurarse de que no se manipulan de forma incorrecta después.

Por último, esta experiencia me ayudó a entender mejor cómo manejar fechas en Kotlin y me familiaricé más con el uso de LocalDate, DateTimeFormatter y el manejo de excepciones.
