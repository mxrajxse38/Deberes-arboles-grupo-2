Catálogo de la Biblioteca FISEI con árbol general y ABB en Java
Asignatura: Estructura de Datos
Nivel: Tercero – Ingeniería de Software
Modalidad: Individual
Tipo: Práctica de programación
Herramientas: Java, Visual Studio Code y GitHub
Calificación: 3 puntos
Entrega: jueves 22 de octubre de 2026, 07:00, en Moodle
1. Objetivo
Implementar en Java un árbol general n-ario y un Árbol Binario de Búsqueda (ABB) mediante programación orientada a objetos y recursividad, aplicando operaciones de inserción, búsqueda, recorridos, eliminación y análisis de su estructura.
2. Descripción de la actividad
Caso práctico: catálogo de la Biblioteca FISEI.
La Biblioteca se organiza en Áreas y cada Área en Estantes. Esta jerarquía se representa con un árbol general n-ario.
Cada Estante almacena sus libros en un ABB ordenado por el código del libro (entero positivo, sin repetidos).
El estudiante deberá desarrollar una aplicación Java que permita construir, consultar, recorrer y modificar ambas estructuras.
3. Estructura inicial
Construya la siguiente biblioteca. Los libros se insertan en el orden indicado:
BIB  Biblioteca FISEI
 ├── SW   Software
 │    ├── SW-E1  Estante 1 de Software   →  60, 35, 80, 20, 45, 70, 90, 40, 75
 │    └── SW-E2  Estante 2 de Software   →  15, 10, 25
 ├── TI   Tecnologías de la Información
 │    └── TI-E1  Estante 1 de TI         →  50
 └── TEL  Telecomunicaciones

La biblioteca contiene 7 unidades, 4 hojas, una altura de 2 aristas y 13 libros.
4. Requerimientos de implementación
Parte A. Enumeraciones y modelo
•	TipoUnidad: BIBLIOTECA, AREA y ESTANTE, con el método puedeTenerHijo(TipoUnidad hijo).
•	TipoRecorrido: PREORDEN, INORDEN y POSORDEN.
•	Libro: codigo (entero mayor que 0, sin setter), titulo, autor y anio (1900–2026), con validaciones.
•	UnidadBiblioteca: codigo (en mayúsculas), nombre y tipo.
•	NodoUnidad: dato, lista de hijos y un CatalogoABB (solo si es ESTANTE).
•	NodoLibro: dato, izquierdo y derecho, con getters y setters.
Parte B. Clase ArbolBiblioteca
Método	Descripción
boolean agregar(String codigoPadre, UnidadBiblioteca nueva)	Agrega una unidad respetando BIBLIOTECA → AREA → ESTANTE, sin códigos repetidos.
NodoUnidad buscar(String codigo)	Busca una unidad sin distinguir mayúsculas. null si no existe.
int altura()	Altura del árbol en aristas.
int contarHojas()	Número de unidades sin hijos.
int nivel(String codigo)	Nivel de la unidad (raíz = 0). -1 si no existe.
String mostrarJerarquia()	Jerarquía en preorden, con 2 espacios por nivel.
int contarLibrosTotal()	Total de libros de todos los estantes.

Parte C. Clase CatalogoABB
Método	Descripción
boolean insertar(Libro libro)	Inserta por código. false si el código ya existe.
Libro buscar(int codigo)	Busca un libro. null si no existe.
int contar()	Número de libros del catálogo.
List<Libro> recorrer(TipoRecorrido tipo)	Recorrido en preorden, inorden o posorden.
String trazaRecorrido(TipoRecorrido tipo)	Traza del recorrido con el formato indicado abajo.
boolean modificar(int codigo, String titulo, String autor, int anio)	Modifica todo menos el código. false si no existe.
boolean eliminar(int codigo)	Elimina hoja, nodo con un hijo y nodo con dos hijos (reemplazo por el sucesor inorden).
NodoLibro getRaiz() · void setRaiz(NodoLibro raiz)	Acceso a la raíz.

Formato de la traza: entra X al entrar al nodo, VISITA X al procesarlo y null -> regresa en un hijo vacío; 2 espacios por nivel; última línea Secuencia TIPO: ….
Parte D. Retos (en CatalogoABB)
Método	Descripción
int contarEnRango(int a, int b)	Cuenta los códigos entre a y b, con poda.
List<Integer> caminoBusqueda(int codigo)	Códigos visitados desde la raíz hasta el libro.
boolean esABBValido()	Verifica todo el ABB con límites mínimo y máximo.
Extra (+0,15): static CatalogoABB desdePreorden(int[] preorden)	Reconstruye el ABB a partir de su preorden, sin insertar uno por uno.

Parte E. Programa principal
Desarrollar un menú interactivo con Scanner que permita:
•	Cargar la estructura inicial.
•	Mostrar la jerarquía.
•	Agregar y buscar unidades.
•	Consultar altura, hojas, nivel y total de libros.
•	Insertar, buscar, modificar y eliminar libros de un estante.
•	Mostrar los recorridos con su traza.
•	Ejecutar los retos.
•	Salir.
Además, la clase PruebasBiblioteca deberá ejecutar mínimo 10 casos y mostrar X/N pruebas OK.
5. Casos de prueba obligatorios
El estudiante deberá demostrar:
•	Biblioteca solo con la raíz: altura 0 y 1 hoja.
•	Biblioteca inicial: altura 2, 4 hojas y 13 libros.
•	Nivel de BIB, sw, SW-E2 y XYZ: 0, 1, 2 y -1.
•	Agregar un ESTANTE como hijo de BIBLIOTECA: false.
•	Recorridos de SW-E1 — preorden: 60 35 20 45 40 80 70 75 90; inorden: 20 35 40 45 60 70 75 80 90; posorden: 20 40 45 35 75 70 90 80 60.
•	Insertar un código repetido: false.
•	caminoBusqueda(42): [60, 35, 45, 40]. contarEnRango(40, 75): 5.
•	Insertar 42 y eliminar 20, 70 y 60, en ese orden: preorden final 75 35 45 40 42 80 90.
•	Buscar, modificar y eliminar un código inexistente: null / false.
•	Crear un libro con datos inválidos: IllegalArgumentException.
•	esABBValido() en un árbol con un nieto mal ubicado: false.
6. Organización del proyecto
src/
 ├── enums/    TipoUnidad.java, TipoRecorrido.java
 ├── modelo/   Libro.java, UnidadBiblioteca.java
 ├── negocio/  NodoUnidad.java, NodoLibro.java,
 │             ArbolBiblioteca.java, CatalogoABB.java
 └── app/      Main.java, DatosPrueba.java, PruebasBiblioteca.java

Restricciones: no se permite utilizar TreeSet, TreeMap, PriorityQueue, NavigableSet, NavigableMap, Collections.sort ni List.sort, ni ordenar listas para obtener recorridos. Todas las operaciones de los árboles deberán implementarse mediante recursividad.
7. Archivo TRAZAS.md
En el repositorio, resolver a mano:
•	Tres códigos personales: con los 4 últimos dígitos de la cédula (abcd) formar ab, bc y cd; si uno es 0, se repite o ya existe, sumarle 1.
•	Insertar la secuencia de SW-E1 y después los 3 códigos personales.
•	ABB final en Mermaid, con las aristas marcadas I (izquierdo) y D (derecho).
•	Los tres recorridos y la traza del preorden.
•	Tres eliminaciones (hoja, un hijo y dos hijos), con el árbol resultante en Mermaid.
•	Salida del programa para cada punto y si coincide o no.
8. Evidencias y entrega
Cada estudiante deberá:
•	Crear un repositorio público de GitHub con el nombre Deber-Arboles-ApellidoNombre.
•	Trabajar en la rama desarrollo y unirla a main con un pull request antes de la hora límite.
•	Realizar al menos tres commits significativos que evidencien el desarrollo individual.
•	Subir el código, README.md y TRAZAS.md.
•	Documentar en el README el problema, las instrucciones de ejecución y una captura de PruebasBiblioteca.
•	Entregar en Moodle un archivo PDF (Deber-Arboles-ApellidoNombre.pdf) con el enlace al repositorio, capturas de ejecución, capturas del historial de commits y del pull request, y la reflexión personal.
9. Preguntas de reflexión
Responder en el PDF:
•	¿Por qué buscar en el ABB es O(h) y en el árbol general es O(N)? ¿Qué le pasa a h si se insertan los códigos ya ordenados?
•	¿Por qué, al eliminar un nodo con dos hijos, se usa el sucesor inorden y no cualquier nodo del subárbol derecho?
•	¿Cómo podría utilizarse un árbol general o un ABB en el proyecto integrador SmartETL para organizar o buscar registros?
10. Rúbrica de evaluación (3 puntos)
Criterio	Puntaje
Enumeraciones, modelo y clase ArbolBiblioteca (Partes A y B)	0,60
CatalogoABB: inserción, búsqueda, recorridos y traza	0,50
CatalogoABB: modificar y eliminar con sus tres casos	0,25
Retos (Parte D)	0,45
TRAZAS.md completo	0,35
Menú funcional y PruebasBiblioteca	0,25
Repositorio GitHub: commits, rama, pull request y README	0,30
PDF de evidencias y respuestas de reflexión	0,30
TOTAL	3,00
Extra: desdePreorden (compensa, sin superar 3 puntos)	+0,15

11. Indicaciones importantes
•	La actividad es estrictamente individual. Los repositorios copiados tendrán 0.
•	El estudiante deberá comprender y explicar el funcionamiento del código presentado.
•	Se deben respetar los nombres de paquetes, clases y métodos indicados.
•	El código debe compilar y ejecutarse correctamente. Si no compila, solo se califican TRAZAS.md, el repositorio y el PDF.
•	Un método no recursivo o que use estructuras prohibidas vale 0.
•	No se califica la entrega sin PDF, sin enlace o con repositorio privado.
•	Solo se considera el estado de main a la hora límite.
•	No usar git push --force ni git reset --hard.
Producto final: aplicación Java funcional, repositorio GitHub documentado y PDF de evidencias entregado en Moodle.
