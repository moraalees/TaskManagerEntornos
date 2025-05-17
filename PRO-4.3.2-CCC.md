### 1.Revisión del código:

| Nº | Code Smell                               | Archivo / Línea                         | Refactorización recomendada                                  |
| -- | ---------------------------------------- | --------------------------------------- | ------------------------------------------------------------ |
| 1  | Captura de excepción genérica            | `ConsolaUI.kt` líneas 69, 89, 154       | **Replace Exception with Specific Exceptions**               |
| 2  | Clase utilitaria con constructor público | `Filtro.kt`                             | **Convert Class to Singleton (object)**                      |
| 3  | Funciones con múltiples return           | `ConsolaUI.kt` (`asignarTareaAUsuario`) | **Simplify Conditional / Replace Return with Guard Clauses** |
| 4  | Propiedad privada no usada               | `Evento.kt` (`fechaNumerica`)           | **Remove Dead Code**                                         |
| 5  | Importaciones con comodín                | `ConsolaUI.kt` línea 3                  | **Replace Wildcard Imports with Explicit Imports**           |

### 2.Seleccion de refactorizadores

| Refactorización                  | Code Smell relacionado                      | Patrón aplicado                             | Archivo objetivo |
| ---------------------------------| ------------------------------------------- | ------------------------------------------- | ---------------- |
| 1. Simplify Conditional          | Múltiples return                            | `Simplify Conditional` (o Guard Clauses)    | `ConsolaUI.kt`   |
| 2. Singleton Utility Object      | Clase utilitaria con solo métodos estáticos | `Convert Class to Singleton`                | `Filtro.kt`      |
| 3. Reemplazar excepción genérica | `catch (e: Exception)`                      | `Replace Exception with Specific Exception` | `ConsolaUI.kt`   |

### 3.Desarrollo de pruebas:
1:

[Prueba Unitaria](https://github.com/moraalees/TaskManagerEntornos/blob/Carlos/src/test/kotlin/ActividadServiciosTest.kt)

[Captura del test ejecutado correctamente](https://github.com/moraalees/TaskManagerEntornos/blob/Carlos/images/pruebaUnitaria/pruebaUnitaria1.png)

2:

[Prueba Unitaria](https://github.com/moraalees/TaskManagerEntornos/blob/Carlos/src/test/kotlin/FiltroTest.kt)

[Captura del test ejecutado correctamente](https://github.com/moraalees/TaskManagerEntornos/blob/Carlos/images/pruebaUnitaria/pruebaUnitaria2.png)

3:

[Prueba Unitaria](https://github.com/moraalees/TaskManagerEntornos/blob/Carlos/src/test/kotlin/ConsolaUITest.kt)

[Captura del test ejecutado correctamente](https://github.com/moraalees/TaskManagerEntornos/blob/Carlos/images/pruebaUnitaria/pruebaUnitaria3.png)

# Preguntas

##  \[1] Code smells y patrones de refactorización

### 1.a ¿Qué *code smell* y patrones de refactorización has aplicado?

He detectado y abordado los siguientes *code smells* y aplicado sus correspondientes patrones de refactorización:

| **Code Smell**                  | **Refactor aplicado**                                |
| ------------------------------- | ---------------------------------------------------- |
| `catch (e: Exception)` genérico | *Replace Exception with Specific Exception*          |
| Dependencia a `System.in/out`   | *Extract Method / Introduce Test Double*             |
| Lógica difícil de testear       | *Separate UI from Logic* / *Encapsulate Conditional* |
| Código repetido en tests        | *Introduce Helper Method* (`mockReadLines`)          |

---

### 1.b Selecciona un patrón cubierto por tests:

#### Refactor: `Replace Exception with Specific Exception` (en `crearTarea()`)

**Código antes del refactor:**

```kotlin
catch (e: Exception) {
    println("❌ Error al crear la tarea: ${e.message}")
}
```

**Código después del refactor:**

```kotlin
catch (e: IllegalArgumentException) {
    println("❌ Error de validación: ${e.message}")
} catch (e: Exception) {
    println("❌ Error inesperado al crear la tarea: ${e.message}")
}
```

Este cambio está cubierto por pruebas unitarias, como por ejemplo:

```kotlin
@Test
fun `crearTarea captura IllegalArgumentException`() {
    // ...
    every { Tarea.creaInstancia("Descripción válida", "etiquetas") } throws IllegalArgumentException("Error de validación")
    // ...
    Assertions.assertTrue(output.contains("❌ Error de validación: Error de validación"))
}
```

**¿Por qué mejora el código?**

* Mejora la legibilidad y precisión del manejo de errores.
* Ayuda a capturar errores esperados de validación sin ocultar excepciones graves.
* Facilita los test unitarios, ya que se pueden simular excepciones específicas.

---

##  \[2] Proceso para validar la refactorización

### 2.a ¿Cómo aseguras que la refactorización no rompe código existente?

1. **Antes del refactor**, ejecuto todos los tests existentes para asegurarme de que están en verde.
2. **Aplico refactorizaciones pequeñas y atómicas**, una a una.
3. **Vuelvo a ejecutar los tests** tras cada cambio para detectar errores rápidamente.
4. En caso de refactor que afecte a entrada/salida, **simulo `readLine()` y capturo `System.out`** para mantener los tests controlados.
5. **Cobertura de test**: uso `verify { ... }` para asegurarme de que los métodos son invocados correctamente.

---

##  \[3] Funcionalidades del IDE utilizadas

### 3.a ¿Qué funcionalidad del IDE has usado?

He usado **IntelliJ IDEA** para aplicar las siguientes refactorizaciones:

* **Alt+Enter → Replace catch with specific exception**

  * Sugiere automáticamente reemplazar `Exception` con una excepción más precisa.
* **Ctrl+Alt+M → Extract Method**

  * Para extraer lógica repetida o hacer funciones más legibles (como `mockReadLines()`).
* **Shift+Shift → Buscar métodos para navegar más fácilmente por el código.**
* **Alt+Insert → Generar código (constructores, test, etc.)**

