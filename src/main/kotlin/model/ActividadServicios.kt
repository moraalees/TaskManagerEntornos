package es.prog2425.taskmanager.model

import es.prog2425.taskmanager.data.IActividadRepository
import java.time.LocalDate

class ActividadServicios(
    private val repositorio: IActividadRepository
) : IActividadService {
    private val usuarios = mutableListOf<Usuario>()
    private val actividades: List<Actividad>
        get() = repositorio.obtenerActividades()
    override fun crearTarea(tarea: Tarea) {
        repositorio.agregarActividad(tarea)
    }
    override fun crearTarea(descripcion: String, etiquetas: String) {
        val tarea = Tarea.creaInstancia(descripcion, etiquetas)
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
    override fun asignarUsuarioATarea(tarea: Tarea, usuario: Usuario) {
        tarea.asignarUsuario(usuario)
    }
    override fun cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado) {
        tarea.cambiarEstado(nuevoEstado)
    }
    override fun obtenerTareasDeUsuario(usuario: Usuario, actividades: List<Actividad>): List<Tarea> {
        return actividades.filterIsInstance<Tarea>().filter { it.asignadoA == usuario }
    }
    override fun obtenerTareaPorId(id: Int?): Tarea? {
        return actividades.find { it is Tarea && it.id == id } as? Tarea
    }
    override fun obtenerOCrearUsuario(nombre: String): Usuario {
        return usuarios.find { it.nombre == nombre } ?: Usuario(nombre).also { usuarios.add(it) }
    }
    fun obtenerResumenTareas(): Map<String, Any> {
        val tareas = listarTareas()
        val resumen = mutableMapOf<String, Any>()
        resumen["totalTareas"] = tareas.size
        var tareasMadre = 0
        for (tarea in tareas) {
            if (tarea.obtenerSubtareas().isNotEmpty()) {
                tareasMadre++
            }
        }
        resumen["tareasMadre"] = tareasMadre
        val porEstado = mutableMapOf<Estado, Int>()
        for (tarea in tareas) {
            val estado = tarea.estado
            porEstado[estado] = porEstado.getOrDefault(estado, 0) + 1
        }
        resumen["porEstado"] = porEstado
        val subtareasPorMadre = mutableMapOf<Int, Int>()
        for (tarea in tareas) {
            subtareasPorMadre[tarea.id] = tarea.obtenerSubtareas().size
        }
        resumen["subtareasPorMadre"] = subtareasPorMadre
        return resumen
    }
    fun obtenerEventosProximos(): Map<String, List<Evento>> {
        val eventos = repositorio.obtenerActividades().filterIsInstance<Evento>()
        val hoy = LocalDate.now()
        val manana = hoy.plusDays(1)
        val finSemana = hoy.plusWeeks(1)
        val resultado = mutableMapOf<String, List<Evento>>()
        val eventosHoy = mutableListOf<Evento>()
        val eventosManana = mutableListOf<Evento>()
        val eventosSemana = mutableListOf<Evento>()
        for (evento in eventos) {
            val fechaEvento = LocalDate.parse(evento.fecha)
            if (evento.fecha == hoy.toString()) {
                eventosHoy.add(evento)
            }
            if (evento.fecha == manana.toString()) {
                eventosManana.add(evento)
            }
            if (fechaEvento.isAfter(hoy) && fechaEvento.isBefore(finSemana)) {
                eventosSemana.add(evento)
            }
        }
        resultado["hoy"] = eventosHoy
        resultado["manana"] = eventosManana
        resultado["semana"] = eventosSemana
        return resultado
    }
    fun contarEventosEsteMes(): Int {
        val hoy = LocalDate.now()
        return repositorio.obtenerActividades()
            .filterIsInstance<Evento>()
            .count { evento ->
                val fechaEvento = LocalDate.parse(evento.fecha)
                fechaEvento.isAfter(hoy) && fechaEvento.isBefore(hoy.plusMonths(1))
            }
    }
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
