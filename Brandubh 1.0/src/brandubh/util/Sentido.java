package brandubh.util;

/**
 * Esta enumeración representa los sentidos de desplazamiento en un tablero de juego.
 * Los sentidos posibles son VERTICAL_N (Norte), VERTICAL_S (Sur), HORIZONTAL_E (Este) y HORIZONTAL_O (Oeste).
 * 
 * @author Jon Ander Incera Moreno
 * Creado 31/10/2023
 * @version 1.0 realizacion del codigo 1.1 comentarios
 * 
 */
public enum Sentido {
	/**Representa el desplazamiento hacia el Norte*/
    VERTICAL_N(-1, 0),
    /**Representa el desplazamiento hacia el Sur*/
    VERTICAL_S(1, 0), 
    /**Representa el desplazamiento hacia el Este*/
    HORIZONTAL_E(0, 1),
    /**Representa el desplazamiento hacia el Oeste*/
    HORIZONTAL_O(0, -1);

	/**Desplazamiento en filas */
    private int desplazamientoEnFilas;    
    /**Desplazamiento en columnas*/
    private int desplazamientoEnColumnas; 

    /**
     * Constructor privado para la enumeración Sentido.
     * @param desplazamientoEnFilas    El desplazamiento en filas.
     * @param desplazamientoEnColumnas El desplazamiento en columnas.
     */
    private Sentido(int desplazamientoEnFilas, int desplazamientoEnColumnas) {
        this.desplazamientoEnFilas = desplazamientoEnFilas;
        this.desplazamientoEnColumnas = desplazamientoEnColumnas;
    }

    /**
     * Obtiene el desplazamiento en filas del sentido.
     *
     * @return El valor del desplazamiento en filas.
     */
    public int consultarDesplazamientoEnFilas() {
        return this.desplazamientoEnFilas;
    }

    /**
     * Obtiene el desplazamiento en columnas del sentido.
     *
     * @return El valor del desplazamiento en columnas.
     */
    public int consultarDesplazamientoEnColumnas() {
        return this.desplazamientoEnColumnas;
    }
}

