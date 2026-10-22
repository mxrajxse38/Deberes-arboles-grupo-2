package negocio;

import enums.TipoRecorrido;
import modelo.Libro;

import java.util.ArrayList;
import java.util.List;

/**
 * Árbol Binario de Búsqueda de libros ordenado por código (sin repetidos).
 * Todas las operaciones son recursivas; no se usan estructuras ordenadas de Java ni sort.
 */
public class CatalogoABB {
    private NodoLibro raiz;

    public NodoLibro getRaiz() {
        return raiz;
    }

    public void setRaiz(NodoLibro raiz) {
        this.raiz = raiz;
    }

    // ---------------------------------------------------------------- inserción

    /** Inserta por código. false si el código ya existe. */
    public boolean insertar(Libro libro) {
        if (libro == null || buscar(libro.getCodigo()) != null) {
            return false;
        }
        raiz = insertar(raiz, libro);
        return true;
    }

    private NodoLibro insertar(NodoLibro nodo, Libro libro) {
        if (nodo == null) {
            return new NodoLibro(libro);
        }
        if (libro.getCodigo() < nodo.getDato().getCodigo()) {
            nodo.setIzquierdo(insertar(nodo.getIzquierdo(), libro));
        } else {
            nodo.setDerecho(insertar(nodo.getDerecho(), libro));
        }
        return nodo;
    }

    // ---------------------------------------------------------------- búsqueda

    /** Busca un libro por código. null si no existe. */
    public Libro buscar(int codigo) {
        return buscar(raiz, codigo);
    }

    private Libro buscar(NodoLibro nodo, int codigo) {
        if (nodo == null) {
            return null;
        }
        int actual = nodo.getDato().getCodigo();
        if (codigo == actual) {
            return nodo.getDato();
        }
        return codigo < actual ? buscar(nodo.getIzquierdo(), codigo) : buscar(nodo.getDerecho(), codigo);
    }

    // ---------------------------------------------------------------- conteo

    public int contar() {
        return contar(raiz);
    }

    private int contar(NodoLibro nodo) {
        if (nodo == null) {
            return 0;
        }
        return 1 + contar(nodo.getIzquierdo()) + contar(nodo.getDerecho());
    }

    // ---------------------------------------------------------------- recorridos

    public List<Libro> recorrer(TipoRecorrido tipo) {
        List<Libro> resultado = new ArrayList<>();
        recorrer(raiz, tipo, resultado);
        return resultado;
    }

    private void recorrer(NodoLibro nodo, TipoRecorrido tipo, List<Libro> resultado) {
        if (nodo == null) {
            return;
        }
        if (tipo == TipoRecorrido.PREORDEN) {
            resultado.add(nodo.getDato());
        }
        recorrer(nodo.getIzquierdo(), tipo, resultado);
        if (tipo == TipoRecorrido.INORDEN) {
            resultado.add(nodo.getDato());
        }
        recorrer(nodo.getDerecho(), tipo, resultado);
        if (tipo == TipoRecorrido.POSORDEN) {
            resultado.add(nodo.getDato());
        }
    }

    /**
     * Traza del recorrido: "entra X" al entrar al nodo, "VISITA X" al procesarlo y
     * "null -> regresa" en un hijo vacío; 2 espacios por nivel; última línea "Secuencia TIPO: ...".
     */
    public String trazaRecorrido(TipoRecorrido tipo) {
        StringBuilder sb = new StringBuilder();
        StringBuilder secuencia = new StringBuilder();
        trazar(raiz, tipo, 0, sb, secuencia);
        sb.append("Secuencia ").append(tipo).append(":").append(secuencia);
        return sb.toString();
    }

    private void trazar(NodoLibro nodo, TipoRecorrido tipo, int nivel, StringBuilder sb, StringBuilder secuencia) {
        String sangria = "  ".repeat(nivel);
        if (nodo == null) {
            sb.append(sangria).append("null -> regresa\n");
            return;
        }
        int codigo = nodo.getDato().getCodigo();
        sb.append(sangria).append("entra ").append(codigo).append("\n");
        if (tipo == TipoRecorrido.PREORDEN) {
            visitar(codigo, sangria, sb, secuencia);
        }
        trazar(nodo.getIzquierdo(), tipo, nivel + 1, sb, secuencia);
        if (tipo == TipoRecorrido.INORDEN) {
            visitar(codigo, sangria, sb, secuencia);
        }
        trazar(nodo.getDerecho(), tipo, nivel + 1, sb, secuencia);
        if (tipo == TipoRecorrido.POSORDEN) {
            visitar(codigo, sangria, sb, secuencia);
        }
    }

    private void visitar(int codigo, String sangria, StringBuilder sb, StringBuilder secuencia) {
        sb.append(sangria).append("VISITA ").append(codigo).append("\n");
        secuencia.append(" ").append(codigo);
    }

    // ---------------------------------------------------------------- modificar

    /** Modifica título, autor y año (nunca el código). false si no existe. */
    public boolean modificar(int codigo, String titulo, String autor, int anio) {
        Libro libro = buscar(codigo);
        if (libro == null) {
            return false;
        }
        new Libro(codigo, titulo, autor, anio); // valida todos los datos antes de modificar nada
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setAnio(anio);
        return true;
    }

    // ---------------------------------------------------------------- eliminar

    /** Elimina hoja, nodo con un hijo y nodo con dos hijos (reemplazo por el sucesor inorden). */
    public boolean eliminar(int codigo) {
        if (buscar(codigo) == null) {
            return false;
        }
        raiz = eliminar(raiz, codigo);
        return true;
    }

    private NodoLibro eliminar(NodoLibro nodo, int codigo) {
        if (nodo == null) {
            return null;
        }
        int actual = nodo.getDato().getCodigo();
        if (codigo < actual) {
            nodo.setIzquierdo(eliminar(nodo.getIzquierdo(), codigo));
        } else if (codigo > actual) {
            nodo.setDerecho(eliminar(nodo.getDerecho(), codigo));
        } else {
            if (nodo.getIzquierdo() == null) {
                return nodo.getDerecho();          // hoja o solo hijo derecho
            }
            if (nodo.getDerecho() == null) {
                return nodo.getIzquierdo();         // solo hijo izquierdo
            }
            NodoLibro sucesor = minimo(nodo.getDerecho());   // dos hijos: sucesor inorden
            nodo.setDato(sucesor.getDato());
            nodo.setDerecho(eliminar(nodo.getDerecho(), sucesor.getDato().getCodigo()));
        }
        return nodo;
    }

    private NodoLibro minimo(NodoLibro nodo) {
        return nodo.getIzquierdo() == null ? nodo : minimo(nodo.getIzquierdo());
    }

    // ---------------------------------------------------------------- retos (Parte D)

    /** Cuenta los códigos entre a y b (inclusive) podando las ramas que no pueden contener valores. */
    public int contarEnRango(int a, int b) {
        if (a > b) {
            return 0;
        }
        return contarEnRango(raiz, a, b);
    }

    private int contarEnRango(NodoLibro nodo, int a, int b) {
        if (nodo == null) {
            return 0;
        }
        int codigo = nodo.getDato().getCodigo();
        if (codigo < a) {
            return contarEnRango(nodo.getDerecho(), a, b);   // todo el subárbol izquierdo es < a
        }
        if (codigo > b) {
            return contarEnRango(nodo.getIzquierdo(), a, b); // todo el subárbol derecho es > b
        }
        return 1 + contarEnRango(nodo.getIzquierdo(), a, b) + contarEnRango(nodo.getDerecho(), a, b);
    }

    /** Códigos visitados desde la raíz hasta el libro (o hasta donde la búsqueda termina). */
    public List<Integer> caminoBusqueda(int codigo) {
        List<Integer> camino = new ArrayList<>();
        caminoBusqueda(raiz, codigo, camino);
        return camino;
    }

    private void caminoBusqueda(NodoLibro nodo, int codigo, List<Integer> camino) {
        if (nodo == null) {
            return;
        }
        int actual = nodo.getDato().getCodigo();
        camino.add(actual);
        if (codigo < actual) {
            caminoBusqueda(nodo.getIzquierdo(), codigo, camino);
        } else if (codigo > actual) {
            caminoBusqueda(nodo.getDerecho(), codigo, camino);
        }
    }

    /** Verifica todo el árbol con límites mínimo y máximo (no solo padre-hijo). */
    public boolean esABBValido() {
        return esABBValido(raiz, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean esABBValido(NodoLibro nodo, long min, long max) {
        if (nodo == null) {
            return true;
        }
        int codigo = nodo.getDato().getCodigo();
        if (codigo <= min || codigo >= max) {
            return false;
        }
        return esABBValido(nodo.getIzquierdo(), min, codigo) && esABBValido(nodo.getDerecho(), codigo, max);
    }

    /**
     * Extra: reconstruye el ABB a partir de su preorden en O(n), sin insertar uno por uno.
     * Los libros se crean con datos genéricos porque el preorden solo contiene códigos.
     */
    public static CatalogoABB desdePreorden(int[] preorden) {
        CatalogoABB catalogo = new CatalogoABB();
        if (preorden == null || preorden.length == 0) {
            return catalogo;
        }
        int[] indice = {0};
        catalogo.raiz = construir(preorden, indice, Long.MIN_VALUE, Long.MAX_VALUE);
        if (indice[0] != preorden.length) {
            throw new IllegalArgumentException("El arreglo no es un preorden válido de un ABB.");
        }
        return catalogo;
    }

    private static NodoLibro construir(int[] preorden, int[] indice, long min, long max) {
        if (indice[0] >= preorden.length) {
            return null;
        }
        int codigo = preorden[indice[0]];
        if (codigo <= min || codigo >= max) {
            return null;
        }
        indice[0]++;
        NodoLibro nodo = new NodoLibro(new Libro(codigo, "Libro " + codigo, "Autor desconocido", 2000));
        nodo.setIzquierdo(construir(preorden, indice, min, codigo));
        nodo.setDerecho(construir(preorden, indice, codigo, max));
        return nodo;
    }
}
