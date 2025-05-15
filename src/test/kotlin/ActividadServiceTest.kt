import es.prog2425.taskmanager.data.ActividadRepositorioEnMemoria
import es.prog2425.taskmanager.data.IActividadRepository
import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.model.Usuario
import es.prog2425.taskmanager.service.ActividadServicios
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.*

class ActividadServiceTest : DescribeSpec({

    lateinit var mockRepo: IActividadRepository
    lateinit var service: ActividadServicios

    beforeEach {
        mockRepo = mockk(relaxed = true)  // Mucho más seguro
        service = ActividadServicios(mockRepo)
    }

    describe("crearTarea") {
        it("debería guardar la tarea correctamente") {
            val tarea = Tarea("Hacer algo")
            every { mockRepo.agregarActividad(any()) } just Runs

            val resultado = service.crearTarea(tarea)

            resultado shouldBe tarea  // Verifica que la tarea sea la misma
            verify { mockRepo.agregarActividad(tarea) }  // Verifica que se haya llamado al repositorio
        }

        it("debería lanzar excepción si la tarea es nula") {
            shouldThrow<IllegalArgumentException> {
                service.crearTarea(null)  // Lanza excepción si la tarea es nula
            }
        }
    }

    describe("asignarUsuarioATarea") {
        it("debería asignar un usuario a la tarea correctamente") {
            val tarea = Tarea("Hacer algo")
            val usuario = Usuario("María")
            every { mockRepo.agregarActividad(any()) } just Runs

            val resultado = service.asignarUsuarioATarea(tarea, usuario)

            resultado shouldBe tarea
            resultado.asignadoA shouldBe usuario  // Verifica el efecto
            verify { mockRepo.agregarActividad(tarea) }
        }

        it("debería lanzar excepción si el usuario es nulo") {
            val tarea = Tarea("Hacer algo")
            shouldThrow<IllegalArgumentException> {
                service.asignarUsuarioATarea(tarea, null)  // Lanza excepción si el usuario es nulo
            }
        }
    }

    describe("cambiarEstadoTarea") {
        it("debería cambiar el estado de la tarea correctamente") {
            val tarea = Tarea("Hacer algo")
            val nuevoEstado = Estado.EN_PROGRESO
            every { mockRepo.agregarActividad(any()) } just Runs

            val resultado = service.cambiarEstadoTarea(tarea, nuevoEstado)

            resultado shouldBe tarea
            resultado.estado shouldBe nuevoEstado  // Verifica el efecto
            verify { mockRepo.agregarActividad(tarea) }
        }


        it("debería lanzar excepción si el estado es nulo") {
            val tarea = Tarea("Hacer algo")
            shouldThrow<IllegalArgumentException> {
                service.cambiarEstadoTarea(tarea, null)  // Lanza excepción si el estado es nulo
            }
        }
    }
})
