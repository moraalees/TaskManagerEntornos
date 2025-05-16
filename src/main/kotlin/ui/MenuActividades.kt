package es.prog2425.taskmanager.ui

import es.prog2425.taskmanager.model.Actividad
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.service.IActividadService

class MenuActividades(val actividadServicios : IActividadService) : IMenuActividades {
    override fun crearActividad() {
        println("\n*** Crear nueva actividad ***")
        println("1.- Crear tarea")
        println("2.- Crear evento")
        println("3.- Volver al menú principal")
        print("Elige una opción: ")
        when (readLine()?.trim()) {
            "1" -> crearTarea()
            "2" -> crearEvento()
            "3" -> return
            else -> println("❌ Opción no válida.")
        }
    }
    override fun crearTarea() {
        println("\n*** Crear nueva tarea ***")
        print("Ingresa la descripción de la tarea: ")
        val descripcion = readLine()?.trim().orEmpty()
        print("Ingresa las etiquetas separadas por ';' (ejemplo: urgente;revisión): ")
        val etiquetas = readLine()?.trim().orEmpty()
        if (descripcion.isBlank()) {
            println("❌ Error: La descripción no puede estar vacía.")
            return
        }
        try {
            val tarea = Tarea.creaInstancia(descripcion, etiquetas)
            actividadServicios.crearTarea(tarea)
            println("✅ Tarea creada correctamente con ID: ${tarea.id}")
        } catch (e: Exception) {
            println("❌ Error al crear la tarea: ${e.message}")
        }
    }
    override fun crearEvento() {
        println("\n*** Crear nuevo evento ***")
        print("Ingresa la descripción del evento: ")
        val descripcion = readLine()?.trim().orEmpty()
        print("Ingresa la ubicación del evento: ")
        val ubicacion = readLine()?.trim().orEmpty()
        print("Ingresa las etiquetas separadas por ';' (ejemplo: reunion;importante): ")
        val etiquetas = readLine()?.trim().orEmpty()
        if (descripcion.isBlank() || ubicacion.isBlank()) {
            println("❌ Error: Descripción y ubicación son campos obligatorios.")
            return
        }
        val fecha = Actividad.obtenerFechaActual()
        try {
            actividadServicios.crearEvento(descripcion, fecha, ubicacion, etiquetas)
            println("✅ Evento creado correctamente para el $fecha")
        } catch (e: Exception) {
            println("❌ Error al crear el evento: ${e.message}")
        }
    }
    override fun listarActividades() {
        println("\n*** Listado de actividades ***")
        val actividades = actividadServicios.listarActividades()
        if (actividades.isEmpty()) {
            println("No hay actividades registradas.")
        } else {
            actividades.forEachIndexed { index, actividad ->
                println("${index + 1}. $actividad")
            }
        }
    }
    override fun buscarActividadesPorEtiqueta() {
        println("\n*** Buscar actividades por etiqueta ***")
        print("Ingresa la etiqueta a buscar: ")
        val etiqueta = readLine()?.trim().orEmpty()
        if (etiqueta.isBlank()) {
            println("❌ Debes ingresar una etiqueta para buscar.")
            return
        }
        val resultados = actividadServicios.listarActividadesPorEtiqueta(etiqueta)
        if (resultados.isEmpty()) {
            println("No se encontraron actividades con la etiqueta '$etiqueta'")
        } else {
            println("\n🔍 Resultados para '$etiqueta':")
            resultados.forEachIndexed { index, actividad ->
                println("${index + 1}. $actividad")
            }
        }
    }
}