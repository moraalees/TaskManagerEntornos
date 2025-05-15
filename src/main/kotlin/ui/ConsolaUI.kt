package es.prog2425.taskmanager.ui

import es.prog2425.taskmanager.model.*
import es.prog2425.taskmanager.service.ActividadServicios

class ConsolaUI(private val actividadServicios: ActividadServicios) : IEntradaSalida {

    override fun mostrarMenu() {
        while (true) {
            println("\n*** Gestor de Tareas ***")
            println("1.- Crear actividad")
            println("2.- Listar todas las actividades")
            println("3.- Buscar actividades por etiqueta")
            println("4.- Asignar tarea a usuario")
            println("5.- Cambiar estado de una tarea")
            println("6.- Gestionar subtareas")
            println("7.- Mostrar Dashboard")
            println("8.- Filtrar actividades")
            println("9.- Mostrar historial de una tarea")
            println("10.- Salir")
            print("Introduce la operación que quieras realizar: ")

            when (readLine()?.trim()) {
                "1" -> crearActividad()
                "2" -> listarActividades()
                "3" -> buscarActividadesPorEtiqueta()
                "4" -> asignarTareaAUsuario()
                "5" -> cambiarEstadoTarea()
                "6" -> gestionarSubtareas()
                "7" -> mostrarDashboard()
                "8" -> Filtro.filtrarActividades(actividadServicios)
                "9" -> mostrarHistorialDeTarea()
                "10" -> {
                    println("Saliendo...")
                    return
                }
                else -> println("❌ Opción no válida. Por favor, selecciona una opción del 1 al 10.")
            }
        }
    }

    private fun crearActividad() {
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
    private fun crearTarea() {
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
    private fun crearEvento() {
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
    private fun buscarActividadesPorEtiqueta() {
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
    private fun asignarTareaAUsuario() {
        println("\n*** Asignar tarea a usuario ***")
        val tareas = actividadServicios.listarTareas()
        if (tareas.isEmpty()) {
            println("No hay tareas disponibles para asignar.")
            return
        }
        println("Tareas disponibles:")
        tareas.forEach { tarea ->
            println(tarea.obtenerDetalle())
        }
        print("Ingresa el ID de la tarea a asignar: ")
        val idTarea = readLine()?.toIntOrNull()
        if (idTarea == null) {
            println("❌ Error: Debes ingresar un número válido.")
            return
        }
        val tarea = tareas.find { it.id == idTarea }
        if (tarea == null) {
            println("❌ Error: No existe una TAREA con ID $idTarea (¿Es un evento?)")
            return
        }
        print("Ingresa el nombre del usuario: ")
        val nombreUsuario = readLine()?.trim().orEmpty()
        if (nombreUsuario.isBlank()) {
            println("❌ Error: El nombre no puede estar vacío.")
            return
        }
        try {
            val usuario = actividadServicios.obtenerOCrearUsuario(nombreUsuario)
            actividadServicios.asignarUsuarioATarea(tarea, usuario)
            println("✅ Tarea '$idTarea - ${tarea.descripcion}' asignada a ${usuario.nombre}")
        } catch (e: Exception) {
            println("❌ Error al asignar: ${e.message}")
        }
    }
    private fun cambiarEstadoTarea() {
        println("\n*** Cambiar estado de tarea ***")
        val tareas = actividadServicios.listarTareas()
        if (tareas.isEmpty()) {
            println("No hay tareas disponibles para actualizar.")
            return
        }
        println("Tareas disponibles:")
        tareas.forEach { tarea ->
            println(tarea.obtenerDetalle())
        }
        print("Ingresa el ID de la tarea a actualizar: ")
        val idTarea = readLine()?.toIntOrNull()
        if (idTarea == null) {
            println("❌ Error: Debes ingresar un número válido.")
            return
        }
        val tarea = actividadServicios.obtenerTareaPorId(idTarea)
        if (tarea == null) {
            println("❌ Error: No existe una TAREA con ID $idTarea (¿Quizás es un evento?)")
            return
        }
        println("\nEstados disponibles:")
        Estado.entries.forEachIndexed { index, estado ->
            println("${index + 1}. $estado")
        }
        print("Selecciona el nuevo estado (1-${Estado.entries.size}): ")
        when (val opcion = readLine()?.toIntOrNull()) {
            null -> println("❌ Error: Ingresa un número válido.")
            !in 1..Estado.entries.size -> println("❌ Error: El número debe estar entre 1 y ${Estado.entries.size}")
            else -> {
                val nuevoEstado = Estado.entries[opcion - 1]
                actividadServicios.cambiarEstadoTarea(tarea, nuevoEstado)
                println("✅ Estado de la tarea $idTarea actualizado a $nuevoEstado")
            }
        }
    }
    private fun gestionarSubtareas() {
        println("\n*** Gestión de Subtareas ***")
        val tareas = actividadServicios.listarTareas()
        tareas.forEach { println("${it.id}: ${it.descripcion} (${it.obtenerSubtareas().size} subtareas)") }
        print("Selecciona el ID de la tarea madre: ")
        val idMadre = readLine()?.toIntOrNull() ?: run {
            println("ID inválido")
            return
        }
        val tareaMadre = tareas.find { it.id == idMadre } ?: run {
            println("Tarea no encontrada")
            return
        }
        println("\n1. Agregar subtarea")
        println("2. Listar subtareas")
        when (readLine()) {
            "1" -> {
                print("Descripción de la subtarea: ")
                val desc = readLine()?.takeIf { it.isNotBlank() } ?: run {
                    println("Descripción vacía")
                    return
                }
                val subtarea = Tarea.creaInstancia(desc)
                tareaMadre.agregarSubtarea(subtarea)
                println("✅ Subtarea agregada")
            }
            "2" -> {
                tareaMadre.obtenerSubtareas().forEach { println(it.obtenerDetalle()) }
            }
        }
    }
    private fun mostrarDashboard() {
        val resumen = actividadServicios.obtenerResumenTareas()
        val eventos = actividadServicios.obtenerEventosProximos()
        println("\n*** Dashboard ***")
        println("\nResumen de tareas:")
        println("- Total: ${resumen["totalTareas"]}")
        println("- Tareas madre: ${resumen["tareasMadre"]}")
        println("- Por estado: ${resumen["porEstado"]}")
        println("\n🗓 Eventos próximos:")
        println("- Hoy: ${eventos["hoy"]?.size ?: 0}")
        println("- Mañana: ${eventos["manana"]?.size ?: 0}")
        println("- Esta semana: ${eventos["semana"]?.size ?: 0}")
        println("- Este mes: ${actividadServicios.contarEventosEsteMes()}")
    }

    private fun mostrarHistorialDeTarea() {
        print("🔍 Introduce el ID de la tarea: ")
        val id = readLine()?.trim()?.toIntOrNull()

        if (id == null) {
            println("⚠️ ID inválido.")
            return
        }

        val tarea = actividadServicios.obtenerTareaPorId(id)

        if (tarea == null) {
            println("❌ No se encontró una tarea con ese ID.")
            return
        }

        println("\n📜 Historial de la tarea '${tarea.descripcion}':")
        val historial = tarea.obtenerHistorial()

        if (historial.isEmpty()) {
            println("No hay acciones registradas todavía.")
        } else {
            historial.forEach {
                println(" - [${it.fecha}] ${it.descripcion}")
            }
        }
    }

}