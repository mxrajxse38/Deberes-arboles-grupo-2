package enums;

/** Tipos de unidad de la Biblioteca: BIBLIOTECA -> AREA -> ESTANTE. */
public enum TipoUnidad {
    BIBLIOTECA, AREA, ESTANTE;

    /** Indica si una unidad de este tipo puede tener como hijo una unidad del tipo indicado. */
    public boolean puedeTenerHijo(TipoUnidad hijo) {
        if (hijo == null) {
            return false;
        }
        switch (this) {
            case BIBLIOTECA:
                return hijo == AREA;
            case AREA:
                return hijo == ESTANTE;
            default:
                return false;
        }
    }
}
