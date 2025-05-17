package es.prog2425.taskmanager.model

import es.prog2425.taskmanager.service.ActividadServicios

object Filtro {

    lateinit var inputReader: InputReader

    object ConsoleInputReader : InputReader {
        override fun readLine() = kotlin.io.readLine()
    }


    fun mostrarResultados(actividades: List<*>) {
        if (actividades.isEmpty()) {
            println("\nNo se encontraron resultados.")
        } else {
            println("\n🔍 Resultados del filtro:")
            actividades.forEachIndexed { index, actividad ->
                when (actividad) {
                    is Actividad -> println("${index + 1}. ${actividad.obtenerDetalle()}")
                    else -> println("${index + 1}. $actividad")
                }
            }
        }
    }

    fun filtrarPorTipo(actividadServicios: ActividadServicios) {
        println("\n*** Filtrar por Tipo ***")
        println("1. Tareas")
        println("2. Eventos")
        print("Selecciona el tipo: ")
        when (inputReader.readLine()?.trim()) {
            "1" -> mostrarResultados(actividadServicios.filtrarPorTipo(Tarea::class.java))
            "2" -> mostrarResultados(actividadServicios.filtrarPorTipo(Evento::class.java))
            else -> println("❌ Opción no válida.")
        }
    }

    fun filtrarActividades(actividadServicios: ActividadServicios) {
        println("\n*** Filtrar Actividades ***")
        println("1. Por tipo (Tarea/Evento)")
        println("2. Por estado (ABIERTA, EN_PROGRESO, FINALIZADA)")
        println("3. Por etiqueta")
        println("4. Por usuario asignado")
        println("5. Por fecha (hoy, mañana, esta semana, este mes)")
        println("6. Volver al menú principal")
        print("Elige un criterio de filtrado: ")

        when (inputReader.readLine()?.trim()) {
            "1" -> filtrarPorTipo(actividadServicios)
            "2" -> filtrarPorEstado(actividadServicios)
            "3" -> filtrarPorEtiqueta(actividadServicios)
            "4" -> filtrarPorUsuario(actividadServicios)
            "5" -> filtrarPorFecha(actividadServicios)
            "6" -> return
            else -> println("❌ Opción no válida.")
        }
    }

    fun filtrarPorEstado(actividadServicios: ActividadServicios) {
        println("\n*** Filtrar por Estado ***")
        Estado.entries.forEachIndexed { index, estado -> println("${index + 1}. $estado") }
        print("Selecciona el estado (1-${Estado.entries.size}): ")
        inputReader.readLine()?.toIntOrNull()?.let { opcion ->
            if (opcion in 1..Estado.entries.size) {
                val estado = Estado.entries[opcion - 1]
                mostrarResultados(actividadServicios.filtrarPorEstado(estado))
            } else {
                println("❌ Opción no válida.")
            }
        }
    }

    fun filtrarPorEtiqueta(actividadServicios: ActividadServicios) {
        print("\nIngresa la etiqueta a filtrar: ")
        val etiqueta = inputReader.readLine()?.trim().orEmpty()
        if (etiqueta.isNotBlank()) {
            mostrarResultados(actividadServicios.filtrarPorEtiqueta(etiqueta))
        } else {
            println("❌ La etiqueta no puede estar vacía.")
        }
    }

    fun filtrarPorUsuario(actividadServicios: ActividadServicios) {
        print("\nIngresa el nombre del usuario: ")
        val nombre = inputReader.readLine()?.trim().orEmpty()
        if (nombre.isNotBlank()) {
            mostrarResultados(actividadServicios.filtrarPorUsuario(nombre))
        } else {
            println("❌ El nombre no puede estar vacío.")
        }
    }

    fun filtrarPorFecha(actividadServicios: ActividadServicios) {
        println("\n*** Filtrar por Fecha ***")
        println("1. Hoy")
        println("2. Mañana")
        println("3. Esta semana")
        println("4. Este mes")
        print("Selecciona el rango: ")
        when (inputReader.readLine()?.trim()) {
            "1" -> mostrarResultados(actividadServicios.filtrarPorFecha("hoy"))
            "2" -> mostrarResultados(actividadServicios.filtrarPorFecha("mañana"))
            "3" -> mostrarResultados(actividadServicios.filtrarPorFecha("semana"))
            "4" -> mostrarResultados(actividadServicios.filtrarPorFecha("mes"))
            else -> println("❌ Opción no válida.")
        }
    }

}