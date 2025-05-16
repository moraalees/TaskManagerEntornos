package es.prog2425.taskmanager.ui

import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.service.ActividadServicios

class MenuMostrar(private val actividadServicios: ActividadServicios) : IMenuMostrar {
    override fun asignarTareaAUsuario() {
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

    override fun cambiarEstadoTarea() {
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
    override fun gestionarSubtareas() {
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
    override fun mostrarDashboard() {
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

    override fun mostrarHistorialDeTarea() {
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