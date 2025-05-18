# Linting / Actividad

---

## Descripción:

La actividad consiste en instalar y usar un analizador de código estático (Detekt o Ktlint) en el proyecto que vienes desarrollando, capturar evidencias gráficas, detectar y clasificar errores, aplicar soluciones y explorar las posibilidades de configuración de la herramienta elegida.

Instala y usa los analizadores de código comentados en clase: Detekt, Ktlint 

## Objetivo:

Conocer que es Analizador de código y su proposito  
Familiarizarse con herramientas Detekt o Ktlint.  
Usar las herramientas y estudiar y aplicar configuraciones de la herramienta seleccionada.

## Trabajo a realizar:

Haciendo uso de las herramientas descritas en el punto 4.3 Analizador de código  

- Instalar la herramienta elegida (Detekt o Ktlint) e incluir capturas de pantalla del proceso.
- Integrar el analizador en el proyecto que se está desarrollando y ejecutar el análisis.
- Identificar al menos 5 tipos de errores detectados.

Para cada tipo de error, documentar:

- Descripción del error.
- Solución aplicada (antes y después, con enlaces a commits específicos).

- Explorar y modificar al menos una opción de configuración del analizador distinta de la predeterminada; describir cómo afecta al código y por tanto al informe de errores.

## Responde a las preguntas:

### [1]

1.a ¿Que herramienta has usado, y para que sirve?  
1.b ¿Cuales son sus características principales?  
1.c ¿Qué beneficios obtengo al utilizar dicha herramienta?

### [2]

2.a De los errores/problemas que la herramienta ha detectado y te ha ayudado a solucionar, ¿cual es el que te ha parecido que ha mejorado más tu código?  
2.b ¿La solución que se le ha dado al error/problema la has entendido y te ha parecido correcta?  
2.c ¿Por qué se ha producido ese error/problema?

### [3]

3.a ¿Que posibilidades de configuración tiene la herramienta?  
3.b De esas posibilidades de configuración, ¿cuál has configurado para que sea distinta a la que viene por defecto?  
3.c Pon un ejemplo de como ha impactado en tu código, enlazando al código anterior al cambio, y al posterior al cambio,

### [4]

4 ¿Qué conclusiones sacas después del uso de estas herramientas?

---

## 1.- Instalación de Detekt

Tras revisar y entender el trabajo que tenía que hacer, decidí echar un vistazo a los apuntes que tenía a mi disponibilidad sobre `Ktlint` y `Detekt`. Me percaté de que debía o bien instalar desde el IDE o agregar ciertas dependencias en el archivo `build.gradle.kts`. No obstante, cuando intenté instalarlo desde donde se me dijo que se podía, no encontré por ningún lado dónde instalar, en mi caso, Detekt. Es por esto, que al final me decanté por agregar a dicho archivo ya mencionado algunas dependencias.

### [`No estan Detekt / Ktlint`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20123301.png)

### [`Dependencias nuevas`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20123329.png)

---

## 2.- Integración

Tras agregar las dependencias, tuve que ingresar en la terminal de mi proyecto cierto comando: `./gradlew detektGenerateConfig`. Todo esto con el fin de generar el archivo de configuración de Detekt, para ya después poder realizar los tests.

### [`Comando e instalación`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20123507.png)

Ya una vez generado el archivo, debía ejecutar el comando `./gradlew detekt` para poder ver los resultados de la detección de errores. Una vez ejecutado, saldrían los diferentes errores del programa.

### [`Análisis completado`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20123640.png)

---

## 3.- Identificación de errores

Luego de consultar los 43 errores del análisis, decidí solucionar 5 diferentes entre ellos:

- [`Excepción de Evento`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124225.png): La excepción que posee un método de la clase Evento es demasiado genérica, cuando debería de ser específica.
- [`Nueva línea en Actividad`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124522.png): La clase Actividad no acaba con una nueva línea.
- [`Imports demasido genéricos en ActividadServiceTest`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124815.png): La clase creada para los tests de las pruebas unitarias contienen unos `imports` bastante genéricos, lo que es llamado `WildcartImport`.
- [`Constructor vacío en Filtro`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125026.png): La clase Filtro contiene un constructor vacío, que fácilmente debería de ser eliminado.
- [`Filtro podría se de clase Object`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125205.png): La clase Filtro debería de ser un objeto, ya que solo posee funciones.

---

## 4.- Solución de errores

Tras identificar los diferentes errores, decidí ponerme manos a la obra para solucionarlos uno a uno:

* 1.- Evento: La excepción usada en el método `validarFecha(fecha: String)` poseía una `Exception`, con lo cual sustituí dicha excepción por una más adecuada en el contexto de dicho método, una llamada `DateTimeParseException`. [`Antes`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124259.png) / [`Después`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124251.png)
* 2.- Actividad: La clase actividad dio un error ya que esta no acababa con una nueva línea... Simplemente agregué una. [`Antes`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124621.png) / [`Después`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124628.png)
* 3.- ActividadServiceTest: La clase usada en las pruebas unitarias presentaba un WildcartImport. Tras revisar lo que realmente debería importar la clase, sustituí dichos imports por unos más apropiados. [`Antes`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124800.png) / [`Después`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20124930.png)
* 4.- Filtro (Constructor): Filtro poseía un constructor vacío porque probablemente en un pasado se decidió que a lo mejor debía de recibir algún parámetro. Simplemente lo quité. [`Antes`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125031.png) / [`Después`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125036.png)
* 5.- Filtro (Tipo de Clase): Al no poseer nigún parámetro o instancias dentro de esta, la clase podría ser perfectamente un `Object`. Cambié su tipo simplemente para solucionar el error. [`Solución`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125405.png)

Sobre los commits acerca de cuándo, cómo y qué modifiqué para resolver los errores:

### [`Commit 1`](https://github.com/moraalees/TaskManagerEntornos/commit/0fea1ce49b535240a1dfe59e811b70a72f7f2f53)
### [`Commit 2`]()
### [`Commit 3`]()
### [`Commit 4`]()
### [`Commit 5`]()
