import es.prog2425.taskmanager.data.IActividadRepository
import es.prog2425.taskmanager.model.Actividad
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.*
import es.prog2425.taskmanager.model.Evento
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.model.Usuario
import es.prog2425.taskmanager.service.ActividadServicios

class ActividadServiciosTest : DescribeSpec({

    lateinit var mockRepo: IActividadRepository
    lateinit var service: ActividadServicios

    beforeSpec {
        mockRepo = mockk(relaxed = true)
        service = ActividadServicios(mockRepo)
    }

    describe("crearEvento") {

        it("debe agregar correctamente un evento") {
            val evento = Evento("Hacer algo", "17-8-2005", "Madrid")
            every { mockRepo.agregarActividad(any()) } just Runs

            val resultado = service.crearEvento(evento)

            resultado shouldBe evento
            verify { mockRepo.agregarActividad(evento) }

        }

        it("debería lanzar error si el evento es nulo") {
            shouldThrow<IllegalArgumentException> {
                service.crearEvento(null)
            }
        }
    }

    describe("listarActividades") {

        it("debe devolver una lista de detalles de actividades") {
            val actividad1 = mockk<Actividad>()
            val actividad2 = mockk<Actividad>()

            every { actividad1.obtenerDetalle() } returns "Detalle 1"
            every { actividad2.obtenerDetalle() } returns "Detalle 2"
            every { mockRepo.obtenerActividades() } returns listOf(actividad1, actividad2)

            val resultado = service.listarActividades()

            resultado shouldBe listOf("Detalle 1", "Detalle 2")
        }

        it("debe devolver una lista vacía si no hay actividades") {
            every { mockRepo.obtenerActividades() } returns emptyList()

            val resultado = service.listarActividades()

            resultado shouldBe emptyList()
        }
    }

    describe("obtenerTareasDeUsuario") {

        it("debe devolver solo tareas asignadas al usuario") {
            val usuario = Usuario("Bruno")
            val tarea1 = Tarea("Tarea 1")
            val tarea2 = Tarea("Tarea 2")
            val evento = Evento("Evento", "3-18-2006", "Madrid")

            tarea1.asignarUsuario(usuario)
            tarea2.asignarUsuario(usuario)

            val actividades = listOf(tarea1, tarea2, evento)

            val resultado = service.obtenerTareasDeUsuario(usuario, actividades)

            resultado shouldBe listOf(tarea1, tarea2)
        }

        it("debe devolver lista vacía si el usuario es null") {
            val tarea = Tarea("Tarea sin asignar")
            val actividades = listOf(tarea)

            val resultado = service.obtenerTareasDeUsuario(null, actividades)

            resultado shouldBe emptyList()
        }
    }
})

