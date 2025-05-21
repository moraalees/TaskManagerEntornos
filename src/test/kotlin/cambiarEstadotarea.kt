import es.prog2425.taskmanager.data.ActividadRepositorioEnMemoria
import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.service.ActividadServicios
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class CambiarEstadoTareaTest {

    private lateinit var actividadServicios: ActividadServicios

    @BeforeEach
    fun setUp() {

        actividadServicios = ActividadServicios(ActividadRepositorioEnMemoria())
    }

    @Test
    fun `cambia el estado correctamente si todo es válido`() {
        // Creo una tarea nueva
        val tarea = Tarea("Hacer ejercicio")
        actividadServicios.crearTarea(tarea)

        // Simulo que quiero cambiarle el estado
        val nuevoEstado = Estado.EN_PROGRESO
        actividadServicios.cambiarEstadoTarea(tarea, nuevoEstado)

        // Compruebo que el estado cambió bien
        assertEquals(nuevoEstado, tarea.estado)
    }
}
