package es.prog2425.taskmanager.model

import java.time.LocalDate
import java.time.format.DateTimeFormatter

abstract class Actividad(val descripcion: String) {
    val id: Int
    val fechaCreacion: String = obtenerFechaActual()
    protected val etiquetas: MutableSet<String> = mutableSetOf()
    init {
        require(descripcion.isNotBlank()) { "La descripción no puede estar vacía" }
        id = generarId(fechaCreacion)
    }
    fun agregarEtiquetas(nuevasEtiquetas: String) {
        nuevasEtiquetas.split(";")
            .map { it.trim() }
            .filter { it.isNotBlank() }
            .forEach { etiquetas.add(it) }
    }
    fun obtenerEtiquetas(): Set<String> = etiquetas.toSet()
    abstract fun obtenerDetalle(): String
    companion object {
        private var ultimoId = 0
        fun obtenerFechaActual(): String {
            return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        }
        private fun generarId(fecha: String): Int {
            return ++ultimoId
        }
    }
}