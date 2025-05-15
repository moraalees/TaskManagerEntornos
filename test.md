# Pruebas unitarias del servicio de tareas

En este archivo he documentado los casos de prueba que he diseñado para el servicio `ActividadServicios`, el cual gestiona las tareas (`Tarea`) y depende de un repositorio inyectado (`ActividadRepositorioEnMemoria`).

## Métodos públicos probados

### obtenerTareasDeUsuario(usuario, actividades)

| Método                    | Caso de prueba                              | Estado inicial del mock                      | Acción                                          | Resultado esperado                              |
|---------------------------|---------------------------------------------|-----------------------------------------------|--------------------------------------------------|--------------------------------------------------|
| obtenerTareasDeUsuario    | Usuario con tareas                          | Lista con tareas asignadas a Juan             | Llamo al método pasando `usuarioJuan` y `actividades` | Debe devolver solo las tareas asignadas a Juan  |
| obtenerTareasDeUsuario    | Usuario sin tareas                          | Lista sin tareas asignadas a `SinTareas`      | Llamo al método pasando `usuarioSinTareas` y `actividades` | Debe devolver una lista vacía                   |

---

### obtenerTareaPorId(id)

| Método                    | Caso de prueba                              | Estado inicial del mock                      | Acción                                          | Resultado esperado                              |
|---------------------------|---------------------------------------------|-----------------------------------------------|--------------------------------------------------|--------------------------------------------------|
| obtenerTareaPorId         | ID existente                                | Lista contiene tarea con ID válido            | Llamo con `tarea3.id`                           | Debe devolver la tarea con ese ID               |
| obtenerTareaPorId         | ID inexistente                              | Lista no contiene ese ID                      | Llamo con un ID inexistente (99)                | Debe devolver `null`                            |
| obtenerTareaPorId         | ID null                                     | No se puede buscar                            | Llamo con `ID = null`                           | Debe devolver `null`                            |
