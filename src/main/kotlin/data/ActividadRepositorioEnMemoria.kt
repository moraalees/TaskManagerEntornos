package es.prog2425.taskmanager.data

import es.prog2425.taskmanager.model.Actividad

class ActividadRepositorioEnMemoria : IActividadRepository{
    private val actividades = mutableListOf<Actividad>()


    override fun agregarActividad(actividad: Actividad) {
        actividades.add(actividad)
    }


    override fun obtenerActividades(): List<Actividad> = actividades
}