package negocio;

import enums.TipoUnidad;
import modelo.UnidadBiblioteca;

import java.util.ArrayList;
import java.util.List;

/** Nodo del árbol general n-ario. Si la unidad es un ESTANTE, guarda un CatalogoABB. */
public class NodoUnidad {
    private final UnidadBiblioteca dato;
    private final List<NodoUnidad> hijos = new ArrayList<>();
    private CatalogoABB catalogo;

    public NodoUnidad(UnidadBiblioteca dato) {
        this.dato = dato;
        if (dato.getTipo() == TipoUnidad.ESTANTE) {
            this.catalogo = new CatalogoABB();
        }
    }

    public UnidadBiblioteca getDato() {
        return dato;
    }

    public List<NodoUnidad> getHijos() {
        return hijos;
    }

    /** Catálogo de libros; null si la unidad no es un ESTANTE. */
    public CatalogoABB getCatalogo() {
        return catalogo;
    }
}
