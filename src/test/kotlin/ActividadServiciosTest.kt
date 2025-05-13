// Importo las clases necesarias del proyecto y las librerías de test
import es.prog2425.taskmanager.data.ActividadRepositorioEnMemoria
import es.prog2425.taskmanager.model.ActividadServicios
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import es.prog2425.taskmanager.model.Tarea
import es.prog2425.taskmanager.model.Usuario

// Esta clase contiene las pruebas unitarias para el servicio de actividades/tareas
class ServicioTareasTest : DescribeSpec({

    // Agrupo las pruebas bajo el nombre del servicio que estoy probando
    describe("ServicioTareas") {

        // Creo dos usuarios que voy a utilizar para asignar tareas
        val usuarioJuan = Usuario("Juan")
        val usuarioAna = Usuario("Ana")

        // Creo tres tareas de ejemplo
        val tarea1 = Tarea("Tarea A")
        val tarea2 = Tarea("Tarea B")
        val tarea3 = Tarea("Tarea C")

        // Asigno tareas 1 y 2 a Juan, y la 3 a Ana
        tarea1.asignarUsuario(usuarioJuan)
        tarea2.asignarUsuario(usuarioJuan)
        tarea3.asignarUsuario(usuarioAna)

        // Lista de todas las tareas creadas
        val actividades = listOf(tarea1, tarea2, tarea3)

        // Creo una instancia del servicio usando un repositorio en memoria
        val servicio: ActividadServicios<Tarea> = ActividadServicios(ActividadRepositorioEnMemoria())

        // Test: el servicio devuelve solo las tareas asignadas al usuario Juan
        it("devuelve solo las tareas de un usuario") {
            val resultado = servicio.obtenerTareasDeUsuario(usuarioJuan, actividades)
            resultado shouldBe listOf(tarea1, tarea2)
        }

        // Test: si el usuario no tiene tareas asignadas, devuelve una lista vacía
        it("devuelve lista vacía si el usuario no tiene tareas") {
            val usuarioSinTareas = Usuario("SinTareas")
            val resultado = servicio.obtenerTareasDeUsuario(usuarioSinTareas, actividades)
            resultado shouldBe emptyList()
        }

        // Test: devuelve correctamente la tarea si el ID existe
        it("devuelve la tarea por id si existe") {
            val resultado = servicio.obtenerTareaPorId(tarea3.id)
            resultado shouldBe tarea3
        }

        // Test: devuelve null si se busca una tarea con un ID que no existe
        it("devuelve null si el id no existe") {
            val resultado = servicio.obtenerTareaPorId(99)
            resultado shouldBe null
        }

        // Test: devuelve null si el ID es null
        it("devuelve null si el id es null") {
            val resultado = servicio.obtenerTareaPorId(null)
            resultado shouldBe null
        }
    }
})
