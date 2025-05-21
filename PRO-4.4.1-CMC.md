# Dokka

---

## 1.- Documentación

Para este proceso, me he propuesto aplicarlo a 3 diferentes clase de mi proyecto:
- `Filtro`: Documentar los métodos y variable.
- `ActividadRepositorioEnMemoria`: Documentar sus 2 métodos y variable.
- `Evento`: Documentar sus variables inyectadas y sus métodos.

Para la documentación, usé KDoc. Mediante la funcionalidad de escribir `/** --- */`, he podido agregar varios bloques de comentarios. Más adelante agregaré las capturas.

---

## 2.- Configuración Dokka

Para poder implementar correctamente Dokka en mi proyecto, he tenido que agregar ciertas líneas en mi archivo `build.gradle.kts` [`Evidencia`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20175522.png)

A la hora de generar el archivo HTML gracias a Dokka, tuve que ingresar un comando (`.\gradlew DokkaHtml`). Gracias a este comando, se me generó en la siguiente ruta [`Ruta`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20182036.png) (`/build/dokka` - y ahí está el css, html, etc.)

### [`Comando y generación`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20175735.png)

### [`Página generada`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20175927.png)

---

## 3.- Capturas Código

- Evento:
    - [`Evento 1`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20182952.png)
    - [`Evento 2`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20183010.png)
- Filtro
    - [`Filtro 1`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20183113.png)
    - [`Filtro 2`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20183123.png)
    - [`Filtro 3`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20183146.png)
    - [`Filtro 4`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20183202.png)
- ActividadRepositorioEnMemoria
    - [`ActividadRepositorioEnMemoria`](https://github.com/moraalees/TaskManagerEntornos/blob/cristian/images/documentacion/Captura%20de%20pantalla%202025-05-21%20183257.png)
