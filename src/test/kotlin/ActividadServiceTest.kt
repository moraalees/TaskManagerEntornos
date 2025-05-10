import es.prog2425.taskmanager.data.ActividadRepositorioEnMemoria
import es.prog2425.taskmanager.model.Estado
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.model.Usuario
import es.prog2425.taskmanager.service.ActividadServicios
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.*

class ActividadServiceTest : DescribeSpec({

    lateinit var mockRepo: ActividadRepositorioEnMemoria
    lateinit var service: ActividadServicios

    beforeEach {
        mockRepo = mockk(relaxed = true)  // Relajado para evitar errores con métodos no configurados
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
            val tarea = spyk(Tarea("Hacer algo"))  // Espiar tarea
            val usuario = Usuario("María")
            every { mockRepo.agregarActividad(any()) } just Runs

            val resultado = service.asignarUsuarioATarea(tarea, usuario)

            resultado shouldBe tarea  // Verifica que la tarea es la misma
            verify { tarea.asignarUsuario(usuario) }  // Verifica que se haya asignado el usuario
            verify { mockRepo.agregarActividad(tarea) }  // Verifica que se haya guardado la tarea
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
            val tarea = spyk(Tarea("Hacer algo"))
            val nuevoEstado = Estado.EN_PROGRESO
            every { mockRepo.agregarActividad(any()) } just Runs


            val resultado = service.cambiarEstadoTarea(tarea, nuevoEstado)

            resultado shouldBe tarea  // Verifica que la tarea es la misma
            verify { tarea.cambiarEstado(nuevoEstado) }  // Verifica que se haya cambiado el estado
            verify { mockRepo.agregarActividad(tarea) }  // Verifica que se haya guardado la tarea
        }

        it("debería lanzar excepción si el estado es nulo") {
            val tarea = Tarea("Hacer algo")
            shouldThrow<IllegalArgumentException> {
                service.cambiarEstadoTarea(tarea, null)  // Lanza excepción si el estado es nulo
            }
        }
    }
})
