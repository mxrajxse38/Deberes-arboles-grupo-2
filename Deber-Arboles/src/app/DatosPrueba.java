package app;

import enums.TipoUnidad;
import modelo.Libro;
import modelo.UnidadBiblioteca;
import negocio.ArbolBiblioteca;
import negocio.CatalogoABB;

/** Datos de la estructura inicial de la Biblioteca FISEI. */
public class DatosPrueba {
    public static final int[] LIBROS_SW_E1 = {60, 35, 80, 20, 45, 70, 90, 40, 75};
    public static final int[] LIBROS_SW_E2 = {15, 10, 25};
    public static final int[] LIBROS_TI_E1 = {50};

    private DatosPrueba() {
    }

    /** Crea un libro con datos genéricos válidos a partir de su código. */
    public static Libro libro(int codigo) {
        return new Libro(codigo, "Libro " + codigo, "Autor " + codigo, 2000 + (codigo % 20));
    }

    /** Inserta en el catálogo los códigos indicados, en ese orden. */
    public static void cargarLibros(CatalogoABB catalogo, int[] codigos) {
        for (int codigo : codigos) {
            catalogo.insertar(libro(codigo));
        }
    }

    /** Biblioteca con solo la raíz. */
    public static ArbolBiblioteca soloRaiz() {
        return new ArbolBiblioteca(new UnidadBiblioteca("BIB", "Biblioteca FISEI", TipoUnidad.BIBLIOTECA));
    }

    /** Estructura inicial: 7 unidades, 4 hojas, altura 2 y 13 libros. */
    public static ArbolBiblioteca crearBibliotecaInicial() {
        ArbolBiblioteca arbol = soloRaiz();
        arbol.agregar("BIB", new UnidadBiblioteca("SW", "Software", TipoUnidad.AREA));
        arbol.agregar("BIB", new UnidadBiblioteca("TI", "Tecnologías de la Información", TipoUnidad.AREA));
        arbol.agregar("BIB", new UnidadBiblioteca("TEL", "Telecomunicaciones", TipoUnidad.AREA));
        arbol.agregar("SW", new UnidadBiblioteca("SW-E1", "Estante 1 de Software", TipoUnidad.ESTANTE));
        arbol.agregar("SW", new UnidadBiblioteca("SW-E2", "Estante 2 de Software", TipoUnidad.ESTANTE));
        arbol.agregar("TI", new UnidadBiblioteca("TI-E1", "Estante 1 de TI", TipoUnidad.ESTANTE));
        cargarLibros(arbol.buscar("SW-E1").getCatalogo(), LIBROS_SW_E1);
        cargarLibros(arbol.buscar("SW-E2").getCatalogo(), LIBROS_SW_E2);
        cargarLibros(arbol.buscar("TI-E1").getCatalogo(), LIBROS_TI_E1);
        return arbol;
    }
}
