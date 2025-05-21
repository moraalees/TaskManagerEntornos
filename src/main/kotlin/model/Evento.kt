package es.prog2425.taskmanager.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.logging.Logger
import java.time.format.DateTimeParseException

class Evento private constructor(
    descripcion: String,

    /**
     * Fecha del evento en formato "yyyy-MM-dd".
     */
    val fecha: String,

    /**
     * Ubicación donde se realizará el evento.
     *
     * Privada ya que no se debe de acceder desde ninguna clase
     */
    private val ubicacion: String
) : Actividad(descripcion) {

    companion object {
        /**
         * Variable que usé en las pruebas anteriores para mirar si funcionaba correctamente
         */
        private val logger = Logger.getLogger("MiLogger")

        /**
         * Crea una nueva instancia de [Evento] validando la fecha y ubicación.
         *
         * Esta clase también permite agregar etiquetas opcionalmente.
         *
         * @param descripcion Descripción del evento.
         * @param fecha Fecha del evento.
         * @param ubicacion Ubicación del evento (no puede estar en blanco).
         * @param etiquetas Etiquetas asociadas al evento (opcional, ya que
         * viene preestablecido su valor).
         * @return una nueva instancia de [Evento].
         * @throws IllegalArgumentException si los parámetros no son válidos.
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
         * Valida que una fecha tenga el formato correcto "yyyy-MM-dd".
         *
         * Es privada ya que no se debe de poder acceder desde otra parte.
         *
         * @param fecha la fecha a validar.
         * @return Boolean: true si la fecha es válida o false en caso contrario.
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
     * Devuelve una instancia de String que contiene con los atributos del evento.
     *
     * @return Descripción del evento.
     */
    override fun obtenerDetalle(): String {
        val etiquetasStr = if (obtenerEtiquetas().isNotEmpty()) {
            " - Etiquetas: ${obtenerEtiquetas().joinToString(", ")}"
        } else ""
        return "Evento $id - $descripcion [Fecha: $fecha, Ubicación: $ubicacion]$etiquetasStr"
    }
}