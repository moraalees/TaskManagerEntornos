package es.prog2425.taskmanager.model

class Tarea private constructor(descripcion: String) : Actividad(descripcion) {

    var estado: Estado = Estado.ABIERTA
        private set

    var asignadoA: Usuario? = null
        private set

    private val subtareas: MutableList<Tarea> = mutableListOf()

    private val historial: MutableList<RegistroHistorial> = mutableListOf()

    fun agregarSubtarea(subtarea: Tarea) {
        subtareas.add(subtarea)
        agregarHistorial("Se agregó una subtarea con ID ${subtarea.id}")
    }

    fun obtenerSubtareas(): List<Tarea> = subtareas.toList()

    fun tieneSubtareasAbiertas(): Boolean {
        return subtareas.any { it.estado != Estado.FINALIZADA }
    }

    fun cambiarEstado(nuevoEstado: Estado) {
        if (nuevoEstado == Estado.FINALIZADA && tieneSubtareasAbiertas()) {
            agregarHistorial("Intento de cerrar tarea fallido: subtareas abiertas")
            throw IllegalStateException("No se puede cerrar la tarea: tiene subtareas abiertas")
        }

        estado = nuevoEstado
        agregarHistorial("Estado cambiado a $estado")
    }

    fun asignarUsuario(usuario: Usuario) {
        asignadoA = usuario
        agregarHistorial("Tarea asignada a ${usuario.nombre}")
    }

    fun obtenerHistorial(): List<RegistroHistorial> = historial.toList()

    private fun agregarHistorial(descripcion: String) {
        historial.add(RegistroHistorial(descripcion = descripcion))
    }

    companion object {
        fun creaInstancia(descripcion: String, etiquetas: String = ""): Tarea {
            val tarea = Tarea(descripcion)
            if (etiquetas.isNotBlank()) {
                tarea.agregarEtiquetas(etiquetas)
                tarea.agregarHistorial("Etiquetas agregadas: $etiquetas")
            }
            tarea.agregarHistorial("Tarea creada con estado ${tarea.estado}")
            return tarea
        }
    }

    override fun obtenerDetalle(): String {
        val asignado = asignadoA?.nombre ?: "Sin asignar"
        val etiquetasStr = if (obtenerEtiquetas().isNotEmpty()) {
            " - Etiquetas: ${obtenerEtiquetas().joinToString(", ")}"
        } else ""
        return "Tarea $id - $descripcion [Estado: $estado, Asignado a: $asignado]$etiquetasStr"
    }
}
