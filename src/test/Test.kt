import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.test.assertEquals
import java.io.FileNotFoundException

class Test {

    @Test
    fun verificarManejoErroresAntesRefactor() {
        var resultado = ""
        try {
            throw FileNotFoundException("Archivo no disponible")
        } catch (e: Exception) { // uso genérico
            resultado = "Se produjo un error: ${e.message}"
        }
        assertEquals("Se produjo un error: Archivo no disponible", resultado)
    }

    @Test
    fun verificarManejoErroresDespuesRefactor() {
        var resultado = ""
        try {
            throw FileNotFoundException("Archivo no disponible")
        } catch (e: FileNotFoundException) {
            resultado = "Se produjo un error: ${e.message}"
        }
        assertEquals("Se produjo un error: Archivo no disponible", resultado)
    }

    class SimuladorTarea {
        var estadoActual = "ABIERTA"
        fun subtareasPendientes(): Boolean = true
        fun logHistorial(cambio: String) {}

        fun actualizarEstado(nuevoEstado: String) {
            check(!(nuevoEstado == "CERRADA" && subtareasPendientes())) {
                "No es posible cerrar la tarea: subtareas activas"
            }
            estadoActual = nuevoEstado
            logHistorial("Nuevo estado: $estadoActual")
        }
    }

    @Test
    fun validarEstadoAntesDeRefactor() {
        val error = assertThrows(IllegalStateException::class.java) {
            if (true) {
                throw IllegalStateException("No es posible cerrar la tarea: subtareas activas")
            }
        }
        assertEquals("No es posible cerrar la tarea: subtareas activas", error.message)
    }

    @Test
    fun validarEstadoDespuesDeRefactor() {
        val tarea = SimuladorTarea()
        val error = assertThrows(IllegalStateException::class.java) {
            tarea.actualizarEstado("CERRADA")
        }
        assertEquals("No es posible cerrar la tarea: subtareas activas", error.message)
    }

    @Test
    fun validarCondicionComplejaAntes() {
        val valor = 10
        val mensaje = if (valor < 1 || valor > 5) {
            "Número inválido"
        } else {
            "Número permitido"
        }
        assertEquals("Número inválido", mensaje)
    }

    @Test
    fun validarCondicionSimplificadaDespues() {
        val valor = 3
        val resultado = runCatching {
            require(valor in 1..5) { "Número inválido" }
            "Número permitido"
        }.getOrElse { it.message ?: "Error desconocido" }

        assertEquals("Número permitido", resultado)
    }
}
