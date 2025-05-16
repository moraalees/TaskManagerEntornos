package es.prog2425.taskmanager.service

import es.prog2425.taskmanager.data.IActividadRepository
import es.prog2425.taskmanager.model.Evento
import es.prog2425.taskmanager.model.Tarea

class ActividadServicioGestion(val repositorio : IActividadRepository) : IActividadServicioGestion {
    override fun crearTarea(tarea: Tarea?): Tarea {
        if (tarea == null) {
            throw IllegalArgumentException("La tarea no puede ser nula")
        }
        repositorio.agregarActividad(tarea)  // Guarda la tarea
        return tarea
    }

    override fun crearTarea(descripcion: String, etiquetas: String) {
        requireNotNull(descripcion) { "La tarea no puede ser nula" }
        val tarea = Tarea.Companion.creaInstancia(descripcion, etiquetas)
        repositorio.agregarActividad(tarea)
    }
    override fun crearEvento(descripcion: String, fecha: String, ubicacion: String, etiquetas: String) {
        val evento = Evento.creaInstancia(descripcion, fecha, ubicacion, etiquetas)
        repositorio.agregarActividad(evento)
    }
    override fun listarActividades(): List<String> {
        return repositorio.obtenerActividades().map { it.obtenerDetalle() }
    }
    override fun listarTareas(): List<Tarea> {
        return repositorio.obtenerActividades()
            .filterIsInstance<Tarea>()
            .sortedBy { it.id }
    }
    override fun listarActividadesPorEtiqueta(etiqueta: String): List<String> {
        return repositorio.obtenerActividades()
            .filter { it.obtenerEtiquetas().contains(etiqueta) }
            .map { it.obtenerDetalle() }
    }
}