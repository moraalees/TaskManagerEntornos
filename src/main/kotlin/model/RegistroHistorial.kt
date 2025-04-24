package es.prog2425.taskmanager.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class RegistroHistorial(
    val fecha: String = obtenerFechaHoraActual(),
    val descripcion: String
) {
    companion object {
        private fun obtenerFechaHoraActual(): String {
            return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        }
    }
}