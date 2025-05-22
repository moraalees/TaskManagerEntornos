# Informe de Revisión y Refactorización de Código

## 1. Revisión de código

Para llevar a cabo una mejora en la calidad interna del proyecto, el primer paso fue realizar una inspección manual del código con el propósito de detectar posibles *code smells*. Para ello, utilicé inicialmente la herramienta de inspección integrada de IntelliJ IDEA a través de la opción `Code > Inspect Code`. Este lanzó algunos avisos relevantes.

En búsqueda de un análisis más preciso, recurrí al uso del *linter* **Detekt**, ya utilizado en proyectos anteriores. Al ejecutar el siguiente comando:

```bash
.\gradlew detekt
```

## Análisis de Problemas en el Código

| Nº | Tipo de Incidencia                       | Ubicación en el Proyecto                     | Solución Técnica Propuesta                          |
|----|-------------------------------------------|-----------------------------------------------|------------------------------------------------------|
| 1  | Manejo de errores sin especificar         | `ConsolaUI.kt`, líneas 69, 89, 154            | Sustituir por capturas de excepciones concretas      |
| 2  | Clase con finalidad estática mal definida | `Filtro.kt`                                   | Transformar a objeto tipo `object` (patrón Singleton)|
| 3  | Lógica con múltiples rutas de salida      | `ConsolaUI.kt`, función `cambiarEstadoTarea`  | Simplificar con condiciones claras o método auxiliar |
| 4  | Campo privado sin uso efectivo            | `Evento.kt`, atributo `fechaNumerica`         | Eliminar propiedad innecesaria                       |
| 5  | Uso de importaciones globales             | `ConsolaUI.kt`, línea 3                       | Reemplazar con imports individuales                  |

---

## Refactorización Aplicada

Tras revisar los puntos detectados, seleccioné tres de ellos para aplicar refactorización efectiva.

Para validar los cambios introducidos, desarrollé una clase de pruebas denominada `Test`, donde verifiqué que el comportamiento funcional no se viera afectado por las modificaciones realizadas.

### 2. Detalles de Refactorizaciones

| Cambio Realizado                      | Descripción del Problema                  | Técnica Aplicada                                 | Archivo Afectado |
|--------------------------------------|--------------------------------------------|--------------------------------------------------|------------------|
| 1. Captura más específica de errores | Captura genérica de excepciones            | Aplicación de `Replace Generic with Specific Catch` | `ConsolaUI.kt`   |
| 2. Reorganización de lógica compleja | Exceso de condiciones y retornos múltiples | Uso de `Simplify Logic` con cláusulas de guarda     | `ConsolaUI.kt`   |
| 3. Rediseño de clase sin estado      | Clase con solo funciones utilitarias       | Conversión a objeto Singleton (`object`)           | `Filtro.kt`      |

Estas acciones permitieron reducir la complejidad del código, mejorar su estructura y facilitar su comprensión.
[Test](https://github.com/moraalees/TaskManagerEntornos/blob/Fran/src/test/Test.kt)

---

## Preguntas
### [1]
- 1.a ¿Qué code smell y patrones de refactorización has aplicado?
He identificado varios *code smells*, como capturas de excepciones genéricas, lógica con muchos `return`, clases utilitarias mal definidas y propiedades sin uso. Para cada uno, apliqué patrones de refactorización como:  
- **Replace Generic with Specific Catch**  
- **Simplify Conditional**  
- **Convert Class to Singleton**  
- **Remove Dead Code**

- 1.b Teniendo en cuenta aquella funcionalidad que tiene pruebas unitarias, selecciona un patrón de refactorización de los que has aplicado y que están cubierto por los test unitarios. ¿Porque mejora o no mejora tu código? Asegurate de poner enlaces a tu código
Selecciono el patrón **Replace Exception with Specific Exception**, perteneciente a la clase `crearTarea()`.


```kotlin
catch (e: IllegalArgumentException) {
    println("Error de validación: ${e.message}")
} catch (e: Exception) {
    println("Error inesperado al crear la tarea: ${e.message}")
}
```

Este cambio mejora el código porque hace que el manejo de errores sea más claro y controlado, a la vez que mejora la legibilidad del código.

---

### [2]
- 2.a Describe el proceso que sigues para asegurarte que la refactorización no afecta a código que ya tenias desarrollado.
Después de hacer la refactorización, ejecuto todos los **tests unitarios** para comprobar que todo sigue funcionando como debe. También reviso manualmente que el comportamiento siga siendo el mismo, sobre todo en los métodos modificados.

---

### [3]
- 3.a ¿Que funcionalidad del IDE has usado para aplicar la refactorización seleccionada? Si es necesario, añade capturas de pantalla para identificar la funcionalidad.
Usé varias funcionalidades del IntelliJ IDEA para aplicar refactorizaciones. En concreto, utilicé:  
- `Alt + Enter` para sugerencias rápidas  
- `Code > Reformat Code` para limpieza  
- `Refactor > Rename` o `Refactor > Extract Method` cuando hice simplificación de condiciones
