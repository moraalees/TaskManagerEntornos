[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/Sdfjb7rh)
# Actividad: Desarrollo de Proyecto Software en Kotlin

**ID actividad:** 2425_PRO_u4u5u6_taskManager

**Agrupamiento de la actividad**: Individual 

---

## **Gestor de Tareas y Eventos para Proyectos Colaborativos**

### **Contexto y Objetivo**

Desarrolla una aplicación de consola en Kotlin que permita gestionar **actividades** en un proyecto colaborativo. Estas actividades se dividen en dos tipos: **Tareas** y **Eventos**. Ambas derivan de una superclase o interfaz denominada **Actividad**.

La aplicación debe seguir una **arquitectura en capas**, separando claramente:

- **La capa de presentación (UI):** se encarga de la interacción con el usuario a través de la consola.
- **La capa de lógica de aplicación (Servicios):** gestiona la lógica de negocio (creación, almacenamiento y manejo de actividades).
- **La capa de acceso a datos:** aunque en este ejercicio utiliza un repositorio en memoria, se debe abstraer su acceso mediante interfaces, aplicando el principio de inversión de dependencias (DIP).
- **La capa de dominio (Modelo)**: contendrá los modelos de negocio y lógica central.

### **Requerimientos Funcionales y No Funcionales**

1. **Arquitectura en Capas y Principio de Inversión de Dependencias**

   - La lógica de negocio debe depender de abstracciones y no de implementaciones concretas.
   - La comunicación entre la interfaz de usuario y la lógica de negocio debe estar claramente separada.

3. **Modelo de Dominio: Actividad, Tarea y Evento**

   - **Actividad:**
     - Contendrá la lógica común a todas las actividades, aunque no se permitirá la creación de una instancia de la misma.
     - Posee un **id** (Int). Se asigna automáticamente al crear la instancia. No se puede modificar.
     - Posee una **fechaCreacion** (String). Se asigna automáticamente al crear la instancia. No se puede modificar.
     - Posee un **descripción** (String). No puede estar vacía.
     - Las propiedades no serán visibles desde fuera de la clase `Actividad`.
     - Debe incluir un método, `obtenerDetalle(): String`, que utilice la lógica común para concatenar el *id* y la descripción (`<id> + " - " + <descripcion>`).

   - **Tarea:**
     - Hereda las propiedades de Actividad.
     - Posee una propiedad **estado**, por defecto `ABIERTA`. Que toma valores de la enum class `src.main.kotlin.model.Estado = {ABIERTA, CERRADA}` 
     - Tiene un método **obtenerDetalle** que se genera dinámicamente, sobreescribiendo el método de la superclase, pero también usándolo en la info: `Tarea <id> + " - " + <descripcion> + "[src.main.kotlin.model.Estado: <estado>]"`.
     - Su constructor es **privado**. Se debe disponer de un método de clase (companion object) llamado `creaInstancia` para generar una nueva instancia.
     - Cualquier otra propiedad o método que consideres necesario. No olvides comentarlo.
   
   - **Evento:**
     - Hereda las propiedades de Actividad.
     - Tiene la propiedad **fecha**.
     - Tiene la propiedad **ubicación**.
     - Tiene un método **obtenerDetalle** que se genera dinámicamente, sobreescribiendo el método de la superclase, pero también usándolo en la info: `Evento <id> + " - " + <descripcion> + "[Fecha: <fecha>, Ubicación: <ubicacion>]"`.
     - Similar a **Tarea** en cuanto a tener un constructor privado y el método `creaInstancia`.
     - Cualquier otra propiedad o método que consideres necesario. No olvides comentarlo.

   - **Cálculo automático del id:**
     - La generación del id será común para Tarea y Evento.
     - Debe ser un valor automático y se generará a partir de la `fechaCreacion` en formato `YYYYMMDD` y un contador.
     - Debéis tener un contador por cada fechaCreacion, lo más sencillo es usar un diccionario o mapa con la fecha como la clave y el contador como valor `<String, Int>`.
     - Podéis crear un método estático `generarId()` que devuelva un nuevo id. Por si os sirve de guía os dejo la documentación del método: [Documentación del método generarId()](GenerarID.md)

4. **Buenas Prácticas y Principios SOLID**

   - Utiliza el principio de **inversión de dependencias**: la lógica de negocio no debe depender de clases concretas para el almacenamiento de las actividades.
   - Documenta y comenta el código de forma clara, y sobre todo aquellas aportaciones que no están indicadas en la descripción de la actividad.
   - Separa los métodos estáticos y asegúrate de que la creación de instancias se haga mediante el método `creaInstancia`.

6. **Interfaz de Usuario (Consola)**

   - La aplicación debe interactuar con el usuario a través de la consola, mostrando un menú que permita:
      - Crear una nueva actividad (seleccionando entre Tarea o Evento).
      - Listar todas las actividades registradas. Aplicando polimorfismo, se debe mostrar el detalle de cada actividad (id y descripción).
   - La capa de presentación debe comunicarse con la lógica de negocio a través de interfaces o abstracciones.
   - `ActividadService` debería implementar un interfaz, de manera que podamos usar esta abstracción para inyectarla en `ConsolaUI` (DIP).

7. **Lógica de Aplicación**
   - Implementa un servicio (por ejemplo, `ActividadService`) que gestione la creación, almacenamiento (en memoria) y consulta de actividades.
   - Este servicio debe depender de una interfaz de repositorio (por ejemplo, `IActividadRepository`), permitiendo cambiar la implementación del almacenamiento sin afectar la lógica de negocio.

8. Paquete extra para las Utilidades.
   - Crear el paquete utilidades para agrupar funciones auxiliares.
   - Implementar `Utils`, que debe seguir un **patrón Singleton**.
   - `Utils` contendrá una propiedad y dos métodos: [Contenido de Utils](Utils.md)
     * Utilizar el método `obtenerFechaActual` en la generación automática del valor de la propiedad `fechaCreacion` al crear una instancia de una actividad, es decir, una `Tarea` o `Evento`.
     * Utilizar el método `esFechaValida` para validar la correcta introducción de la `fecha` de un `Evento`.

### **Trabajo a realizar**

El que se deriva de la descripción anterior. No obstante, te listo algunas cosas que te pueden ayudar a resolverlo:

1. **Definición de Clases y Estructura del Proyecto**
   - Crea un paquete para cada capa: 
     - `presentacion` para la interfaz de usuario.
     - `aplicacion` o `servicios` para la lógica de negocio.
     - `datos` para la implementación del repositorio (en memoria).
     - `dominio` para definir las clases **Actividad**, **Tarea** y **Evento**.

2. **Paquete de Utilidades**
     - Crea un paquete más, `utilidades` para agrupar las funciones auxiliares reutilizables en la aplicación.

3. **Implementación del Modelo de Dominio**
   - Define la superclase o interfaz **Actividad** que incluya:
     - Cualquier lógica que consideres común a Tareas y Eventos.
     - Recuerda que sus propiedades no serán accesibles desde fuera de la clase.
   - Implementa **Tarea** y **Evento**:
     - Constructores privados: Asegúrate de que los constructores sean privados y se acceda a ellos únicamente mediante el método `creaInstancia`.
     - Propiedades: Implementa las propiedades no comunes y específicas de cada clase.
     - Método obtenerDetalle(): Sobreescribe y modifica su comportamiento de manera específica.
   
4. **Comprobación de errores en ls información introducida por el usuario:**
   - Realiza el control dentro de las clases del Modelo de Dominio.
   - Utiliza `require` para evitar que la `descripcion` y `ubicacion` esten vacías y la `fecha` del evento no sea válida.
   - Recuerda gestionar de manera correcta las excepciones que se lanzarán.

5. **Modificadores de acceso:**
   - Al terminar tu programa, intenta establecer los modificadores de acceso adecuados a la lógica final, tanto en métodos como en propiedades.
   - Las indicaciones del IDE te ayudarán bastante para esta modificación final.

6. **Desarrollo de la Lógica de Aplicación**
   - Implementa un servicio (`ActividadService`) que:
     - Utilice una interfaz de repositorio (`IActividadRepository`) para almacenar y recuperar actividades.
     - Permita la creación de nuevas actividades mediante métodos que invoquen `creaInstancia` de cada clase.
   - Recuerda aplicar el **principio de inversión de dependencias**: el servicio debe depender de la abstracción, no de una implementación concreta.

7. **Interfaz de Usuario (Consola)**
   - Crea una interfaz de usuario sencilla que muestre un menú:
     - **Opción 1:** Crear nueva actividad (se debe preguntar al usuario si desea crear una Tarea, un Evento o Cancelar la operación, si eliges 1 o 2 deberá solicitar los datos requeridos).
     - **Opción 2:** Listar todas las actividades, mostrando el detalle de cada una.
   - La capa de presentación debe invocar los métodos del servicio para realizar las operaciones solicitadas.
   - Una solución sencilla es que la clase `ConsolaUI` tenga un método dónde se muestre el menú. En tal caso, debes inyectar a `ConsolaUI` la `ActividadService` mediante una abstracción.
   - Otra posibilidad es desarrollar en la capa de aplicación una clase `GestorActividades` y mediante DIP, inyectar en ella `ConsolaUI` y `ActividadService`. Entonces esta clase será la principal y deberá tener el menú que gestiona las actividades.

8. **Documentación y Comentarios**
   - Asegúrate de comentar el código, explicando las decisiones de diseño, la aplicación de los principios SOLID (especialmente la inversión de dependencias) y el funcionamiento general del sistema.

9. **Prueba y Depuración:** Realiza pruebas para asegurarte de que tu aplicación funciona como se espera y depura cualquier error encontrado.

10. **MUY IMPORTANTE** => **Contesta a las preguntas** (Ver el punto **Preguntas para la Evaluación**)

---

### **📌 AYUDA EXTRA: ORGANIZACIÓN DEL CÓDIGO**

Existen **dos posibles enfoques** para estructurar la aplicación. Puedes elegir el que prefieras:

#### **Opción 1 - `ConsolaUI` es la clase principal**
Aquí, `ConsolaUI` maneja toda la interacción y llama a `ActividadService`.

**Posibles métodos de `ConsolaUI`:**
- `mostrarMenu()`
- `mostrar()`
- `pedirInfo()`
- `menu()`
- `crearActividad()`
- `listarActividades()`

#### **Opción 2 - `GestorActividades` es la clase principal**
En este caso, creamos `GestorActividades`, que orquesta la aplicación inyectando `ConsolaUI` y `ActividadService`.

**Posibles métodos de `GestorActividades`:**
- `menu()`
- `crearActividad()`
- `listarActividades()`

**Posibles métodos de `ConsolaUI`:**
- `mostrarMenu()`
- `mostrar()`
- `pedirInfo()`

Si decides utilizar `GestorActividades`, **colócala en el paquete `servicios`**, ya que actúa como intermediaria entre `ConsolaUI` y `ActividadService`.  

---

### Recursos

- Apuntes dados en clase sobre programación orientada a objetos, Kotlin, uso de IDEs, y manejo de librerías.
- Documentación de Kotlin y guías de uso de librerías.

### Evaluación y calificación

**RA y CE evaluados**: Resultados de Aprendizaje 2, 4, 6, 7 y Criterios de Evaluación asociados.

**Conlleva presentación**: SI

### Entrega

> **La entrega tiene que cumplir las condiciones de entrega para poder ser calificada. En caso de no cumplirlas podría calificarse como no entregada.**
>
- **Conlleva la entrega de URL a repositorio:** El contenido se entregará en un repositorio GitHub. 
- **Respuestas a las preguntas:** Deben contestarse, de manera clara y detallada en este fichero, README.md.
- **MUY IMPORTANTE!!** Incluir un subapartado ("Respuestas a las preguntas planteadas") dónde se resuelvan las preguntas de evaluación que os realizamos a continuación. De forma clara y detallada, incluyendo **OBLIGATORIAMENTE** enlaces a fragmentos de código que justifiquen vuestras respuestas.

---

# PREGUNTAS PARA LA EVALUACIÓN

Este conjunto de preguntas está diseñado para ayudarte a reflexionar sobre cómo has aplicado los criterios de evaluación en tu proyecto. Al responderlas, **asegúrate de hacer referencia y enlazar al código relevante**, facilitando así la evaluación de tu trabajo.

### **Criterio global 1: Instancia objetos y hacer uso de ellos**
- **(2.a, 2.b, 2.c, 2.d, 2.f, 2.h, 4.e, 4.f)**: Describe cómo has instanciado y utilizado los objetos en tu proyecto. ¿Cómo has aplicado los constructores públicos y constructores privados? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código donde usas constructores publicos y privados y les pasas parámetros.
- He usado constructores privados en Tarea y en Evento, asi evito que se puedan crear instancias directamente. En su lugar hago uso de la función creaInstancia.
  
https://github.com/IES-Rafael-Alberti/dam1-2425-u4u5u6-taskmanager-FranciscoLG241/blob/master/src/main/kotlin/Actividad.kt#L43-L59

- Aqui vemos como creaInstancia valida la descripcion antes de crear una nueva Tarea.




 

### **Criterio global 2: Crear y llamar métodos estáticos**
- **(4.h)**: ¿Has definido algún método/propiedad estático en tu proyecto? Enumera TODOS los métodos y propiedades estáticas de tu proyecto. ¿Cuál era el objetivo y por qué consideraste que debía ser estático en lugar de un método/propiedad de instancia? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código.
- **(2.e)**: ¿En qué parte del código se llama a un método estático o se utiliza la propiedad estática? Proporciona obligatoriamente ejemplos específicos de fragmentos de tu código.





### **Criterio global 3: Uso de entornos**
- **(2.i)**: ¿Cómo utilizaste el IDE para el desarrollo de tu proyecto? Describe muy brevemente la estructura de tu proyecto.
- Para el desarrollo del proyecto, he optado por empezar desde lo mas específico a lo mas general, es decir, he comenzado con las superclases hasta llegar a la interfaz del menú. Aparte de esto, he hecho uso
de los breakpoints (puntos de ruptura) para ir viendo poco a poco como iba funcionando el código, además de apoyarme de librerías externas para manejar con mayor facilidad las funciones que se basan en
tiempo o fechas.






### **Criterio global 4: Definir clases y su contenido**
- **(4.a, 4.b, 4.c, 4.d, 4.g)**: Explica sobre un fragmento enlazado a tu código, cómo definiste las clases en tu proyecto, es decir como identificaste las de propiedades, métodos y constructores y modificadores del control de acceso a métodos y propiedades, para representar al objeto del mundo real. ¿Cómo contribuyen estas clases a la solución del problema que tu aplicación aborda, es decir a que paquete pertenecen y porque?
- Para responder a esta pregunta, voy a poner de ejemplo a la clase Actividad:

https://github.com/IES-Rafael-Alberti/dam1-2425-u4u5u6-taskmanager-FranciscoLG241/blob/master/src/main/kotlin/Actividad.kt#L9-L36

- En este caso, la clase (superclase) Actividad contiene la función obtener detalles, que despues será heredada por las clases Tarea y Evento, asegurando de esta manera el polimorfismo. Que me servirá para poder sobreescribirla y adaptarla a Tarea y Eventos.







### **Criterio global 5: Herencia y uso de clases abstractas e interfaces**
- **(4.g, 7.a, 7.b, 7.c, 7.i, 7.j)**: Explica sobre un fragmento enlazado a tu código cómo has implementado la herencia y/o utilizado interfaces en tu proyecto. ¿Por qué elegiste este enfoque y cómo beneficia a la estructura de tu aplicación? ¿De qué manera has utilizado los principios SOLID para mejorar el diseño de tu proyecto? Mostrando tu código, contesta qué principios has utilizado y qué beneficio has obtenido. Recuerda los que hay, porque seguro que has utilizado más de uno.
- En este proyecto he usado la herencia varias veces, ya que tiene varias ventajas, la que mas veo favorable es el hecho de poder sobreescribir las funciones que heredan de la superclase como puede ser
tanto en Actividades como en la interfaz IActividadRepository de las que heredan ActividadRepositorioEnMemoria y ActividadServicios. Pero voy a poner de ejemplo el caso de Actividades que es el que es mas adecuado para explicar.

https://github.com/IES-Rafael-Alberti/dam1-2425-u4u5u6-taskmanager-FranciscoLG241/blob/master/src/main/kotlin/Actividad.kt#L9-L18
https://github.com/IES-Rafael-Alberti/dam1-2425-u4u5u6-taskmanager-FranciscoLG241/blob/master/src/main/kotlin/Actividad.kt#L43-L56
https://github.com/IES-Rafael-Alberti/dam1-2425-u4u5u6-taskmanager-FranciscoLG241/blob/master/src/main/kotlin/Actividad.kt#L68-L90

- Como podemos observar en la función de obtenerDetalles la he sobreescribido para cada clase, ajustándola para que contenga la información que debe mostrar cada una.

- En cuanto a los principios SOLID, se ha cumplido el de Single Responsability, este principio establece que una clase debe tener una única razón para cambiar, es decir, debe tener una sola responsabilidad.
El principio Open/Closed, ya que son capaces de extender el comportamiento de una clase sin modificar su código base. Otro sería el Liskov Substitution Principle con Tarea y Evento que pueden usarse en lugar de Actividad y por último sería Interface Segregation ya que IActividadRepository solo expone métodos necesarios para el programa.







### **Criterio global 6: Diseño de jerarquía de clases**
- **(7.d, 7.e, 7.f, 7.g)**: Presenta la jerarquía de clases que diseñaste, proporciona obligatoriamente ejemplos específicos de fragmentos de tu código. ¿Cómo probaste y depuraste esta jerarquía para asegurar su correcto funcionamiento? ¿Qué tipo de herencia has utilizado: Especificación, Especialización, Extensión, Construcción? (Ver [Tipo de Herencia](TipoHerencia.md))
- En este proyecto la herencia que he utilizado es la de especialización (en la clase de Actividad, Tarea y Eventos). Esto se debe a que la clase hija (Tarea y Eventos es una versión más concreta de la clase padre. En cuanto a la depuración la he usado a lo largo del proyecto, también una vez finalizado el programa para ver si funcionaba como debía o si había algun error que pudiera corregir.

https://github.com/IES-Rafael-Alberti/dam1-2425-u4u5u6-taskmanager-FranciscoLG241/blob/master/src/main/kotlin/Actividad.kt#L266-L274








### **Criterio global 7: Documentado**
- **(7.h)**: Muestra enlaces a fragmentos de tu código en donde se vean ejemplos de cómo has documentado y comentado tu código. Explica la diferencia entre documentar y comentar. ¿Siempre es necesario documentar una aplicación o es más limpio y claro no hacerlo? ¿Realizarías **comentarios** para todas las líneas de código para que se entendiera mejor? o ¿Qué código comentarías y por qué?

https://github.com/IES-Rafael-Alberti/dam1-2425-u4u5u6-taskmanager-FranciscoLG241/blob/master/src/main/kotlin/Actividad.kt#L8-L42

- Comentar el código se refiere a agregar notas breves dentro del código para explicar partes específicas o decisiones tomadas, enfocado en el funcionamiento de una sección concreta. Mientras que documentar el código es proporcionar descripciones más detalladas y estructuradas, generalmente sobre clases, funciones o módulos completos, explicando su propósito, parámetros, y cómo se deben usar. Siempre es mejor documentar tu código para así los futuros lectores del código puedan entender de mejor manera el código.
