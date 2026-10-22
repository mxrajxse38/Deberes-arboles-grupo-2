package app;

import enums.TipoRecorrido;
import modelo.Libro;
import negocio.CatalogoABB;

/**
 * Apoyo para TRAZAS.md: inserta la secuencia de SW-E1 y los códigos recibidos como argumentos,
 * muestra los tres recorridos y la traza del preorden, y luego elimina los códigos dados con "-".
 * Uso: java app.EjemploTrazas 12 23 34 -75 -23 -35
 */
public class EjemploTrazas {
    public static void main(String[] args) {
        CatalogoABB catalogo = new CatalogoABB();
        DatosPrueba.cargarLibros(catalogo, DatosPrueba.LIBROS_SW_E1);
        for (String a : args) {
            int n = Integer.parseInt(a);
            if (n > 0) {
                catalogo.insertar(DatosPrueba.libro(n));
            }
        }
        imprimirRecorridos(catalogo, "INSERCIONES");
        System.out.println(catalogo.trazaRecorrido(TipoRecorrido.PREORDEN));
        for (String a : args) {
            int n = Integer.parseInt(a);
            if (n < 0) {
                catalogo.eliminar(-n);
                imprimirRecorridos(catalogo, "ELIMINAR " + (-n));
            }
        }
    }

    private static void imprimirRecorridos(CatalogoABB c, String titulo) {
        System.out.println("## " + titulo);
        for (TipoRecorrido t : TipoRecorrido.values()) {
            StringBuilder sb = new StringBuilder();
            for (Libro l : c.recorrer(t)) {
                sb.append(" ").append(l.getCodigo());
            }
            System.out.println(t + ":" + sb);
        }
    }
}
