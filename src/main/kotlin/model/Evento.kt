class Evento private constructor(
    descripcion: String,
    fecha: String,
    private val ubicacion: String
) : Actividad(descripcion) {
    private val fechaNumerica: Int = fecha.toInt() 

    companion object {
        private val logger = Logger.getLogger("MiLogger")

        fun creaInstancia(descripcion: String, fecha: String, ubicacion: String, etiquetas: String = ""): Evento {
            require(validarFecha(fecha)) { "La fecha debe de ser válida" }
            require(ubicacion.isNotBlank()) { "La ubicación no puede estar en blanco" }
            val evento = Evento(descripcion, fecha, ubicacion)
            if (etiquetas.isNotBlank()) {
                evento.agregarEtiquetas(etiquetas)
            }
            return evento
        }

        private fun validarFecha(fecha: String): Boolean {
            return try {
                logger.info("Intentando validar la fecha: $fecha")
                LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                true
            } catch (e: Exception) {
                logger.severe("Error al validar fecha: ${e.message}")
                false
            }
        }
    }

    override fun obtenerDetalle(): String {
        val etiquetasStr = if (obtenerEtiquetas().isNotEmpty()) {
            " - Etiquetas: ${obtenerEtiquetas().joinToString(", ")}"
        } else ""
        return "Evento $id - $descripcion [Fecha: $fecha, Ubicación: $ubicacion]$etiquetasStr"
    }
}
