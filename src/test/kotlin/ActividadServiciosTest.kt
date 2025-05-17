import es.prog2425.taskmanager.data.ActividadRepositorioEnMemoria
import es.prog2425.taskmanager.model.Actividad
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.service.ActividadServicios
import es.prog2425.taskmanager.ui.ConsolaUI
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ActividadServiciosTest {

    private val mockTarea1 = Tarea("Limpiar")
    private val mockTarea2 = Tarea("Organizar")
    private val tareas = listOf(mockTarea1, mockTarea2)
    private val consolaUI = ConsolaUI(ActividadServicios(ActividadRepositorioEnMemoria()))


        private lateinit var actividadServicios: ActividadServicios

        @BeforeEach
        fun setup() {
            // Reiniciamos el contador para que siempre empiece igual en cada test
            Actividad.ultimoId = 0
            actividadServicios = ActividadServicios(ActividadRepositorioEnMemoria())

            // Creamos tareas (no importa qué ID tengan, lo capturamos)
            actividadServicios.crearTarea(Tarea("2025-05-17"))
            actividadServicios.crearTarea(Tarea("2025-05-17"))
        }

        @Test
        fun `debe devolver tarea valida si el ID existe`() {
            val tareas = actividadServicios.listarTareas()
            val idTarea = tareas.first().id // Obtenemos el ID generado real

            val tarea = actividadServicios.obtenerTareaPorId(idTarea)
            assertNotNull(tarea)
            assertEquals(idTarea, tarea?.id)
        }



    @Test
    fun `debe devolver null si el ID no existe`() {
        val tarea = consolaUI.seleccionarTareaPorId(tareas, 99)
        assertNull(tarea)
    }

    @Test
    fun `debe devolver null si el ID es nulo`() {
        val tarea = consolaUI.seleccionarTareaPorId(tareas, null)
        assertNull(tarea)
    }

    @Test
    fun `debe devolver usuario si el nombre es válido`() {
        val servicios = ActividadServicios(ActividadRepositorioEnMemoria())
        val usuario = servicios.obtenerOCrearUsuario("Carlos")
        assertEquals("Carlos", usuario.nombre)
    }

    @Test
    fun `debe devolver null si el nombre está vacío`() {
        val usuario = consolaUI.obtenerUsuarioPorNombre("")
        assertNull(usuario)
    }
}
