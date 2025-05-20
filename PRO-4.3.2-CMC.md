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
