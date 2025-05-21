## 1.- Integración de Detekt

Tras entender claramente la tarea que debía llevar a cabo, decidí revisar los apuntes que tenía sobre Ktlint y Detekt. Al analizarlos, me di cuenta de que había dos formas de integrar Detekt: una era a través del propio entorno de desarrollo (IDE), y la otra consistía en incluir ciertas dependencias dentro del archivo `build.gradle.kts`.

Probé primero con la instalación desde el IDE, siguiendo las indicaciones que me habían dado, pero no logré encontrar ninguna opción visible para agregar Detekt de esa manera. Debido a esto, opté finalmente por configurar el proyecto manualmente, añadiendo directamente las dependencias necesarias en el archivo `build.gradle.kts`.


## 2.- Generación del archivo de configuración y ejecución de Detekt

Después de añadir las dependencias necesarias, el siguiente paso fue ejecutar un comando en la terminal del proyecto: `.\gradlew detektGenerateConfig`. Este comando se utiliza para generar el archivo de configuración de Detekt, lo cual es un paso previo necesario para poder realizar los análisis correctamente.

Una vez creado dicho archivo, procedí a ejecutar el comando `.\gradlew detekt`, que permite ejecutar el análisis del código. Al hacerlo, se mostraron los distintos errores detectados en el proyecto, proporcionando así un resumen claro de los problemas a corregir.


## 3.- Identificación y corrección de errores

Una vez revisados los resultados del análisis realizado con Detekt, decidí corregir cinco de los errores detectados. A continuación, detallo los problemas encontrados y las acciones que tomé para solucionarlos:

- **Filtro como objeto**: La clase `Filtro` contenía únicamente funciones, por lo que lo más adecuado era convertirla en un `object` en lugar de una clase.

https://github.com/moraalees/TaskManagerEntornos/blob/e35bbfa2c90e70c1af82dbe765297a95cc5d0eea/src/main/kotlin/model/Filtro.kt#L3-L16

- **Constructor vacío en Filtro**: Detecté que `Filtro` tenía un constructor sin contenido, lo cual no era necesario. Por ello, eliminé dicho constructor para simplificar la clase.

- **Demasiados `return` en asignarTareaAUsuario**: El analizador marcó como mala práctica el uso excesivo de sentencias `return` en la función `asignarTareaAUsuario()`. Para corregirlo, agrupé las condiciones usando estructuras `if/else`, lo que permitió reducir los `return` y hacer el código más claro.

- **Demasiados `return` en gestionarSubtarea**: Dentro de la función `gestionarSubtarea`, salió un error debido a un exceso de `return`. Refactoricé el código para reducir su número y mejorar la legibilidad.

- **Excepción genérica en Evento**: Se utilizaba una excepción genérica al manejar errores en la clase `Evento`. Reemplacé esta por una excepción más específica: `DateTimeParseException`, mejorando así la precisión del manejo de errores.

https://github.com/moraalees/TaskManagerEntornos/blob/e35bbfa2c90e70c1af82dbe765297a95cc5d0eea/src/main/kotlin/model/Evento.kt#L28-L38
