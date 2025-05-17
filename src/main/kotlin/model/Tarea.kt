package es.prog2425.taskmanager.model

/**
 * Representa una tarea dentro del sistema, que puede tener subtareas,
 * un estado, un usuario asignado, etiquetas y un historial de cambios.
 *
 * @property estado Estado actual de la tarea (por defecto ABIERTA).
 * @property asignadoA Usuario asignado a la tarea, puede ser nulo si no está asignada.
 */
class Tarea(descripcion: String) : Actividad(descripcion) {

    var estado: Estado = Estado.ABIERTA
        private set

    var asignadoA: Usuario? = null
        private set

    private val subtareas: MutableList<Tarea> = mutableListOf()

    private val historial: MutableList<RegistroHistorial> = mutableListOf()

    /**
     * Agrega una subtarea a esta tarea y registra la acción en el historial.
     *
     * @param subtarea La subtarea que se va a agregar.
     */
    fun agregarSubtarea(subtarea: Tarea) {
        subtareas.add(subtarea)
        agregarHistorial("Se agregó una subtarea con ID ${subtarea.id}")
    }

    /**
     * Devuelve una lista inmutable con las subtareas asociadas a esta tarea.
     *
     * @return Lista de subtareas.
     */
    fun obtenerSubtareas(): List<Tarea> = subtareas.toList()

    /**
     * Indica si alguna de las subtareas está en un estado diferente a FINALIZADA.
     *
     * @return `true` si hay subtareas abiertas, `false` en caso contrario.
     */
    fun tieneSubtareasAbiertas(): Boolean {
        return subtareas.any { it.estado != Estado.FINALIZADA }
    }

    /**
     * Cambia el estado de la tarea.
     *
     * @param nuevoEstado El nuevo estado que se desea asignar. No puede ser nulo.
     * @throws IllegalStateException si se intenta finalizar la tarea y tiene subtareas abiertas.
     * @throws IllegalArgumentException si `nuevoEstado` es nulo.
     */
    fun cambiarEstado(nuevoEstado: Estado?) {
        requireNotNull(nuevoEstado) { "El estado no puede ser nulo" }
        if (nuevoEstado == Estado.FINALIZADA && tieneSubtareasAbiertas()) {
            agregarHistorial("Intento de cerrar tarea fallido: subtareas abiertas")
            throw IllegalStateException("No se puede cerrar la tarea: tiene subtareas abiertas")
        }

        estado = nuevoEstado
        agregarHistorial("Estado cambiado a $estado")
    }

    /**
     * Asigna un usuario responsable a esta tarea.
     *
     * @param usuario Usuario que se asignará. No puede ser nulo.
     * @throws IllegalArgumentException si `usuario` es nulo.
     */
    fun asignarUsuario(usuario: Usuario?) {
        requireNotNull(usuario) { "El usuario no puede ser nulo" }
        asignadoA = usuario
        agregarHistorial("Tarea asignada a ${usuario.nombre}")
    }

    /**
     * Devuelve una lista inmutable con los registros del historial de la tarea.
     *
     * @return Lista de registros de historial.
     */
    fun obtenerHistorial(): List<RegistroHistorial> = historial.toList()

    /**
     * Añade una entrada al historial de la tarea con la descripción dada.
     *
     * @param descripcion Texto descriptivo del evento a registrar.
     */
    private fun agregarHistorial(descripcion: String) {
        historial.add(RegistroHistorial(descripcion = descripcion))
    }

    companion object {
        /**
         * Fabrica una nueva instancia de [Tarea] con una descripción y etiquetas opcionales.
         * Si se proporcionan etiquetas, estas se agregan y se registra en el historial.
         *
         * @param descripcion Descripción de la tarea.
         * @param etiquetas Etiquetas separadas por ';', opcionales.
         * @return Nueva instancia de [Tarea].
         */
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

    /**
     * Devuelve una cadena descriptiva con los detalles de la tarea,
     * incluyendo id, descripción, estado, usuario asignado y etiquetas.
     *
     * @return Detalle textual de la tarea.
     */
    override fun obtenerDetalle(): String {
        val asignado = asignadoA?.nombre ?: "Sin asignar"
        val etiquetasStr = if (obtenerEtiquetas().isNotEmpty()) {
            " - Etiquetas: ${obtenerEtiquetas().joinToString(", ")}"
        } else ""
        return "Tarea $id - $descripcion [Estado: $estado, Asignado a: $asignado]$etiquetasStr"
    }
}

