package es.prog2425.taskmanager.service

import es.prog2425.taskmanager.model.Tarea

interface IActividadServicioGestion {
    fun crearTarea(tarea: Tarea?) : Tarea
    fun crearTarea(descripcion : String, etiquetas: String)
    fun crearEvento(descripcion: String,fecha : String, ubicacion : String, etiquetas: String)
    fun listarActividades(): List<String>
    fun listarTareas(): List<Tarea>
    fun listarActividadesPorEtiqueta(etiqueta: String): List<String>
}