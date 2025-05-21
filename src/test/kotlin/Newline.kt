import es.prog2425.taskmanager.data.ActividadRepositorioEnMemoria
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.service.ActividadServicios
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MostrarHistorialDeTareaTest {

    private lateinit var actividadServicios: ActividadServicios

    @BeforeEach
    fun setUp() {

        actividadServicios = ActividadServicios(ActividadRepositorioEnMemoria())
    }

    @Test
    fun `mostrar historial devuelve lista vacía si no hay acciones`() {
        // Creo una tarea nueva sin historial
        val tarea = Tarea("Estudiar Kotlin")
        actividadServicios.crearTarea(tarea)

        // Obtengo la tarea para ver su historial
        val tareaObtenida = actividadServicios.obtenerTareaPorId(tarea.id)

        // Compruebo que el historial está vacío porque la tarea es nueva
        assertTrue(tareaObtenida?.obtenerHistorial()?.isEmpty() ?: false)
    }
}
