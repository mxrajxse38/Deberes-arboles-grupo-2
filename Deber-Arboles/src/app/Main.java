package app;

import enums.TipoRecorrido;
import enums.TipoUnidad;
import modelo.Libro;
import modelo.UnidadBiblioteca;
import negocio.ArbolBiblioteca;
import negocio.CatalogoABB;
import negocio.NodoUnidad;

import java.util.List;
import java.util.Scanner;

/** Menú interactivo del catálogo de la Biblioteca FISEI. */
public class Main {
    private static final Scanner SC = new Scanner(System.in);
    private static ArbolBiblioteca biblioteca = DatosPrueba.crearBibliotecaInicial();

    public static void main(String[] args) {
        int opcion;
        do {
            mostrarMenu();
            opcion = leerEntero("Elija una opción: ");
            try {
                ejecutar(opcion);
            } catch (IllegalArgumentException e) {
                System.out.println("Datos inválidos: " + e.getMessage());
            }
        } while (opcion != 0);
        System.out.println("Hasta pronto.");
    }

    private static void mostrarMenu() {
        System.out.println("\n===== BIBLIOTECA FISEI =====");
        System.out.println(" 1. Cargar estructura inicial");
        System.out.println(" 2. Mostrar jerarquía");
        System.out.println(" 3. Agregar unidad");
        System.out.println(" 4. Buscar unidad");
        System.out.println(" 5. Consultar altura, hojas y total de libros");
        System.out.println(" 6. Consultar nivel de una unidad");
        System.out.println(" 7. Insertar libro en un estante");
        System.out.println(" 8. Buscar libro en un estante");
        System.out.println(" 9. Modificar libro de un estante");
        System.out.println("10. Eliminar libro de un estante");
        System.out.println("11. Mostrar recorridos con su traza");
        System.out.println("12. Ejecutar retos (rango, camino, validar ABB)");
        System.out.println(" 0. Salir");
    }

    private static void ejecutar(int opcion) {
        switch (opcion) {
            case 1:
                biblioteca = DatosPrueba.crearBibliotecaInicial();
                System.out.println("Estructura inicial cargada.");
                break;
            case 2:
                System.out.print(biblioteca.mostrarJerarquia());
                break;
            case 3:
                agregarUnidad();
                break;
            case 4:
                buscarUnidad();
                break;
            case 5:
                System.out.println("Altura (aristas): " + biblioteca.altura());
                System.out.println("Hojas: " + biblioteca.contarHojas());
                System.out.println("Unidades: " + biblioteca.contarUnidades());
                System.out.println("Total de libros: " + biblioteca.contarLibrosTotal());
                break;
            case 6:
                String codigo = leerTexto("Código de la unidad: ");
                System.out.println("Nivel: " + biblioteca.nivel(codigo));
                break;
            case 7:
                insertarLibro();
                break;
            case 8:
                buscarLibro();
                break;
            case 9:
                modificarLibro();
                break;
            case 10:
                eliminarLibro();
                break;
            case 11:
                mostrarRecorridos();
                break;
            case 12:
                ejecutarRetos();
                break;
            case 0:
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    // ------------------------------------------------------------------ unidades

    private static void agregarUnidad() {
        String padre = leerTexto("Código de la unidad padre: ");
        String codigo = leerTexto("Código de la nueva unidad: ");
        String nombre = leerTexto("Nombre: ");
        TipoUnidad tipo = leerTipoUnidad();
        if (tipo == null) {
            return;
        }
        boolean ok = biblioteca.agregar(padre, new UnidadBiblioteca(codigo, nombre, tipo));
        System.out.println(ok ? "Unidad agregada."
                : "No se pudo agregar (padre inexistente, código repetido o jerarquía no permitida).");
    }

    private static TipoUnidad leerTipoUnidad() {
        int t = leerEntero("Tipo (1=BIBLIOTECA, 2=AREA, 3=ESTANTE): ");
        switch (t) {
            case 1:
                return TipoUnidad.BIBLIOTECA;
            case 2:
                return TipoUnidad.AREA;
            case 3:
                return TipoUnidad.ESTANTE;
            default:
                System.out.println("Tipo no válido.");
                return null;
        }
    }

    private static void buscarUnidad() {
        NodoUnidad nodo = biblioteca.buscar(leerTexto("Código de la unidad: "));
        if (nodo == null) {
            System.out.println("La unidad no existe.");
        } else {
            System.out.println("Encontrada: " + nodo.getDato() + " | hijos: " + nodo.getHijos().size());
        }
    }

    // ------------------------------------------------------------------ libros

    /** Pide el código de un estante y devuelve su catálogo, o null si no es válido. */
    private static CatalogoABB pedirCatalogo() {
        String codigo = leerTexto("Código del estante: ");
        NodoUnidad nodo = biblioteca.buscar(codigo);
        if (nodo == null) {
            System.out.println("La unidad no existe.");
            return null;
        }
        if (nodo.getCatalogo() == null) {
            System.out.println("La unidad no es un ESTANTE.");
            return null;
        }
        return nodo.getCatalogo();
    }

    private static void insertarLibro() {
        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        int codigo = leerEntero("Código del libro: ");
        String titulo = leerTexto("Título: ");
        String autor = leerTexto("Autor: ");
        int anio = leerEntero("Año (1900-2026): ");
        boolean ok = catalogo.insertar(new Libro(codigo, titulo, autor, anio));
        System.out.println(ok ? "Libro insertado." : "El código ya existe en el catálogo.");
    }

    private static void buscarLibro() {
        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        Libro libro = catalogo.buscar(leerEntero("Código del libro: "));
        System.out.println(libro == null ? "El libro no existe." : "Encontrado: " + libro);
    }

    private static void modificarLibro() {
        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        int codigo = leerEntero("Código del libro a modificar: ");
        String titulo = leerTexto("Nuevo título: ");
        String autor = leerTexto("Nuevo autor: ");
        int anio = leerEntero("Nuevo año (1900-2026): ");
        System.out.println(catalogo.modificar(codigo, titulo, autor, anio) ? "Libro modificado." : "El libro no existe.");
    }

    private static void eliminarLibro() {
        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        System.out.println(catalogo.eliminar(leerEntero("Código del libro a eliminar: "))
                ? "Libro eliminado." : "El libro no existe.");
    }

    private static void mostrarRecorridos() {
        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        for (TipoRecorrido tipo : TipoRecorrido.values()) {
            List<Libro> libros = catalogo.recorrer(tipo);
            System.out.println("\n--- " + tipo + " (" + libros.size() + " libros) ---");
            System.out.println(catalogo.trazaRecorrido(tipo));
        }
    }

    private static void ejecutarRetos() {
        CatalogoABB catalogo = pedirCatalogo();
        if (catalogo == null) {
            return;
        }
        int a = leerEntero("Rango - límite inferior a: ");
        int b = leerEntero("Rango - límite superior b: ");
        System.out.println("contarEnRango(" + a + ", " + b + ") = " + catalogo.contarEnRango(a, b));
        int codigo = leerEntero("Código para caminoBusqueda: ");
        System.out.println("caminoBusqueda(" + codigo + ") = " + catalogo.caminoBusqueda(codigo));
        System.out.println("esABBValido() = " + catalogo.esABBValido());
    }

    // ------------------------------------------------------------------ entrada

    private static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return SC.nextLine().trim();
    }

    private static int leerEntero(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String linea = SC.nextLine().trim();
            try {
                return Integer.parseInt(linea);
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }
}
