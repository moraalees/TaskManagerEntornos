import es.prog2425.taskmanager.service.ActividadServicios
import es.prog2425.taskmanager.model.Tarea
import org.junit.jupiter.api.*
import io.mockk.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import es.prog2425.taskmanager.ui.ConsolaUI

class ConsolaUITest {

    private lateinit var actividadServicios: ActividadServicios

    private val outputStream = ByteArrayOutputStream()
    private val originalOut = System.out


    @BeforeEach
    fun setUp() {
        actividadServicios = mockk()
        System.setOut(PrintStream(outputStream))
    }

    @AfterEach
    fun tearDown() {
        clearMocks(actividadServicios)
        System.setOut(originalOut)
        outputStream.reset()
    }

    private fun mockReadLines(vararg inputs: String) {
        var index = 0
        mockkStatic("kotlin.io.ConsoleKt") // para mockear readLine()
        every { readLine() } answers {
            if (index < inputs.size) inputs[index++] else null
        }
    }

    @Test
    fun `crearTarea crea tarea correctamente`() {
        mockReadLines("Descripción de prueba", "urgente;importante")

        val tareaMock = mockk<Tarea>(relaxed = true)
        every { tareaMock.id } returns 42

        // Mockear el companion object
        mockkObject(Tarea.Companion)
        every { Tarea.creaInstancia(any(), any()) } returns tareaMock
        every { actividadServicios.crearTarea(any()) } returns tareaMock

        val consolaUI = ConsolaUI(actividadServicios)
        consolaUI.crearTarea()

        val output = outputStream.toString()
        Assertions.assertTrue(output.contains("✅ Tarea creada correctamente con ID: 42"))

        unmockkObject(Tarea.Companion)
    }



    @Test
    fun `crearTarea con descripcion vacia muestra error y no crea tarea`() {
        mockReadLines("", "") // descripción vacía

        val consolaUI = ConsolaUI(actividadServicios)
        consolaUI.crearTarea()

        val output = outputStream.toString()
        Assertions.assertTrue(output.contains("❌ Error: La descripción no puede estar vacía."))
        verify(exactly = 0) { actividadServicios.crearTarea(any()) }
    }
    @Test
    fun `crearTarea captura IllegalArgumentException`() {
        // Redirigir salida para capturar consola
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        // Mock entrada del usuario
        mockReadLines("Descripción válida", "etiquetas")

        // Mockear el companion object correctamente
        mockkObject(Tarea) // Esto es suficiente si creaInstancia está en el companion object

        // Simular excepción
        every { Tarea.creaInstancia("Descripción válida", "etiquetas") } throws IllegalArgumentException("Error de validación")

        // Mock del servicio
        val actividadServicios = mockk<ActividadServicios>(relaxed = true)

        // Ejecutar
        val consolaUI = ConsolaUI(actividadServicios)
        consolaUI.crearTarea()

        val output = outputStream.toString()
        println("🔎 Output capturado:\n$output")

        Assertions.assertTrue(output.contains("❌ Error de validación: Error de validación"))

        // Verificar llamada
        verify { Tarea.creaInstancia("Descripción válida", "etiquetas") }

        unmockkObject(Tarea)
    }





}
