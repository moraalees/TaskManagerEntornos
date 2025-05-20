import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.IOException

class RefactorizacionTest {
    @Test
    fun testCrearEventoAntesRefactor() {
        var mensaje = ""
        try {
            throw IOException("Error al guardar evento")
        } catch (e: Exception) { // demasiado genérico
            mensaje = "Error al crear el evento: ${e.message}"
        }
        assertEquals("Error al crear el evento: Error al guardar evento", mensaje)
    }

    @Test
    fun testCrearEventoDespuesRefactor() {
        var mensaje = ""
        try {
            throw IOException("Error al guardar evento")
        } catch (e: IOException) {
            mensaje = "Error al crear el evento: ${e.message}"
        }
        assertEquals("Error al crear el evento: Error al guardar evento", mensaje)
    }


    class MockTarea {
        var estado: String = "PENDIENTE"
        fun tieneSubtareasAbiertas(): Boolean = true
        fun agregarHistorial(msg: String) {}

        fun cambiarEstado(nuevoEstado: String) {
            check(!(nuevoEstado == "FINALIZADA" && tieneSubtareasAbiertas())) {
                "No se puede cerrar la tarea: tiene subtareas abiertas"
            }
            estado = nuevoEstado
            agregarHistorial("Estado cambiado a $estado")
        }
    }

    @Test
    fun testCambiarEstadoAntesRefactor() {
        val exception = assertThrows(IllegalStateException::class.java) {
            if (true) {
                throw IllegalStateException("No se puede cerrar la tarea: tiene subtareas abiertas")
            }
        }
        assertEquals("No se puede cerrar la tarea: tiene subtareas abiertas", exception.message)
    }

    @Test
    fun testCambiarEstadoDespuesRefactor() {
        val tarea = MockTarea()
        val exception = assertThrows(IllegalStateException::class.java) {
            tarea.cambiarEstado("FINALIZADA")
        }
        assertEquals("No se puede cerrar la tarea: tiene subtareas abiertas", exception.message)
    }


    @Test
    fun testSimplifyConditionalAntes() {
        val entrada = 5
        val resultado = if (entrada !in 1..3) {
            "Error: fuera de rango"
        } else {
            "Estado válido"
        }
        assertEquals("Error: fuera de rango", resultado)
    }

    @Test
    fun testSimplifyConditionalDespues() {
        val entrada = 2
        val resultado = runCatching {
            require(entrada in 1..3) { "Error: fuera de rango" }
            "Estado válido"
        }.getOrElse { it.message!! }

        assertEquals("Estado válido", resultado)
    }
}