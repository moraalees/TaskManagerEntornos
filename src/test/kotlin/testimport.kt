import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.service.ActividadServicios
import es.prog2425.taskmanager.data.ActividadRepositorioEnMemoria
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ImportEspecificoTest {

    private lateinit var actividadServicios: ActividadServicios

    @BeforeEach
    fun setup() {
        actividadServicios = ActividadServicios(ActividadRepositorioEnMemoria())
    }

    @Test
    fun `crear tarea y cambiar estado usando enum Estado`() {
        val tarea = Tarea("Estudiar enums")
        actividadServicios.crearTarea(tarea)

        // Cambiar estado a FINALIZADA usando el enum importado
        actividadServicios.cambiarEstadoTarea(tarea, Estado.FINALIZADA)

        val tareaActualizada = actividadServicios.obtenerTareaPorId(tarea.id)
        assertEquals(Estado.FINALIZADA, tareaActualizada?.estado)
    }
}
