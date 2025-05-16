package es.prog2425.taskmanager.service

import es.prog2425.taskmanager.data.IActividadRepository
import es.prog2425.taskmanager.model.Actividad
import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.model.Usuario

class ActividadServicioUsuarioTarea(val repositorio : IActividadRepository) : IActividadServicioUsuarioTarea {
    override fun asignarUsuarioATarea(tarea: Tarea, usuario: Usuario?): Tarea {
        if (usuario == null) {
            throw IllegalArgumentException("El usuario no puede ser nulo")
        }
        tarea.asignarUsuario(usuario)  // Asigna el usuario a la tarea
        repositorio.agregarActividad(tarea)  // Guarda la tarea con el usuario asignado
        return tarea
    }

    override fun cambiarEstadoTarea(tarea: Tarea, nuevoEstado: Estado?): Tarea {
        if (nuevoEstado == null) {
            throw IllegalArgumentException("El estado no puede ser nulo")
        }
        tarea.cambiarEstado(nuevoEstado)  // Cambia el estado de la tarea
        repositorio.agregarActividad(tarea)  // Guarda la tarea con el nuevo estado
        return tarea
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
}