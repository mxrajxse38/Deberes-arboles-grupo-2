# Preguntas de reflexión

## 1. ¿Por qué buscar en el ABB es O(h) y en el árbol general es O(N)? ¿Qué le pasa a h si se insertan los códigos ya ordenados?

En el ABB cada comparación descarta todo un subárbol: si el código buscado es menor que el del nodo, se
sigue solo por la izquierda; si es mayor, solo por la derecha. Se baja un nivel por paso, así que el costo
máximo es la altura **h** del árbol: O(h).

En el árbol general n-ario los hijos no tienen un orden que permita decidir hacia dónde seguir, así que
para hallar una unidad puede ser necesario revisar todos los hijos de cada nodo, es decir, potencialmente
los **N** nodos: O(N).

Si los códigos se insertan ya ordenados (p. ej. 10, 20, 30, 40...) cada nodo nuevo queda como hijo
derecho del anterior y el ABB se degenera en una lista enlazada: **h = N − 1**. La búsqueda pasa de
O(log N) (árbol equilibrado) a O(N), y la recursión puede además llegar a una profundidad de N llamadas.

## 2. ¿Por qué, al eliminar un nodo con dos hijos, se usa el sucesor inorden y no cualquier nodo del subárbol derecho?

Porque el reemplazo debe conservar la propiedad del ABB: todo lo del subárbol izquierdo < nodo < todo lo
del subárbol derecho. El sucesor inorden es el **menor** valor del subárbol derecho: es mayor que todo el
subárbol izquierdo y menor o igual que el resto del derecho. Cualquier otro nodo del subárbol derecho
dejaría valores menores que él todavía en el lado derecho, y el árbol dejaría de ser un ABB válido.
Además, el sucesor no tiene hijo izquierdo, por lo que quitarlo de su posición original es un caso
sencillo (hoja o un solo hijo).

## 3. ¿Cómo podría utilizarse un árbol general o un ABB en el proyecto integrador SmartETL?

- **Árbol general:** para representar jerarquías naturales de un proceso ETL: fuente de datos → tabla →
  columna, o pipeline → etapa → tarea. También sirve para organizar categorías o esquemas de destino y
  recorrerlos para validar o documentar cada nivel.
- **ABB:** como índice en memoria para buscar registros por una clave numérica (id de registro, código de
  cliente), detectar duplicados al cargar datos (insertar devuelve `false` si el código ya existe),
  consultar rangos de claves con poda (`contarEnRango`) y obtener los datos ordenados con un recorrido
  inorden sin ordenar listas. Con claves que llegan ordenadas convendría un árbol balanceado (AVL o
  rojo-negro) para evitar la degeneración.
