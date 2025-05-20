# 1.-Instalar la herramienta elegida (Detekt o Ktlint) e incluir capturas de pantalla del proceso.
[Captura de instalación](https://github.com/moraalees/TaskManagerEntornos/blob/Bruno/images/debug1/Captura%20de%20pantalla%202025-05-20%20185517.png)

# 2.-Integrar el analizador en el proyecto que se está desarrollando y ejecutar el análisis.
[He metido el comando .\gradlew detekt](https://github.com/moraalees/TaskManagerEntornos/blob/Bruno/images/debug1/Captura%20de%20pantalla%202025-05-20%20190056.png)

# 3-4.-Identifica los errores 
He elegido los siguientes errores de mi código.

## 1.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\model\Estado.kt:5:2: The file C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\model\Estado.kt is not ending with a new line. [NewLineAtEndOfFile]  

Este error lo arreglé con una línea en blanco al final del archivo. Aunque no afecta al código, algunas herramientas como linters lo necesitan porque es una buena práctica: ayuda a evitar problemas en sistemas de control de versiones y mejora la legibilidad al concatenar archivos en Git

[captura error 1](https://github.com/moraalees/TaskManagerEntornos/blob/Bruno/images/debug1/Captura%20de%20pantalla%202025-05-20%20192439.png)

## 2.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\model\Filtro.kt:6:1: The class Filtro only contains utility functions. Consider defining it as an object. [UtilityClassWithPublicConstructor]

Cambie class por object 

[captura error 2](https://github.com/moraalees/TaskManagerEntornos/blob/Bruno/images/debug1/Captura%20de%20pantalla%202025-05-20%20194313.png)

## 3.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\ui\ConsolaUI.kt:122:17: Function asignarTareaAUsuario has 4 return statements which exceeds the limit of 2. [ReturnCount]

El error aparece porque mi función asignarTareaAUsuario() tiene muchos return, y el analizador de código lo considera mala práctica. Para solucionarlo, agrupé las condiciones dentro de if/else para reducir  los return y mejorar la legibilidad del código.

[captura error 3](https://github.com/moraalees/TaskManagerEntornos/blob/Bruno/images/debug1/Captura%20de%20pantalla%202025-05-20%20194954.png)

## 4.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\ui\ConsolaUI.kt:195:17: Function gestionarSubtareas has 3 return statements which exceeds the limit of 2. [ReturnCount]

El error era por tener demasiados return dentro de la función gestionarSubtareas. Lo solucioné reorganizando el código para que en vez de salir antes con return, use if y controle los flujos dentro de la función. Así solo dejo un return general si no hay tareas, y el resto lo manejo con condiciones.

[captura error 4](https://github.com/moraalees/TaskManagerEntornos/blob/Bruno/images/debug1/Captura%20de%20pantalla%202025-05-20%20195430.png)

## 5.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\test\kotlin\ActividadServiciosTest.kt:6:1: io.mockk.* is a wildcard import. Replace it with fully qualified imports. [WildcardImport]

En este error solo  cambie * en los imports porque IntelliJ lo marcan como mala práctica. En su lugar, puse solamente los imports de las funciones de mockk que realmente estoy utilizando, como mockk, every, verify... Así el código es más limpio, más claro, y cumple con las reglas de estilo del proyecto o del linter.

[captura error 5](https://github.com/moraalees/TaskManagerEntornos/blob/Bruno/images/debug1/Captura%20de%20pantalla%202025-05-20%20191528.png)


# 5.-Cambie de true a false el  TooManyFunctions


# Responde a las preguntas 

## [1]
### 1.a ¿Que herramienta has usado, y para que sirve? 
He usado Detekt, una herramienta que revisa mi código para encontrar errores o malas prácticas.
### 1.b ¿Cuales son sus características principales?
 Sus funciones principales son detectar un código raro, errores comunes y decirme cómo mejorar.
### 1.c ¿Qué beneficios obtengo al utilizar dicha herramienta?
Me ayuda a escribir mejor el código desde el principio y a evitar problemas en el futuro.
# [2]

### 2.a De los errores/problemas que la herramienta ha detectado y te ha ayudado a solucionar, ¿cual es el que te ha parecido que ha mejorado más tu código?
El error que más me ayudó fue el de tener muchos return en una misma función.
### 2.b ¿La solución que se le ha dado al error/problema la has entendido y te ha parecido correcta?
Sí, entendí la solución y me pareció correcta.
### 2.c ¿Por qué se ha producido ese error/problema?
El error pasó porque usé varios return para salir de la función en diferentes partes, lo que la hacía más difícil de leer.
# [3]

### 3.a ¿Que posibilidades de configuración tiene la herramienta? 
Detekt se puede configurar para marcar ciertos errores, como la cantidad de return, el tamaño de las funciones...
### 3.b De esas posibilidades de configuración, ¿cuál has configurado para que sea distinta a la que viene por defecto?
En mi caso, Detekt estaba configurado para permitir solo 2 return, y tuve que adaptar mi código a eso.
### 3.c Pon un ejemplo de como ha impactado en tu código, enlazando al código anterior al cambio, y al posterior al cambio,
Antes usaba return directo cuando algo fallaba. Ahora uso if-else para organizar mejor la función.

# [4]

### 4 ¿Qué conclusiones sacas después del uso de estas herramientas?

Detekt me ha ayudado a escribir código más claro y ordenado. Aunque al principio cuesta cambiar cosas, luego veo que el resultado es mejor y aprendo buenas prácticas.


