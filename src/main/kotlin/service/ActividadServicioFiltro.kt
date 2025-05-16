package es.prog2425.taskmanager.service

import es.prog2425.taskmanager.data.IActividadRepository
import es.prog2425.taskmanager.model.Actividad
import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.model.Evento
import es.prog2425.taskmanager.model.Tarea
import java.time.LocalDate

class ActividadServicioFiltro(val repositorio : IActividadRepository) : IActividadServicioFiltro {
    override fun filtrarPorTipo(tipo: Class<out Actividad>): List<Actividad> {
        return repositorio.obtenerActividades().filter { it.javaClass == tipo }
    }
    override fun filtrarPorEstado(estado: Estado): List<Tarea> {
        return listarTareas().filter { it.estado == estado }
    }
    override fun filtrarPorEtiqueta(etiqueta: String): List<Actividad> {
        return repositorio.obtenerActividades()
            .filter { it.obtenerEtiquetas().contains(etiqueta) }
    }
    override fun filtrarPorUsuario(nombreUsuario: String): List<Tarea> {
        val usuario = usuarios.find { it.nombre == nombreUsuario }
        return if (usuario != null) {
            listarTareas().filter { it.asignadoA == usuario }
        } else {
            emptyList()
        }
    }
    override fun filtrarPorFecha(rango: String): List<Evento> {
        val eventos = repositorio.obtenerActividades().filterIsInstance<Evento>()
        val hoy = LocalDate.now()
        return when (rango.lowercase()) {
            "hoy" -> eventos.filter { it.fecha == hoy.toString() }
            "mañana" -> eventos.filter { it.fecha == hoy.plusDays(1).toString() }
            "semana" -> eventos.filter {
                val fechaEvento = LocalDate.parse(it.fecha)
                fechaEvento.isAfter(hoy) && fechaEvento.isBefore(hoy.plusWeeks(1))
            }
            "mes" -> eventos.filter {
                val fechaEvento = LocalDate.parse(it.fecha)
                fechaEvento.isAfter(hoy) && fechaEvento.isBefore(hoy.plusMonths(1))
            }
            else -> emptyList()
        }
    }
}