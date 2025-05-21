## 1.- Integración de Detekt

Tras entender claramente la tarea que debía llevar a cabo, decidí revisar los apuntes que tenía sobre Ktlint y Detekt. Al analizarlos, me di cuenta de que había dos formas de integrar Detekt: una era a través del propio entorno de desarrollo (IDE), y la otra consistía en incluir ciertas dependencias dentro del archivo `build.gradle.kts`.

Probé primero con la instalación desde el IDE, siguiendo las indicaciones que me habían dado, pero no logré encontrar ninguna opción visible para agregar Detekt de esa manera. Debido a esto, opté finalmente por configurar el proyecto manualmente, añadiendo directamente las dependencias necesarias en el archivo `build.gradle.kts`.
