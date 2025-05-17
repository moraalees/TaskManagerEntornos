package es.prog2425.taskmanager.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Representa un registro individual en el historial de una tarea o actividad,
 * con la fecha y hora en que ocurrió el evento y una descripción del mismo.
 *
 * @property fecha Fecha y hora del registro en formato `yyyy-MM-dd HH:mm:ss`.
 *                 Se asigna automáticamente al crear la instancia.
 * @property descripcion Descripción textual del evento registrado.
 */
data class RegistroHistorial(
    val fecha: String = obtenerFechaHoraActual(),
    val descripcion: String
) {
    companion object {
        /**
         * Obtiene la fecha y hora actual formateada como `yyyy-MM-dd HH:mm:ss`.
         *
         * @return Cadena con la fecha y hora actual formateada.
         */
        private fun obtenerFechaHoraActual(): String {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }
}
