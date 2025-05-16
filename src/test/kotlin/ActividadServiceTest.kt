import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.assertions.throwables.shouldThrow
import io.mockk.every
import io.mockk.mockk
import es.prog2425.taskmanager.model.*
import es.prog2425.taskmanager.data.*
import es.prog2425.taskmanager.service.*

class ActividadServiceTest : DescribeSpec({

    val mockRepositorio = mockk<ActividadRepositorioEnMemoria>()
    val servicio = ActividadServicios(mockRepositorio)

    describe("filtrarPorTipo") {

        it("debería devolver solo actividades del tipo indicado (tipo válido)") {
            val actividades = listOf(
                Tarea.creaInstancia("Tarea 1"),
                OtraActividad(),
                Tarea.creaInstancia("Tarea 2")
            )

            every { mockRepositorio.obtenerActividades() } returns actividades

            val resultado = servicio.filtrarPorTipo(Tarea::class.java)

            resultado.size shouldBe 2
            resultado.all { it is Tarea } shouldBe true
        }

        it("debería lanzar IllegalArgumentException si se pasa tipo nulo") {
            shouldThrow<IllegalArgumentException> {
                servicio.filtrarPorTipo(null)
            }
        }
    }

    describe("filtrarPorEstado") {

        it("debería devolver tareas con estado especificado (estado válido)") {
            val t1 = Tarea.creaInstancia("Tarea 1")
            t1.cambiarEstado(Estado.EN_PROGRESO)

            val t2 = Tarea.creaInstancia("Tarea 2")
            t2.cambiarEstado(Estado.FINALIZADA)

            val t3 = Tarea.creaInstancia("Tarea 3")
            t3.cambiarEstado(Estado.EN_PROGRESO)

            val actividades = listOf(t1, t2, t3)
            every { mockRepositorio.obtenerActividades() } returns actividades

            val resultado = servicio.filtrarPorEstado(Estado.EN_PROGRESO)

            resultado.size shouldBe 2
            resultado.all { it.estado == Estado.EN_PROGRESO } shouldBe true
        }

        it("debería devolver lista vacía si no hay tareas con ese estado") {
            val t1 = Tarea.creaInstancia("Tarea 1")
            t1.cambiarEstado(Estado.ABIERTA)

            val t2 = Tarea.creaInstancia("Tarea 2")
            t2.cambiarEstado(Estado.FINALIZADA)

            every { mockRepositorio.obtenerActividades() } returns listOf(t1, t2)

            val resultado = servicio.filtrarPorEstado(Estado.EN_PROGRESO)

            resultado.shouldBe(emptyList())
        }
    }

    describe("obtenerTareaPorId") {

        it("debería devolver la tarea con el ID especificado si existe") {
            val tareaEsperada = Tarea.creaInstancia("Tarea importante")
            val otra = OtraActividad()
            val actividades = listOf(tareaEsperada, otra)

            every { mockRepositorio.obtenerActividades() } returns actividades

            val resultado = servicio.obtenerTareaPorId(tareaEsperada.id)

            resultado?.id shouldBe tareaEsperada.id
            resultado?.descripcion shouldBe tareaEsperada.descripcion
        }

        it("debería devolver null si no existe una tarea con ese ID") {
            val actividades = listOf(
                Tarea.creaInstancia("Tarea 1"),
                OtraActividad()
            )

            every { mockRepositorio.obtenerActividades() } returns actividades

            val resultado = servicio.obtenerTareaPorId(999)

            resultado shouldBe null
        }
    }
})