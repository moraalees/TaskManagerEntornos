package es.prog2425.taskmanager.model

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