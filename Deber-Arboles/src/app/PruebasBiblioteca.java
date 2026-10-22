package app;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import modelo.Libro;
import modelo.UnidadBiblioteca;
import negocio.ArbolBiblioteca;
import negocio.CatalogoABB;
import negocio.NodoLibro;

import java.util.Arrays;
import java.util.List;

/** Casos de prueba obligatorios. Imprime "X/N pruebas OK". */
public class PruebasBiblioteca {
    private static int total = 0;
    private static int correctas = 0;

    public static void main(String[] args) {
        System.out.println("===== PRUEBAS BIBLIOTECA FISEI =====");

        ArbolBiblioteca solo = DatosPrueba.soloRaiz();
        verificar("Biblioteca solo con la raíz: altura 0 y 1 hoja",
                solo.altura() == 0 && solo.contarHojas() == 1);

        ArbolBiblioteca bib = DatosPrueba.crearBibliotecaInicial();
        verificar("Biblioteca inicial: altura 2", bib.altura() == 2);
        verificar("Biblioteca inicial: 4 hojas y 7 unidades", bib.contarHojas() == 4 && bib.contarUnidades() == 7);
        verificar("Biblioteca inicial: 13 libros", bib.contarLibrosTotal() == 13);
        verificar("Niveles de BIB, sw, SW-E2 y XYZ: 0, 1, 2 y -1",
                bib.nivel("BIB") == 0 && bib.nivel("sw") == 1 && bib.nivel("SW-E2") == 2 && bib.nivel("XYZ") == -1);
        verificar("Agregar un ESTANTE como hijo de BIBLIOTECA: false",
                !bib.agregar("BIB", new UnidadBiblioteca("E-X", "Estante suelto", TipoUnidad.ESTANTE)));
        verificar("Agregar código de unidad repetido: false",
                !bib.agregar("BIB", new UnidadBiblioteca("sw", "Otra área", TipoUnidad.AREA)));
        verificar("Buscar unidad sin distinguir mayúsculas / inexistente",
                bib.buscar("sw-e1") != null && bib.buscar("NOEXISTE") == null);

        CatalogoABB sw1 = bib.buscar("SW-E1").getCatalogo();
        verificar("SW-E1 preorden: 60 35 20 45 40 80 70 75 90",
                secuencia(sw1, TipoRecorrido.PREORDEN).equals("60 35 20 45 40 80 70 75 90"));
        verificar("SW-E1 inorden: 20 35 40 45 60 70 75 80 90",
                secuencia(sw1, TipoRecorrido.INORDEN).equals("20 35 40 45 60 70 75 80 90"));
        verificar("SW-E1 posorden: 20 40 45 35 75 70 90 80 60",
                secuencia(sw1, TipoRecorrido.POSORDEN).equals("20 40 45 35 75 70 90 80 60"));
        verificar("Traza termina con la línea Secuencia",
                sw1.trazaRecorrido(TipoRecorrido.INORDEN).endsWith("Secuencia INORDEN: 20 35 40 45 60 70 75 80 90"));
        verificar("Insertar un código repetido: false", !sw1.insertar(DatosPrueba.libro(60)));
        verificar("caminoBusqueda(42): [60, 35, 45, 40]",
                sw1.caminoBusqueda(42).equals(Arrays.asList(60, 35, 45, 40)));
        verificar("contarEnRango(40, 75): 5", sw1.contarEnRango(40, 75) == 5);

        sw1.insertar(DatosPrueba.libro(42));
        sw1.eliminar(20);
        sw1.eliminar(70);
        sw1.eliminar(60);
        verificar("Insertar 42 y eliminar 20, 70 y 60: preorden 75 35 45 40 42 80 90",
                secuencia(sw1, TipoRecorrido.PREORDEN).equals("75 35 45 40 42 80 90"));
        verificar("El ABB sigue siendo válido tras eliminar", sw1.esABBValido());

        CatalogoABB vacio = new CatalogoABB();
        verificar("Buscar, modificar y eliminar código inexistente: null / false",
                vacio.buscar(999) == null && !vacio.modificar(999, "T", "A", 2000) && !vacio.eliminar(999));
        verificar("Modificar libro existente (sin cambiar el código)",
                sw1.modificar(75, "Nuevo título", "Nuevo autor", 2020)
                        && sw1.buscar(75).getTitulo().equals("Nuevo título"));

        boolean lanza = false;
        try {
            new Libro(-5, "", "", 1800);
        } catch (IllegalArgumentException e) {
            lanza = true;
        }
        verificar("Libro con datos inválidos: IllegalArgumentException", lanza);

        CatalogoABB malo = new CatalogoABB();
        NodoLibro raiz = new NodoLibro(DatosPrueba.libro(50));
        raiz.setIzquierdo(new NodoLibro(DatosPrueba.libro(30)));
        raiz.getIzquierdo().setDerecho(new NodoLibro(DatosPrueba.libro(60))); // nieto mal ubicado
        malo.setRaiz(raiz);
        verificar("esABBValido() con un nieto mal ubicado: false", !malo.esABBValido());

        CatalogoABB reconstruido = CatalogoABB.desdePreorden(new int[]{60, 35, 20, 45, 40, 80, 70, 75, 90});
        verificar("Extra desdePreorden: reconstruye el ABB de SW-E1",
                secuencia(reconstruido, TipoRecorrido.INORDEN).equals("20 35 40 45 60 70 75 80 90")
                        && reconstruido.esABBValido());

        System.out.println("\n" + correctas + "/" + total + " pruebas OK");
    }

    private static String secuencia(CatalogoABB catalogo, TipoRecorrido tipo) {
        List<Libro> libros = catalogo.recorrer(tipo);
        StringBuilder sb = new StringBuilder();
        for (Libro libro : libros) {
            if (sb.length() > 0) {
                sb.append(" ");
            }
            sb.append(libro.getCodigo());
        }
        return sb.toString();
    }

    private static void verificar(String descripcion, boolean ok) {
        total++;
        if (ok) {
            correctas++;
        }
        System.out.println((ok ? "[OK]    " : "[FALLA] ") + descripcion);
    }
}
