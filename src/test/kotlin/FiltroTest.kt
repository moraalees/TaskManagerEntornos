import es.prog2425.taskmanager.model.Filtro
import es.prog2425.taskmanager.model.InputReader
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.service.ActividadServicios
import org.junit.jupiter.api.*
import io.mockk.*

class FiltroTest {

    private lateinit var actividadServicios: ActividadServicios
    private lateinit var inputReaderMock: InputReader

    @BeforeEach
    fun setUp() {
        actividadServicios = mockk()
        inputReaderMock = mockk()
        Filtro.inputReader = inputReaderMock
    }

    @Test
    fun `filtrarPorTipo con opción 1 muestra tareas`() {
        val listaTareas = listOf(
            Tarea("Tarea 1"),
            Tarea("Tarea 2")
        )
        every { inputReaderMock.readLine() } returns "1"
        every { actividadServicios.filtrarPorTipo(Tarea::class.java) } returns listaTareas

        // Capturar salida
        val outputStream = java.io.ByteArrayOutputStream()
        System.setOut(java.io.PrintStream(outputStream))

        Filtro.filtrarPorTipo(actividadServicios)

        val output = outputStream.toString()
        Assertions.assertTrue(output.contains("Tarea 1"))
        Assertions.assertTrue(output.contains("Tarea 2"))
    }

    @Test
    fun `filtrarPorTipo con opción inválida muestra error`() {
        every { inputReaderMock.readLine() } returns "5"

        val outputStream = java.io.ByteArrayOutputStream()
        System.setOut(java.io.PrintStream(outputStream))

        Filtro.filtrarPorTipo(actividadServicios)

        val output = outputStream.toString()
        Assertions.assertTrue(output.contains("❌ Opción no válida."))
    }
}

