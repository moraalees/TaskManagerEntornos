package es.prog2425.taskmanager.service

import es.prog2425.taskmanager.model.Actividad
import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.model.Evento
import es.prog2425.taskmanager.model.Tarea

interface IActividadServicioFiltro {
    fun filtrarPorTipo(tipo: Class<out Actividad>): List<Actividad>
    fun filtrarPorEstado(estado: Estado): List<Tarea>
    fun filtrarPorEtiqueta(etiqueta: String): List<Actividad>
    fun filtrarPorUsuario(nombreUsuario: String): List<Tarea>
    fun filtrarPorFecha(rango: String): List<Evento>
    fun listarTareas(): List<Tarea>
}