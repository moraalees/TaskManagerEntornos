package es.prog2425.taskmanager.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.logging.Logger


/**
@property Evento Esta clase representa un evento, como una reunión o algo que tiene fecha y lugar concreto.
Hereda de la clase Actividad, así que también tiene una descripción.
Además, le añado dos propiedades más: la fecha en la que es el evento y la ubicación donde se hace.
Es útil cuando quiero diferenciar entre tareas normales y eventos con más detalles.
 */

class Evento  (
    descripcion: String,
     val fecha: String,
     val ubicacion: String
) : Actividad(descripcion) {


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
