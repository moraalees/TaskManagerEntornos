package es.prog2425.taskmanager.model

interface IActividadService {
    fun crearTarea(tarea: Tarea)
    fun crearTarea(descripcion : String, etiquetas: String)
    fun crearEvento(descripcion: String,fecha : String, ubicacion : String, etiquetas: String)
    fun listarActividades(): List<String>
    fun listarTareas(): List<Tarea>
    fun listarActividadesPorEtiqueta(etiqueta: String): List<String>
    fun asignarUsuarioATarea(tarea: Tarea, usuario: Usuario)
    fun cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado)
    fun obtenerTareasDeUsuario(usuario: Usuario, actividades: List<Actividad>): List<Tarea>
    fun obtenerTareaPorId(id: Int?): Tarea?
    fun obtenerOCrearUsuario(nombre: String): Usuario
    fun filtrarPorTipo(tipo: Class<out Actividad>): List<Actividad>
    fun filtrarPorEstado(estado: Estado): List<Tarea>
    fun filtrarPorEtiqueta(etiqueta: String): List<Actividad>
    fun filtrarPorUsuario(nombreUsuario: String): List<Tarea>
    fun filtrarPorFecha(rango: String): List<Evento>
}