package es.prog2425.taskmanager.service

import es.prog2425.taskmanager.model.Actividad
import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.model.Usuario

interface IActividadServicioUsuarioTarea {
    fun asignarUsuarioATarea(tarea: Tarea, usuario: Usuario?) : Tarea
    fun cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado?) : Tarea
    fun obtenerTareasDeUsuario(usuario: Usuario, actividades: List<Actividad>): List<Tarea>
    fun obtenerTareaPorId(id: Int?): Tarea?
    fun obtenerOCrearUsuario(nombre: String): Usuario
}