package es.prog2425.taskmanager.model

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

         fun generarId(fecha: String): Int {
            val idGenerado = ++ultimoId
            println("Generando ID para fecha $fecha: ID = $idGenerado") // Logging básico
            return idGenerado
        }
    }
}