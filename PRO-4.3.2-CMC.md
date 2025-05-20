# Code Smell

---

## 1.- Revisión de código

En un principio, mi intención fue hacer una revisión manual para identificar 5 `code smells` diferentes. Intenté hacerlo mediante la opción que te da IntelliJ IDEA al pulsar Code y luego Inspect Code. Se me generó un resultado del análisis dónde me salieron unos cuantos errores. [`Evidencia`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/codeSmells/Captura%20de%20pantalla%202025-05-20%20165626.png). Sin embargo, a partir de ahí no supe continuar. Fue entonces cuando caí en la existencia del Linter Detekt, que usé anteriormente para otra entrega. Gracias a este, al ejecutar el comando `.\gradlew detekt`, me salieron 26 advertencias y/o errores. [`Evidencia`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/codeSmells/Captura%20de%20pantalla%202025-05-20%20171510.png).

Dentro de esos 26 errores, que catalogué como `code smells`, se encuentran:
- Captura de excepción genérica en la Clase ConsolaUI. Como patrón de refactorización emplearé `Replace Exception with Specific`.
- Excepción lanzada en Tarea. Como patrón de refactorización emplearé `Replace Exception with check/error`.
- La función cambiarEstadoTarea posee demasiados return en la clase ConsolaUI. Como patrón de refactorización emplearé `Simplify Conditional` o `Extract Method`.
- WildcardImport en ConsolaUI. Como patrón de refactorización emplearé `Replace Wildcard Imports with Explicit Imports`.
- Demasiadas funciones en ConsolaUI. Como patrón de refactorización emplearé `Split Class`.

---

## 2.- Refactorizaciones

Sobre los 5 posibles candidatos para arreglar, he decicido centrarme en:
- Excepción de ConsolaUI / `Replace Exception with Specific`.
- Excepción de Tarea / `Replace Exception with check/error`.
- Función cambiarEstadoTarea / `Simplify Conditional`.

Tras asegurar dichos errores o `code smells`, decidí crear la clase `RefactorTest`. Una vez creados los tests, comprobé que funcionaban de forma correcta: [`Evidencia`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/codeSmells/Captura%20de%20pantalla%202025-05-20%20233821.png)

---

## 3.- Desarrollo

Además, tras las pruebas, decidí arreglar el códgio gracias a las funcionalidades del IDE. (Refactor -> RefactorizaciónNecesaria):

1. Reemplazo de excepción genérica por específica: En crearEvento(), reemplacé catch (Exception) por catch (IOException) para capturar errores más relevantes y evitar capturar lógicamente errores no controlados del todo. [`Commit Arreglo`](https://github.com/moraalees/TaskManagerEntornos/commit/4edb29601781829bc2821755f7f996573a67d03e)
2. Sustitución de throw IllegalStateException por check: En Tarea.cambiarEstado(), sustituí una validación manual con throw por check, haciéndolo más propio de Kotlin y más claro semánticamente. [`Commit Arreglo`](https://github.com/moraalees/TaskManagerEntornos/commit/1b652a8820e12b43d377038a3a34c2901f42fa07)
3. Simplificación de condicional con require: En cambiarEstadoTarea(), la verificación manual del rango se reemplazó por require, reduciendo la complejidad condicional y mejorando legibilidad. [`Commit Arreglo`](https://github.com/moraalees/TaskManagerEntornos/commit/e3e382fc195f8dc4b7bab8952702f1f382ffe1ca)

---

## 4.- Preguntas

[1]
1.a ¿Qué code smell y patrones de refactorización has aplicado?


1.b Teniendo en cuenta aquella funcionalidad que tiene pruebas unitarias, selecciona un patrón de refactorización de los que has aplicado y que están cubierto por los test unitarios. ¿Porque mejora o no mejora tu código? Asegurate de poner enlaces a tu código


[2]
2.a Describe el proceso que sigues para asegurarte que la refactorización no afecta a código que ya tenias desarrollado.


[3]
3.a ¿Que funcionalidad del IDE has usado para aplicar la refactorización seleccionada? Si es necesario, añade capturas de pantalla para identificar la funcionalidad.
