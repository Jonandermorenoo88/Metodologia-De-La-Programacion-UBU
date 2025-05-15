package brandubh.modelo;

import java.util.Objects;

import brandubh.util.Color;
import brandubh.util.Coordenada;
import brandubh.util.TipoCelda;

/**
 * Clase que representa una celda en el tablero de juego.
 * 
 * @author Jon Ander Incera Moreno
 * Creado 31/10/2023
 * @version 1.0 realizacion del codigo 
 * 			1.1 comentarios
 * 
 */
public class Celda {
	
	private Coordenada coordenada;
	private TipoCelda tipoCelda;
	private Pieza pieza;

	/**
	 * Constructor de una celda con coordenada y tipo de celda por defecto (NORMAL).
	 *
	 * @param coordenada La coordenada de la celda.
	 */
	public Celda(Coordenada coordenada) {
		this(coordenada, TipoCelda.NORMAL);
	}

	/**
	 * Constructor de una celda con coordenada y tipo de celda especificados.
	 *
	 * @param coordenada La coordenada de la celda.
	 * @param tipoCelda  El tipo de celda.
	 */
	public Celda(Coordenada coordenada, TipoCelda tipoCelda) {
		this.coordenada = coordenada;
		this.tipoCelda = tipoCelda;
		this.pieza = null;
	}

	/**
	 * Crea una copia (clon) de la celda, incluyendo su contenido si contiene una
	 * pieza.
	 *
	 * @return Una copia de la celda.
	 */
	public Celda clonar() {
		Celda newCelda = new Celda(this.coordenada, this.tipoCelda);
		if (this.pieza != null) {
			newCelda.pieza = this.pieza.clonar();
		}
		return newCelda; 
	}

	/**
	 * Coloca una pieza en la celda.
	 *
	 * @param pieza La pieza a colocar en la celda.
	 */
	public void colocar(Pieza pieza) {
		this.pieza = pieza;
	}

	/**
	 * Consulta el color de la pieza en la celda.
	 *
	 * @return El color de la pieza en la celda o null si la celda está vacía.
	 */
	public Color consultarColorDePieza() {
		if (this.pieza == null) {
			return null;
		} else {
			return this.pieza.consultarColor();
		}
	}

	/**
	 * Consulta la coordenada de la celda.
	 *
	 * @return La coordenada de la celda.
	 */
	public Coordenada consultarCoordenada() {
		return new Coordenada(this.coordenada.fila(), this.coordenada.columna());
	}

	/**
	 * Consulta la pieza en la celda.
	 *
	 * @return La pieza en la celda o null si la celda está vacía.
	 */
	public Pieza consultarPieza() {
		if (this.pieza == null) {
			return null;
		} else {
			return this.pieza.clonar();
		}
	}

	/**
	 * Consulta el tipo de celda.
	 *
	 * @return El tipo de celda.
	 */
	public TipoCelda consultarTipoCelda() {
		return this.tipoCelda;
	}

	/**
	 * Elimina la pieza de la celda, dejándola vacía.
	 * y no retorno nada
	 */
	public void eliminarPieza() {
		this.pieza = null;
	}

	/**
	 * Verifica si la celda está vacía (sin pieza).
	 *
	 * @return true si la celda está vacía, false de lo contrario.
	 */
	public boolean estaVacia() {
		return this.pieza == null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(coordenada, pieza, tipoCelda);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Celda other = (Celda) obj;
		return Objects.equals(coordenada, other.coordenada) && Objects.equals(pieza, other.pieza)
				&& tipoCelda == other.tipoCelda;
	}

	@Override
	public String toString() {
		return "Celda [coordenada=" + coordenada + ", tipoCelda=" + tipoCelda + ", pieza=" + pieza + "]";
	}
}
