package es.prog2425.taskmanager.data

import es.prog2425.taskmanager.model.Actividad

interface IActividadRepository{
    fun agregarActividad(actividad: Actividad)
    fun obtenerActividades(): List<Actividad>
}
