package tafl.excepcion;

/**
 * Excepción personalizada para manejar casos relacionados con el tipo de árbitro.
 * * @author Jon Ander Incera Moreno
 * Creado 18/11/2023
 * @version 1.0 realizacion del codigo 
 * 			1.1 comentarios
 * 
 */
public class TipoArbitroException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	 /**
     * Constructor por defecto para la excepción de tipo de árbitro.
     */
	public TipoArbitroException() {
		super();
	}
	
	/**
     * Constructor que acepta un mensaje descriptivo para la excepción.
     * 
     * @param message Mensaje descriptivo sobre el problema del tipo de árbitro.
     */
	public TipoArbitroException(String message) {
		super(message);
	}
	
	/**
     * Constructor que acepta una causa subyacente de la excepción.
     * 
     * @param cause Causa subyacente que originó el problema.
     */
	public TipoArbitroException(Throwable cause) {
		super(cause);
	}
	
	 /**
     * Constructor que acepta un mensaje descriptivo y una causa subyacente de la excepción.
     * 
     * @param message Mensaje descriptivo sobre el problema del tipo de árbitro.
     * @param cause Causa subyacente que originó el problema.
     */
	public TipoArbitroException(String message, Throwable cause) {
		super(message, cause);
	}
}
