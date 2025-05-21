package es.prog2425.taskmanager.data

import es.prog2425.taskmanager.model.Actividad

class ActividadRepositorioEnMemoria : IActividadRepository{
    /**
     * Lista mutable que almacena las actividades agregadas.
     */
    private val actividades = mutableListOf<Actividad>()

    /**
     * Agrega una nueva [Actividad] al repositorio.
     *
     * @param actividad la actividad que se desea añadir.
     */
    override fun agregarActividad(actividad: Actividad) {
        actividades.add(actividad)
    }

    /**
     * Retorna la lista de actividades almacenadas en memoria.
     *
     * @return lista de actividades.
     */
    override fun obtenerActividades(): List<Actividad> = actividades
}