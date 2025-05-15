package tafl.excepcion;

/**
 * Excepción personalizada para manejar casos de coordenadas incorrectas.
 * @author Jon Ander Incera Moreno
 * Creado 18/12/2023
 * @version 1.0 realizacion del codigo 
 * 			1.1 comentarios
 * 
 */
public class CoordenadasIncorrectasException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor por defecto para la excepción de coordenadas incorrectas.
     */
	public CoordenadasIncorrectasException() {
		super();
	}
	
	 /**
     * Constructor que acepta un mensaje descriptivo para la excepción.
     * 
     * @param message Mensaje descriptivo sobre el error de coordenadas.
     */
	public CoordenadasIncorrectasException(String message) {
		super(message);
	}
	
	/**
     * Constructor que acepta una causa subyacente de la excepción.
     * 
     * @param cause Causa subyacente que originó el error.
     */
	public CoordenadasIncorrectasException(Throwable cause) {
		super(cause);
	}

	/**
     * Constructor que acepta un mensaje descriptivo y una causa subyacente de la excepción.
     * 
     * @param message Mensaje descriptivo sobre el error de coordenadas.
     * @param cause Causa subyacente que originó el error.
     */
	public CoordenadasIncorrectasException(String message, Throwable cause) {
		super(message, cause);
	}
}
