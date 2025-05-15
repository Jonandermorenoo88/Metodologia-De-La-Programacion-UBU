package tafl.util;

/**
 * Esta enumeración representa los colores utilizados en el juego Brandubh.
 * Los dos colores posibles son BLANCO ('B') y NEGRO ('N').
 * 
 * @author Jon Ander Incera Moreno
 * Creado 31/10/2023
 * @version 1.0 realizacion del codigo 1.1 comentarios
 * 
 */
public enum Color {
    /**Representa el color blanco*/
	BLANCO('B'),
	/**Representa el color negro*/
    NEGRO('N');   

    private char letra; // La letra asociada al color

    /**
     * Constructor privado para la enumeración Color.
     * @param letra La letra que representa el color.
     */
    private Color(char letra) {
        this.letra = letra;
    }

    /**
     * Obtiene el color contrario al color actual.
     * @return El color contrario (BLANCO si es NEGRO y NEGRO si es BLANCO).
     */
    public Color consultarContrario() {
        if (this.letra == 'B') {
            return NEGRO;
        } else {
            return BLANCO;
        }
    }

    /**
     * Convierte el color en un carácter ('B' o 'N').
     * @return El carácter que representa el color.
     */
    public char toChar() {
        return this.letra;
    }
}
