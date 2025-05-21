package es.prog2425.taskmanager.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/** @property RegistroHistorial:
 Esta clase la uso para guardar cosas que han pasado con una tarea, como un historial de acciones.
 Tiene dos datos: la fecha en la que pasó algo (que se pone automáticamente con la función obtenerFechaHoraActual())
 y una descripción que explica qué pasó exactamente, como por ejemplo "estado cambiado".
 Me sirve para ver todo lo que se ha hecho con una tarea desde que se creó.
*/
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