import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import es.prog2425.taskmanager.model.*
import es.prog2425.taskmanager.data.*
import es.prog2425.taskmanager.service.*

class ActividadServiceTest : DescribeSpec({

    lateinit var mockRepo: IActividadRepository
    lateinit var service: ActividadServicios

    beforeEach {
        mockRepo = mockk(relaxed = true)
        service = ActividadServicios(mockRepo)
    }

    describe("filtrarPorTipo") {
        it("debería devolver solo actividades del tipo indicado") {
            val actividades = listOf(
                Tarea("Tarea A"),
                OtraActividad(),
                Tarea("Tarea B")
            )
            every { mockRepo.obtenerActividades() } returns actividades

            val resultado = service.filtrarPorTipo(Tarea::class.java)

            resultado.size shouldBe 2
            resultado.all { it is Tarea } shouldBe true
        }

        it("debería lanzar excepción si el tipo es nulo") {
            shouldThrow<IllegalArgumentException> {
                service.filtrarPorTipo(null)
            }
        }
    }

    describe("filtrarPorEstado") {
        it("debería devolver tareas con el estado especificado") {
            val tareas = listOf(
                Tarea("T1").apply { cambiarEstado(Estado.EN_PROGRESO) },
                Tarea("T2").apply { cambiarEstado(Estado.FINALIZADA) },
                Tarea("T3").apply { cambiarEstado(Estado.EN_PROGRESO) }
            )
            every { mockRepo.obtenerActividades() } returns tareas

            val resultado = service.filtrarPorEstado(Estado.EN_PROGRESO)

            resultado.size shouldBe 2
            resultado.all { it.estado == Estado.EN_PROGRESO } shouldBe true
        }

        it("debería devolver lista vacía si no hay tareas con ese estado") {
            val tareas = listOf(
                Tarea("T1").apply { cambiarEstado(Estado.FINALIZADA) },
                Tarea("T2").apply { cambiarEstado(Estado.FINALIZADA) }
            )
            every { mockRepo.obtenerActividades() } returns tareas

            val resultado = service.filtrarPorEstado(Estado.EN_PROGRESO)

            resultado shouldBe emptyList()
        }
    }

    describe("obtenerTareaPorId") {
        it("debería devolver la tarea con el ID correcto") {
            val tareaBuscada = Tarea("Tarea encontrada")
            val tareas = listOf(
                Tarea("Otra tarea"),
                tareaBuscada
            )
            every { mockRepo.obtenerActividades() } returns tareas

            val resultado = service.obtenerTareaPorId(tareaBuscada.id)

            resultado shouldBe tareaBuscada
        }

        it("debería devolver null si no existe la tarea con ese ID") {
            val tareas = listOf(
                Tarea("T1"),
                Tarea("T2")
            )
            every { mockRepo.obtenerActividades() } returns tareas

            val resultado = service.obtenerTareaPorId(999)

            resultado shouldBe null
        }
    }
})