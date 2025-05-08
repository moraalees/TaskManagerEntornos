Para este trabajo de entornos he decidido usar una version nueva del taskmanager hecha por mi mismo con cosas nuevas dadas en programación y optimizada.

## 1.Selección del servicio
Para el servicio elegí `ActividadService` al ser el principal servicio de la aplicación.

## 2.Identificación de métodos

### Nombre del método
`agregarTarea()`

`agregarEvento()`

`listarActividades()`

### Parámetros de entrada
`agregarTarea()` recibe un objeto de clase Tarea

`agregarEvento()` recibe un objeto de clase evento

`listarActividades()` no recibe nada pero es complementaria a las dos amteriores

### Resultado esperado o efecto en el repositorio
El resultado esperado en ambas funciones `agregarTarea()` y `agregarEvento()` se añada la Actividad correspondiente a una lista que posteriormente `listarActividades()` ejecutará.

## 3.Diseño de casos de prueba
### Casos nominales:
`agregarTarea(tarea : Tarea)`

`agregarEvento(evento : Evento)`

`listarActividad` returns `tarea ` y ` evento` en modo de lista

### Casos de error:

## 4.Implementación de los tests

## 5.Ejecución y reporte de los resultados
