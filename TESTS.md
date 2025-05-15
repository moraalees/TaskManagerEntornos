# 1.- Servicio escogido

Como solo disponemos de un solo servicio en todo el código, me veo en la necesidad de escoger la clase `ActividadServicios`. Esta clase es la que simula ser como la intermediaria entre las clases y la consola.
El servicio recoge los métodos de las clases y los emplea para que después la consola lo muestre y ejecute.

---

# 2.- Métodos escogidos

Este servicio, al ser el único en todo nuestro programa, posee un total de 19 métodos. Como son demasiados, y entre que unos son muy sencillos y somos 4 personas en el grupo, hemos decidido dividir los métodos
con el fin de probar en 3 métodos por persona las pruebas unitarias.

### Funciones escogidas
He decidido realizar las pruebas unitarias sobre:

- `filtrarPortipo(tipo: Class<out Actividad>): List<Actividad>`: Filtra, como su nombre indica, por el tipo o clase de la Actividad.
- `filtrarPorEstado(estado: Estado): List<Tarea>`: Filtra las tareas según el estado que tengan asignados.
- `obtenerTareaPorId(id: Int?): Tarea?`: Busca una tarea por su ID y la retorna.
