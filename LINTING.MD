### 1.Instalar la herramienta elegida (Detekt o Ktlint) e incluir capturas de pantalla del proceso.



### 2.Integrar el analizador en el proyecto que se está desarrollando y ejecutar el análisis.

[Errores en pantalla](https://github.com/moraalees/TaskManagerEntornos/blob/Carlos/images/linting/errores.png)

### 3.Identificar al menos 5 tipos de errores detectados.

C:\Users\caram\Reposgit\TaskManagerEntornos\src\main\kotlin\service\ActividadServicios.kt:11:7: Class 'ActividadServicios' with '19' functions detected. Defined threshold inside classes is set to '11' [TooManyFunctions]

C:\Users\caram\Reposgit\TaskManagerEntornos\src\main\kotlin\service\IActividadService.kt:9:11: Interface 'IActividadService' with '16' functions detected. Defined threshold inside interfaces is set to '11' [TooManyFunctions]

C:\Users\caram\Reposgit\TaskManagerEntornos\src\main\kotlin\ui\ConsolaUI.kt:6:7: Class 'ConsolaUI' with '11' functions detected. Defined threshold inside classes is set to '11' [TooManyFunctions]

C:\Users\caram\Reposgit\TaskManagerEntornos\src\main\kotlin\model\Filtro.kt:5:13: An empty default constructor can be removed. [EmptyDefaultConstructor]

C:\Users\caram\Reposgit\TaskManagerEntornos\src\main\kotlin\model\Evento.kt:32:22: The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled. [TooGenericExceptionCaught]

### 4.Para cada tipo de error, documentar:

Descripción del error.

1.Class 'ActividadServicios' with '19' functions detected. Defined threshold inside classes is set to '11' [TooManyFunctions]

2.Interface 'IActividadService' with '16' functions detected. Defined threshold inside interfaces is set to '11' [TooManyFunctions]

3.Class 'ConsolaUI' with '11' functions detected. Defined threshold inside classes is set to '11' [TooManyFunctions]

4.An empty default constructor can be removed. [EmptyDefaultConstructor]

5.The caught exception is too generic. Prefer catching specific exceptions to the case that is currently handled. [TooGenericExceptionCaught]

Solución aplicada (antes y después, con enlaces a commits específicos).

1: `ActividadServicios` fue dividida en `ActividadServicioFiltro`, `ActividadServicioGestion` y  `ActividadServicioUsuarioTarea`.

[Commit](https://github.com/moraalees/TaskManagerEntornos/commit/bdeaec4a02c5da64a4b89a1d54db72256fb79714)

2: `IActividadServicios` fue dividida en `IActividadServicioFiltro`, `IActividadServicioGestion` y  `IActividadServicioUsuarioTarea`.

[Commit](https://github.com/moraalees/TaskManagerEntornos/commit/851bd2bf0930479be5e52797ae0edf72fd806414)

3: `ConsolaUI` fue dividida en `MenuActividades` y `MenuMostrar` con sus interfaces `IMenuActividades` y `IMenuMostrar`.

[Commit](https://github.com/moraalees/TaskManagerEntornos/commit/c8aaae4e42c3df8cb242f01609bbde50322053a8)

4: El constructor de `Filtro` fue eliminado

[Commit](https://github.com/moraalees/TaskManagerEntornos/commit/39d1e443c31102e6cdfa64ccf5296570b5e0c218)

5: Se cambió el error generico de `Evento` por `DateTimeParseException`

[Commit](https://github.com/moraalees/TaskManagerEntornos/commit/306b1dcb2f0297e4c28d51b3d1f1170af97e8d19)

### 5.Explorar y modificar al menos una opción de configuración del analizador distinta de la predeterminada; describir cómo afecta al código y por tanto al informe de errores.
