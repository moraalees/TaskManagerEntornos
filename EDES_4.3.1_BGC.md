##1.-Identifica los errores 
He elegido los siguientes errores de mi código.
1.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\model\Estado.kt:5:2: The file C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\model\Estado.kt is not ending with a new line. [NewLineAtEndOfFile]   

2.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\model\Filtro.kt:6:1: The class Filtro only contains utility functions. Consider defining it as an object. [UtilityClassWithPublicConstructor]

3.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\ui\ConsolaUI.kt:122:17: Function asignarTareaAUsuario has 4 return statements which exceeds the limit of 2. [ReturnCount]

4.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\main\kotlin\ui\ConsolaUI.kt:195:17: Function gestionarSubtareas has 3 return statements which exceeds the limit of 2. [ReturnCount]

5.-C:\Users\gomez\OneDrive\Escritorio\fullset\Alberti\Entornos de desarrollo\taskmanager_kotest\TaskManagerEntornos\src\test\kotlin\ActividadServiciosTest.kt:6:1: io.mockk.* is a wildcard import. Replace it with fully qualified imports. [WildcardImport]
En este error solo  cambie * en los imports porque IntelliJ lo marcan como mala práctica. En su lugar, puse solamente los imports de las funciones de mockk que realmente estoy utilizando, como mockk, every, verify... Así el código es más limpio, más claro, y cumple con las reglas de estilo del proyecto o del linter.
