package tafl.util;

/**
 * Esta enumeración representa los tipos de piezas en el juego Brandubh.
 * Los tipos posibles son DEFENSOR (pieza defensora), ATACANTE (pieza atacante) y REY (rey).
 * 
 * @author Jon Ander Incera Moreno 
 * Creado 31/10/2023
 * @version 1.0 realizacion del codigo 1.1 comentarios
 */
public enum TipoPieza {
	/**Representa una pieza defensora de color blanco.*/
    DEFENSOR('D', Color.BLANCO), 
    /**Representa una pieza atacante de color negro.*/
    ATACANTE('A', Color.NEGRO),
    /**Representa al rey de color blanco.*/
    REY('R', Color.BLANCO);

	/**El carácter asociado al tipo de pieza.*/
    private char caracter;
    /**El color asociado al tipo de pieza.*/
    private Color color;

    /**
     * Constructor privado para la enumeración TipoPieza.
     * @param caracter El carácter que representa el tipo de pieza.
     * @param color    El color de la pieza.
     */
    private TipoPieza(char caracter, Color color) {
        this.caracter = caracter;
        this.color = color;
    }

    /**
     * Obtiene el color de la pieza.
     *
     * @return El color de la pieza (BLANCO o NEGRO).
     */
    public Color consultarColor() {
        return this.color;
    }

    /**
     * Convierte el tipo de pieza en un carácter ('D', 'A' o 'R').
     *
     * @return El carácter que representa el tipo de pieza.
     */
    public char toChar() {
        return this.caracter;
    }
    
    public String toString() {
    	return "" + toChar();
    }
}