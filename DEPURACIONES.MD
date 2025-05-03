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

