## 1.- Integración de Detekt

Tras entender claramente la tarea que debía llevar a cabo, decidí revisar los apuntes que tenía sobre Ktlint y Detekt. Al analizarlos, me di cuenta de que había dos formas de integrar Detekt: una era a través del propio entorno de desarrollo (IDE), y la otra consistía en incluir ciertas dependencias dentro del archivo `build.gradle.kts`.

Probé primero con la instalación desde el IDE, siguiendo las indicaciones que me habían dado, pero no logré encontrar ninguna opción visible para agregar Detekt de esa manera. Debido a esto, opté finalmente por configurar el proyecto manualmente, añadiendo directamente las dependencias necesarias en el archivo `build.gradle.kts`.


## 2.- Generación del archivo de configuración y ejecución de Detekt

Después de añadir las dependencias necesarias, el siguiente paso fue ejecutar un comando en la terminal del proyecto: `.\gradlew detektGenerateConfig`. Este comando se utiliza para generar el archivo de configuración de Detekt, lo cual es un paso previo necesario para poder realizar los análisis correctamente.

Una vez creado dicho archivo, procedí a ejecutar el comando `.\gradlew detekt`, que permite ejecutar el análisis del código. Al hacerlo, se mostraron los distintos errores detectados en el proyecto, proporcionando así un resumen claro de los problemas a corregir.


## 3.- Identificación y corrección de errores

Una vez revisados los resultados del análisis realizado con Detekt, decidí corregir cinco de los errores detectados. A continuación, detallo los problemas encontrados y las acciones que tomé para solucionarlos:

- **Filtro como objeto**: La clase `Filtro` contenía únicamente funciones, por lo que lo más adecuado era convertirla en un `object` en lugar de una clase.

https://github.com/moraalees/TaskManagerEntornos/blob/e35bbfa2c90e70c1af82dbe765297a95cc5d0eea/src/main/kotlin/model/Filtro.kt#L5-L22

- **Constructor vacío en Filtro**: Detecté que `Filtro` tenía un constructor sin contenido, lo cual no era necesario. Por ello, eliminé dicho constructor para simplificar la clase.

https://github.com/moraalees/TaskManagerEntornos/blob/e35bbfa2c90e70c1af82dbe765297a95cc5d0eea/src/main/kotlin/model/Filtro.kt#L5-L22

- **Demasiados `return` en asignarTareaAUsuario**: El analizador marcó como mala práctica el uso excesivo de sentencias `return` en la función `asignarTareaAUsuario()`. Para corregirlo, agrupé las condiciones usando estructuras `if/else`, lo que permitió reducir los `return` y hacer el código más claro.

https://github.com/moraalees/TaskManagerEntornos/blob/e35bbfa2c90e70c1af82dbe765297a95cc5d0eea/src/main/kotlin/ui/ConsolaUI.kt#L122-L157

- **Demasiados `return` en gestionarSubtarea**: Dentro de la función `gestionarSubtarea`, salió un error debido a un exceso de `return`. Refactoricé el código para reducir su número y mejorar la legibilidad.

https://github.com/moraalees/TaskManagerEntornos/blob/e35bbfa2c90e70c1af82dbe765297a95cc5d0eea/src/main/kotlin/ui/ConsolaUI.kt#L195-L225

- **Excepción genérica en Evento**: Se utilizaba una excepción genérica al manejar errores en la clase `Evento`. Reemplacé esta por una excepción más específica: `DateTimeParseException`, mejorando así la precisión del manejo de errores.

https://github.com/moraalees/TaskManagerEntornos/blob/e35bbfa2c90e70c1af82dbe765297a95cc5d0eea/src/main/kotlin/model/Evento.kt#L28-L38

## 4.- Exploración y modificación de una opción de configuración personalizada en Detekt

Además de usar las configuraciones por defecto de Detekt, decidí ajustar una de las reglas para adaptarla mejor a la estructura de mi proyecto. En esta ocasión, me enfoqué en la longitud máxima de línea permitida.

### Opción modificada: `MaxLineLength.maxLineLength`

Esta regla define cuántos caracteres puede tener como máximo una línea de código antes de que sea avisada como un problema. Su objetivo es mejorar la legibilidad del código y aumentar el número de caracteres.

### ¿Cómo cambiarlo?

En el archivo `detekt.yml`, se puede modificar esta regla así:

```yaml
style:
  MaxLineLength:
    active: true
    maxLineLength: 150
```

Con esta configuración, el límite de longitud por línea se incrementa de su valor predeterminado (generalmente 120) a 150 caracteres.

### ¿Cómo afecta esto al código y al informe?

**Antes:**  
Cualquier línea de código que superara los 120 caracteres era reportada como error.

**Después:**  
Ahora, solo se reportan líneas que superan los 150 caracteres. Esto reduce el número de advertencias innecesarias, especialmente en llamadas a funciones con muchos argumentos o cadenas largas.

## Preguntas

## [1]

### 1.a ¿Qué herramienta has usado, y para qué sirve?  
He utilizado **Detekt**, una herramienta de análisis para proyectos escritos en Kotlin. Sirve para analizar automáticamente el código y detectar errores, malas prácticas o incumplimientos de estilo.

### 1.b ¿Cuáles son sus características principales?  
Detekt puede encontrar funciones demasiado largas, clases con demasiadas responsabilidades, nombres de variables o funciones mal definidos, y problemas de formato. Además, es personalizable, por lo que puedes adaptar sus reglas a lo que necesites en tu proyecto.

### 1.c ¿Qué beneficios obtengo al utilizar dicha herramienta?  
Con Detekt puedes mejorar la calidad del código, hacer que sea más legible y fácil de mantener, y evitar errores comunes.

---

## [2]

### 2.a De los errores/problemas que la herramienta ha detectado y te ha ayudado a solucionar, ¿cuál es el que te ha parecido que ha mejorado más tu código?  
El que más ha mejorado mi código ha sido el de tener **demasiados `return`** dentro de una misma función. Reestructuré el código usando bloques `if/else` y así quedó mucho más ordenado y fácil de seguir.

### 2.b ¿La solución que se le ha dado al error/problema la has entendido y te ha parecido correcta?  
Sí, la solución me pareció clara y acertada. Entendí que usar muchos `return` puede dificultar la lectura del código y que al organizarlos mejor, el flujo es más comprensible.

### 2.c ¿Por qué se ha producido ese error/problema?  
El error surgió porque la función tenía demasiados caminos de salida (`return`), lo cual hace que el código sea más difícil de leer y mantener. Detekt lo señala como mala práctica para ayudar a programar con un código más limpio.

---

## [3]

### 3.a ¿Qué posibilidades de configuración tiene la herramienta?  
Detekt permite configurar muchas de sus reglas: se puede ajustar la longitud máxima de líneas, el número de funciones por clase, el formato de nombres, qué reglas están activas o no, entre otras opciones.

### 3.b De esas posibilidades de configuración, ¿cuál has configurado para que sea distinta a la que viene por defecto?  
He modificado la regla **MaxLineLength**, que define cuántos caracteres puede tener como máximo una línea. Por defecto está en 120, y yo lo aumenté a **150 caracteres**.

### 3.c Pon un ejemplo de cómo ha impactado en tu código, enlazando al código anterior al cambio, y al posterior al cambio.  
Al modificar la configuración de la longitud máxima de línea de 120 a 150 caracteres, el análisis de Detekt pasó de mostrar **34 errores** a **22**. Se eliminaron **12 avisos** relacionados con ese límite, lo que hizo que el informe fuera más limpio y centrado en errores realmente importantes.

---

## [4]

### ¿Qué conclusiones sacas después del uso de estas herramientas?  
Detekt me ha parecido una herramienta muy útil para mejorar la calidad del código. Me ha ayudado a encontrar y corregir errores que sin el uso de esta herramienta habría pasado por alto. Además, permite adaptar las reglas a las necesidades del proyecto, lo que hace que el análisis sea más eficiente y menos molesto. En general, creo que es una buena forma de aprender a programar mejor y mantener un código ordenado desde el principio.


