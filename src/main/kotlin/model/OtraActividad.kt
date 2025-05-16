package es.prog2425.taskmanager.model

class OtraActividad : Actividad("Otra actividad") {
    override fun obtenerDetalle(): String {
        return "Detalle de otra actividad"
    }
}