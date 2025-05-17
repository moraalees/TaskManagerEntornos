package es.prog2425.taskmanager.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.logging.Logger

/**
 * Representa un evento dentro del sistema, con descripción, fecha y ubicación.
 *
 * @property fecha Fecha del evento en formato `yyyy-MM-dd`.
 * @property ubicacion Ubicación donde se realiza el evento.
 * @constructor privado para forzar la creación a través del método [creaInstancia].
 *
 * @param descripcion Descripción del evento.
 */
class Evento private constructor(
    descripcion: String,
    val fecha: String,
    private val ubicacion: String
) : Actividad(descripcion) {

    private val fechaNumerica: Int = fecha.toInt()

    companion object {
        private val logger = Logger.getLogger("MiLogger")

        /**
         * Fabrica una nueva instancia de [Evento] validando la fecha y ubicación.
         *
         * @param descripcion Descripción del evento.
         * @param fecha Fecha del evento en formato `yyyy-MM-dd`.
         * @param ubicacion Ubicación donde se realizará el evento.
         * @param etiquetas Etiquetas opcionales separadas por `;` que se asignan al evento.
         * @return Una instancia válida de [Evento].
         * @throws IllegalArgumentException si la fecha no es válida o la ubicación está vacía.
         */
        fun creaInstancia(descripcion: String, fecha: String, ubicacion: String, etiquetas: String = ""): Evento {
            require(validarFecha(fecha)) { "La fecha debe de ser válida" }
            require(ubicacion.isNotBlank()) { "La ubicación no puede estar en blanco" }
            val evento = Evento(descripcion, fecha, ubicacion)
            if (etiquetas.isNotBlank()) {
                evento.agregarEtiquetas(etiquetas)
            }
            return evento
        }

        /**
         * Valida que la fecha proporcionada tenga el formato correcto y sea una fecha válida.
         *
         * @param fecha Cadena con la fecha a validar en formato `yyyy-MM-dd`.
         * @return `true` si la fecha es válida, `false` en caso contrario.
         */
        private fun validarFecha(fecha: String): Boolean {
            return try {
                logger.info("Intentando validar la fecha: $fecha")
                LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                true
            } catch (e: DateTimeParseException) {
                logger.severe("Error al validar fecha: ${e.message}")
                false
            }
        }
    }

    /**
     * Obtiene una representación textual detallada del evento,
     * incluyendo descripción, fecha, ubicación y etiquetas asociadas.
     *
     * @return Cadena con el detalle del evento.
     */
    override fun obtenerDetalle(): String {
        val etiquetasStr = if (obtenerEtiquetas().isNotEmpty()) {
            " - Etiquetas: ${obtenerEtiquetas().joinToString(", ")}"
        } else ""
        return "Evento $id - $descripcion [Fecha: $fecha, Ubicación: $ubicacion]$etiquetasStr"
    }
}

