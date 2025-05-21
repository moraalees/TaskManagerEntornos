package es.prog2425.taskmanager.model

import es.prog2425.taskmanager.service.ActividadServicios

object Filtro {

    /**
     * Muestra los resultados del filtrado en consola.
     *
     * @param actividades Lista de actividades resultantes del filtro.
     */
    private fun mostrarResultados(actividades: List<*>) {
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

    /**
     * Despliega un menú interactivo para seleccionar criterios de filtrado y aplicar filtros
     * a las actividades.
     *
     * @param actividadServicios Proveedor de actividades a filtrar.
     */
    fun filtrarActividades(actividadServicios: ActividadServicios) {
        println("\n*** Filtrar Actividades ***")
        println("1. Por tipo (Tarea/Evento)")
        println("2. Por estado (ABIERTA, EN_PROGRESO, FINALIZADA)")
        println("3. Por etiqueta")
        println("4. Por usuario asignado")
        println("5. Por fecha (hoy, mañana, esta semana, este mes)")
        println("6. Volver al menú principal")
        print("Elige un criterio de filtrado: ")

        when (readLine()?.trim()) {
            "1" -> filtrarPorTipo(actividadServicios)
            "2" -> filtrarPorEstado(actividadServicios)
            "3" -> filtrarPorEtiqueta(actividadServicios)
            "4" -> filtrarPorUsuario(actividadServicios)
            "5" -> filtrarPorFecha(actividadServicios)
            "6" -> return
            else -> println("❌ Opción no válida.")
        }
    }

    /**
     * Filtra las actividades por tipo (Tarea o Evento).
     *
     * Funciona como un submenú que decide si filtrar o no según la entrada del usuario
     */
    private fun filtrarPorTipo(actividadServicios: ActividadServicios) {
        println("\n*** Filtrar por Tipo ***")
        println("1. Tareas")
        println("2. Eventos")
        print("Selecciona el tipo: ")
        when (readLine()?.trim()) {
            "1" -> mostrarResultados(actividadServicios.filtrarPorTipo(Tarea::class.java))
            "2" -> mostrarResultados(actividadServicios.filtrarPorTipo(Evento::class.java))
            else -> println("❌ Opción no válida.")
        }
    }

    /**
     * Filtra las actividades por su estado actual.
     *
     * El usuario debe escoger entre el tamaño de una lista y si selecciona correctamente
     * se realiza el filtro.
     */
    private fun filtrarPorEstado(actividadServicios: ActividadServicios) {
        println("\n*** Filtrar por Estado ***")
        Estado.entries.forEachIndexed { index, estado -> println("${index + 1}. $estado") }
        print("Selecciona el estado (1-${Estado.entries.size}): ")
        readLine()?.toIntOrNull()?.let { opcion ->
            if (opcion in 1..Estado.entries.size) {
                val estado = Estado.entries[opcion - 1]
                mostrarResultados(actividadServicios.filtrarPorEstado(estado))
            } else {
                println("❌ Opción no válida.")
            }
        }
    }

    /**
     * Filtra actividades según una etiqueta proporcionada por el usuario.
     *
     * Valida si la etiqueta que se le pasa está vacía o no para entonces filtrar
     */
    private fun filtrarPorEtiqueta(actividadServicios: ActividadServicios) {
        print("\nIngresa la etiqueta a filtrar: ")
        val etiqueta = readLine()?.trim().orEmpty()
        if (etiqueta.isNotBlank()) {
            mostrarResultados(actividadServicios.filtrarPorEtiqueta(etiqueta))
        } else {
            println("❌ La etiqueta no puede estar vacía.")
        }
    }

    /**
     * Filtra actividades por el nombre del usuario asignado.
     *
     * Valida si el nombre que se ingresa está vacío o no para luego realizar el filtro
     */
    private fun filtrarPorUsuario(actividadServicios: ActividadServicios) {
        print("\nIngresa el nombre del usuario: ")
        val nombre = readLine()?.trim().orEmpty()
        if (nombre.isNotBlank()) {
            mostrarResultados(actividadServicios.filtrarPorUsuario(nombre))
        } else {
            println("❌ El nombre no puede estar vacío.")
        }
    }

    /**
     * Filtra actividades según una fecha o rango de fechas.
     *
     * Funciona como un submenú que hace algo específico según la entrada del usuario.
     */
    private fun filtrarPorFecha(actividadServicios: ActividadServicios) {
        println("\n*** Filtrar por Fecha ***")
        println("1. Hoy")
        println("2. Mañana")
        println("3. Esta semana")
        println("4. Este mes")
        print("Selecciona el rango: ")
        when (readLine()?.trim()) {
            "1" -> mostrarResultados(actividadServicios.filtrarPorFecha("hoy"))
            "2" -> mostrarResultados(actividadServicios.filtrarPorFecha("mañana"))
            "3" -> mostrarResultados(actividadServicios.filtrarPorFecha("semana"))
            "4" -> mostrarResultados(actividadServicios.filtrarPorFecha("mes"))
            else -> println("❌ Opción no válida.")
        }
    }
}