class ActividadServiceTest : DescribeSpec({

    lateinit var mockRepo: IActividadRepository
    lateinit var service: ActividadServicios
    lateinit var usuarioEjemplo: Usuario

    beforeEach {
        mockRepo = mockk(relaxed = true)
        service = ActividadServicios(mockRepo)
        usuarioEjemplo = Usuario("Ana")
        service.usuarios.add(usuarioEjemplo)
    }


    describe("filtrarPorTipo") {
        it("debería devolver solo las actividades del tipo especificado") {
            val tarea1 = Tarea("Tarea 1")
            val tarea2 = Tarea("Tarea 2")
            val evento = object : Actividad("Evento") {}
            every { mockRepo.obtenerActividades() } returns listOf(tarea1, tarea2, evento)

            val resultado = service.filtrarPorTipo(Tarea::class.java)

            resultado.all { it is Tarea } shouldBe true
            resultado.size shouldBe 2
        }

        it("debería lanzar IllegalArgumentException si el tipo es nulo") {
            shouldThrow<IllegalArgumentException> {
                service.filtrarPorTipo(null)
            }
        }
    }

    describe("filtrarPorEstado") {
        it("debería devolver las tareas que coincidan con el estado dado") {
            val tarea1 = Tarea("Tarea 1", estado = Estado.EN_PROGRESO)
            val tarea2 = Tarea("Tarea 2", estado = Estado.EN_PROGRESO)
            val tarea3 = Tarea("Tarea 3", estado = Estado.COMPLETADA)
            every { mockRepo.obtenerActividades() } returns listOf(tarea1, tarea2, tarea3)

            val resultado = service.filtrarPorEstado(Estado.EN_PROGRESO)

            resultado shouldContainExactly listOf(tarea1, tarea2)
        }

        it("debería devolver una lista vacía si no hay tareas con ese estado") {
            val tarea1 = Tarea("Tarea 1", estado = Estado.COMPLETADA)
            every { mockRepo.obtenerActividades() } returns listOf(tarea1)

            val resultado = service.filtrarPorEstado(Estado.EN_PROGRESO)

            resultado.shouldBeEmpty()
        }
    }

    describe("filtrarPorUsuario") {
        it("debería devolver las tareas asignadas al usuario dado") {
            val tarea1 = Tarea("Tarea 1", asignadoA = usuarioEjemplo)
            val tarea2 = Tarea("Tarea 2", asignadoA = usuarioEjemplo)
            val tarea3 = Tarea("Tarea 3", asignadoA = Usuario("Otro"))
            every { mockRepo.obtenerActividades() } returns listOf(tarea1, tarea2, tarea3)

            val resultado = service.filtrarPorUsuario("Ana")

            resultado shouldContainExactly listOf(tarea1, tarea2)
        }

        it("debería devolver una lista vacía si el usuario no existe") {
            every { mockRepo.obtenerActividades() } returns emptyList()

            val resultado = service.filtrarPorUsuario("Inexistente")

            resultado.shouldBeEmpty()
        }
    }
})
