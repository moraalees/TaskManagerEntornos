package es.prog2425.taskmanager

import es.prog2425.taskmanager.data.ActividadRepositorioEnMemoria
import es.prog2425.taskmanager.model.ActividadServicios
import es.prog2425.taskmanager.ui.ConsolaUI


fun main(){

    val repositorio = ActividadRepositorioEnMemoria()
    val servicio = ActividadServicios(repositorio)
    val ui = ConsolaUI(servicio)

     ui.mostrarMenu()

}


