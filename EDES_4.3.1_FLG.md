## 1.- Integración de Detekt

Tras entender claramente la tarea que debía llevar a cabo, decidí revisar los apuntes que tenía sobre Ktlint y Detekt. Al analizarlos, me di cuenta de que había dos formas de integrar Detekt: una era a través del propio entorno de desarrollo (IDE), y la otra consistía en incluir ciertas dependencias dentro del archivo `build.gradle.kts`.

Probé primero con la instalación desde el IDE, siguiendo las indicaciones que me habían dado, pero no logré encontrar ninguna opción visible para agregar Detekt de esa manera. Debido a esto, opté finalmente por configurar el proyecto manualmente, añadiendo directamente las dependencias necesarias en el archivo `build.gradle.kts`.


## 2.- Generación del archivo de configuración y ejecución de Detekt

Después de añadir las dependencias necesarias, el siguiente paso fue ejecutar un comando en la terminal del proyecto: `.\gradlew detektGenerateConfig`. Este comando se utiliza para generar el archivo de configuración de Detekt, lo cual es un paso previo necesario para poder realizar los análisis correctamente.

Una vez creado dicho archivo, procedí a ejecutar el comando `.\gradlew detekt`, que permite ejecutar el análisis del código. Al hacerlo, se mostraron los distintos errores detectados en el proyecto, proporcionando así un resumen claro de los problemas a corregir.
