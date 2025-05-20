# Linting / Actividad

---

## 1.- Instalación de Detekt

Tras revisar y entender el trabajo que tenía que hacer, decidí echar un vistazo a los apuntes que tenía a mi disponibilidad sobre `Ktlint` y `Detekt`. Me percaté de que debía o bien instalar desde el IDE o agregar ciertas dependencias en el archivo `build.gradle.kts`. No obstante, cuando intenté instalarlo desde donde se me dijo que se podía, no encontré por ningún lado dónde instalar, en mi caso, Detekt. Es por esto, que al final me decanté por agregar a dicho archivo ya mencionado algunas dependencias.

### [`No estan Detekt / Ktlint`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20123301.png)

### [`Dependencias nuevas`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20123329.png)

---

## 2.- Integración

Tras agregar las dependencias, tuve que ingresar en la terminal de mi proyecto cierto comando: `.\gradlew detektGenerateConfig`. Todo esto con el fin de generar el archivo de configuración de Detekt, para ya después poder realizar los tests.

### [`Comando e instalación`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20123507.png)

Ya una vez generado el archivo, debía ejecutar el comando `.\gradlew detekt` para poder ver los resultados de la detección de errores. Una vez ejecutado, saldrían los diferentes errores del programa.

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
### [`Commit 2`](https://github.com/moraalees/TaskManagerEntornos/commit/0b10532807250870a8fdad547ae1e474a4bd9c78)
### [`Commit 3`](https://github.com/moraalees/TaskManagerEntornos/commit/73685f0ec4e1854e0435425aca0738a4d8cbdd76)
### [`Commit 4`](https://github.com/moraalees/TaskManagerEntornos/commit/d3bec0bab8f0b2c866b1c7f33c747c06dba5af5c)
### [`Commit 5`](https://github.com/moraalees/TaskManagerEntornos/commit/cbca5ab63e332e19af0c47c62b22954bb39bb305)

Aquí la nueva detección de errores tras modificar el código: [`MENOS ERRORES`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125455.png)

---

## 5.- Modificación en Detekt

A la hora de volver a mirar los errores, me percaté que, como es normal, todas las clases presentaban el error de `NewLineAtEndOfFile`. Este error no lo tomé demasiado en serio, y de hecho sigo sin entender por qué es considerado un error. Es por esto mismo que decidí explorar el archivo de configuración de Detekt para hacer que no salte aún cuando no hay una línea en blanco en las clases. Simplemente abrí el archivo y busqué el nombre del error. Tras localizarlo y ver que estaba en `true`, lo cambié y puse `false`. [`Antes`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125723.png) / [`Después`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125734.png)

Tras modificar el archivo y volver a usar el comando para realizar las pruebas de los errores, el número bajó considerablemente. [`MENOS ERRORES`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/test/Captura%20de%20pantalla%202025-05-17%20125754.png)

---

## Responde a las preguntas:

### [1]

1.a ¿Que herramienta has usado, y para que sirve?
He usado Detekt. Es una herramienta de análisis estático que sirve para proyectos en Kotlin. Se usa con el fin de detectar ya sean errores, malas prácticas o problemas en el código, sin ejecutar la aplicación. Además, analiza el código fuente y alerta de advertencias o errores que pueden afectar el programa.

1.b ¿Cuales son sus características principales?  
- Tiene diversas reglas que pueden see modificadas por el usuario (como yo hice con `NewLineAtEndOfFile`).
- Puede ser integrado con diferentes estructuras de proyectos (Gradle en mi caso).
- Genera informes automáticos en diferentes formatos.
- Analiza código sin ejecutar el programa.

1.c ¿Qué beneficios obtengo al utilizar dicha herramienta?
He notado que usando Detekt he podido identficar diferentes errores o mejoras dentro de mi código, como el constructor vacío o las excepciones genéricas. Me ha ayudado también a la hora revisar código, ya que de esta forma ha podido identificar automáticamente ciertos puntos de mejora o de error de mi propio código en lugar de hacerlo yo manualmente.

### [2]

2.a De los errores/problemas que la herramienta ha detectado y te ha ayudado a solucionar, ¿cual es el que te ha parecido que ha mejorado más tu código?
Desde mi punto de vista, yo creo que el error de la excepción genérica ayuda bastante a comprender y aplicar en un futuro que no se deberían de poner `Exception` así porque así, que es mejor aplicar el error concreto.

2.b ¿La solución que se le ha dado al error/problema la has entendido y te ha parecido correcta?
He entendido que la excepción concreta se debe de usar en lugar de la genérica. Me parece del todo correcto haberlas cambiado.

2.c ¿Por qué se ha producido ese error/problema?
Ese error se produce porque se está empleando una excepción demasiado genérica, lo cual no es considerado del todo una buena práctica. Usar excepciones genéricas permite ocultar la excepción concreta, la cuál es mejor manejar de otra forma

### [3]

3.a ¿Que posibilidades de configuración tiene la herramienta?
- Poder manipular qué errores cuentan o no a la hora del análisis.
- Elegir el tipo de archivo del informe a descargar.
- Poder declarar un nuevo error o regla.

3.b De esas posibilidades de configuración, ¿cuál has configurado para que sea distinta a la que viene por defecto?
Como ya mostré anteriormente, el error de `NewLineAtEndOfFile` me parecía demasiado absurdo. Por eso mismo, lo desactivé y modifiqué para que no salte en el análisis.

3.c Pon un ejemplo de como ha impactado en tu código, enlazando al código anterior al cambio, y al posterior al cambio.
Lo puse anteriormente. El análisis pasó de 36 errores sin la modificación, a 26. Se eliminaron 10 errores o avisos al desactivar dicho error.

### [4]

4 ¿Qué conclusiones sacas después del uso de estas herramientas?
Realmente me parecieron muy útiles ya que, como comenté, te permiten ahorrar tiempo de búsqueda de errores manual al hacerlo automáticamente.
No comenté un error que me apareció, el cual es el de que una clase posee demasiadas funciones. Si me hubiera dedicado a arreglar dicho error, este me hubiera ayudado a entender mejor que las clases pueden ser divididas en otras más pequeñas, a lo que se le llama responsabilidad única.
