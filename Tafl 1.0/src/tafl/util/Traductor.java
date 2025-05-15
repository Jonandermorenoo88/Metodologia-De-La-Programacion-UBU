package tafl.util;

/**
 * Clase de utilidad para traducir entre notación algebraica y coordenadas en un tablero de tamaño 7x7 celdas.
 * 
 * @author Jon Ander Incera Moreno 
 * Creado 07/11/2023
 * @version 1.0 realizacion del codigo 1.1 comentarios
 * 
 * teses corregidos
 */
public class Traductor {
    /**
     * Traduce un texto en notación algebraica a una coordenada.
     *
     * @param texto El texto en notación algebraica a traducir.
     * @return La coordenada correspondiente si el texto es válido, o null si el texto es incorrecto.
     * 
     */
	public static Coordenada consultarCoordenadaParaNotacionAlgebraica(String texto) {
		if (!esTextoCorrectoParaCoordenada(texto)) return null;
		int fila, columna;
		columna = texto.charAt(0) - 'a';
		fila = 7 - Integer.parseInt(texto.substring(1));
		return new Coordenada(fila, columna);		
	}
    /**
     * Traduce una coordenada a texto en notación algebraica.
     *
     * @param coordenada La coordenada a traducir.
     * @return El texto en notación algebraica de 2 caracteres si la traducción es posible, o null si la coordenada es inválida.
     * 
     */
    public static String consultarTextoEnNotacionAlgebraica(Coordenada coordenada) {
        if (coordenada == null || coordenada.fila() < 0 || coordenada.fila() >= 7 || coordenada.columna() < 0 || coordenada.columna() >= 7) {
            return null;
        }

        char col = (char) ('a' + coordenada.columna());
        char row = (char) ('7' - coordenada.fila());

        return Character.toString(col) + Character.toString(row);
    }

    /**
     * Verifica si un texto es válido para representar una coordenada en notación algebraica.
     *
     * @param texto El texto a verificar.
     * @return true si el texto es válido (longitud 2 y corresponde a una coordenada válida), false de lo contrario.
     * 
     */
    public static boolean esTextoCorrectoParaCoordenada(String texto) {
        if (texto == null || texto.length() != 2) {
            return false;
        }

        char col = texto.charAt(0);
        char row = texto.charAt(1);

        return (col >= 'a' && col <= 'g' && row >= '1' && row <= '7');
    }
}
