package brandubh.modelo;

import java.util.Arrays;
import java.util.Objects;

import brandubh.util.Coordenada;
import brandubh.util.TipoCelda;
import brandubh.util.TipoPieza;

/**
 * La clase Tablero representa el tablero de un juego de mesa. Contiene una
 * matriz de celdas y proporciona métodos para realizar operaciones en el
 * tablero.
 * 
 * @author Jon Ander Incera Moreno Creado 07/11/2023
 * @version 1.0 realizacion del codigo 1.1 comentarios
 */
public class Tablero {
	//atributo de una matrix bidemensional
	private Celda[][] board;
	//atributo de una constante que este caso es siete
	private final int DIM = 7;
	
	/**
	 * Constructor de la clase Tablero. Inicializa la matriz de celdas y configura
	 * las celdas iniciales del juego.
	 */
	public Tablero () {
		board = new Celda[DIM][DIM];
		for (int i = 0; i < DIM; i++)
			for (int j = 0; j < DIM; j++) {
				if (i == 0 && (j == 0 || j == DIM-1)) { // primera fila
					board[i][j] = new Celda(new Coordenada(i, j), TipoCelda.PROVINCIA);
				} else if (i == DIM-1 && (j == 0 || j == DIM-1)) { // última fila
					board[i][j] = new Celda(new Coordenada(i, j), TipoCelda.PROVINCIA);
				} else if (i == 3 && j == 3) {
					board[i][j] = new Celda(new Coordenada(i, j), TipoCelda.TRONO);
				} else { //  normal
					board[i][j] = new Celda(new Coordenada(i, j)); // otras celdas -> NORMAL
				}
			}
	}
	
	/**
	 * Devuelve una representación en formato de texto del tablero.
	 *
	 * @return Una cadena de caracteres que representa el estado actual del tablero.
	 */
	public String aTexto() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < DIM; i++) {
			sb.append((7 - i));
			for (int j = 0; j < DIM; j++) {
				sb.append(" ");
				if (board[i][j].consultarPieza() == null) {
					sb.append("-");
				} else {
					sb.append(board[i][j].consultarPieza().consultarTipoPieza().toChar());
				}
			}
			sb.append("\n");
		}
		sb.append("  a b c d e f g");
		return sb.toString();
	}
	
	/**
	 * Crea una copia (clon) del tablero actual.
	 *
	 * @return Una nueva instancia de Tablero que es una copia del tablero actual.
	 */
	public Tablero clonar() {
		Tablero newTablero = new Tablero();
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				if (!this.board[i][j].estaVacia()) {
					// this.board[i][j].consultarPieza() retorna un clon de la pieza
					newTablero.board[i][j].colocar(this.board[i][j].consultarPieza());
				}
			}
		}
		return newTablero;
	}
	
	/**
	 * Coloca una pieza en una celda específica del tablero.
	 *
	 * @param pieza      La pieza que se va a colocar en la celda.
	 * @param coordenada Las coordenadas de la celda en la que se colocará la pieza.
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) {
		if (pieza != null && coordenada != null && coordenada != null && coordenada.fila() >= 0 && coordenada.fila() < DIM &&
				coordenada.columna() >= 0 && coordenada.columna() < DIM) {
			this.board[coordenada.fila()][coordenada.columna()].colocar(pieza);
		}
	}
	
	/**
	 * Consulta la celda en el tablero que corresponde a una coordenada específica.
	 *
	 * @param coordenada Las coordenadas de la celda que se desea consultar.
	 * @return La referencia a la celda especificada o null si las coordenadas están
	 *         fuera de los límites del tablero.
	 */
	public Celda consultarCelda(Coordenada coordenada) {
		if (coordenada != null && coordenada.fila() >= 0 && coordenada.fila() < DIM &&
				coordenada.columna() >= 0 && coordenada.columna() < DIM) {
			Celda celda = this.board[coordenada.fila()][coordenada.columna()];
			return celda.clonar(); // retorno el clon de la celda
		} else {
			return null;
		}		
	}
	
	/**
	 * Consulta todas las celdas del tablero y devuelve una matriz de referencias a
	 * las celdas.
	 *
	 * @return Un array de objetos Celda que contiene todas las celdas del tablero
	 *         en un orden específico.
	 */
	public Celda[] consultarCeldas() {
		Celda[] newArray = new Celda[DIM * DIM];
		int idx = 0;
		for (int j = 0; j < DIM; j++) { // recorro por columna
			for (int i = 0; i < DIM; i++) {
				Celda celda = this.board[i][j]; // referencia
				newArray[idx++] = celda.clonar();
			}
		}
		return newArray;		
	}
	
	/**
	 * Consulta todas las celdas contiguas a una coordenada específica en el
	 * tablero.
	 *
	 * @param coordenada Las coordenadas de la celda cuyas celdas contiguas se
	 *                   desean consultar.
	 * @return Un array de objetos Celda que contiene todas las celdas contiguas a
	 *         la coordenada especificada. Si las coordenadas están fuera de los
	 *         límites del tablero, se devuelve un array vacío.
	 */
	public Celda[] consultarCeldasContiguas(Coordenada coordenada) {
		if (!(coordenada != null && coordenada.fila() >= 0 && coordenada.fila() < DIM &&
				coordenada.columna() >= 0 && coordenada.columna() < DIM)) return new Celda[0]; // no hay celdas porque la coordenada no es válida
		Celda[] celdasVertical = consultarCeldasContiguasEnVertical(coordenada);
		Celda[] celdasHorizontal = consultarCeldasContiguasEnHorizontal(coordenada);
		Celda[] newArray = new Celda[celdasVertical.length + celdasHorizontal.length];
		System.arraycopy(celdasVertical, 0, newArray, 0, celdasVertical.length);
		System.arraycopy(celdasHorizontal, 0, newArray, celdasVertical.length, celdasHorizontal.length);
		return newArray;
	}
	
	/**
	 * Consulta las celdas contiguas en la dirección vertical (arriba y abajo) a una
	 * coordenada específica en el tablero.
	 *
	 * @param coordenada Las coordenadas de la celda cuyas celdas contiguas
	 *                   verticales se desean consultar.
	 * @return Un array de objetos Celda que contiene las celdas contiguas en la
	 *         dirección vertical. Si las coordenadas están fuera de los límites del
	 *         tablero, se devuelve un array vacío.
	 */
	public Celda[] consultarCeldasContiguasEnVertical(Coordenada coordenada) {
		if (!(coordenada != null && coordenada.fila() >= 0 && coordenada.fila() < DIM &&
				coordenada.columna() >= 0 && coordenada.columna() < DIM)) return new Celda[0];
		Celda[] newArray;
		if (coordenada.fila() == 0) {
			newArray = new Celda[1]; // retorno la celda de abajo
			newArray[0] = this.board[coordenada.fila() + 1][coordenada.columna()].clonar();
		} else if (coordenada.fila() == DIM - 1) {
			newArray = new Celda[1]; // retorno la celda de arriba
			newArray[0] = this.board[coordenada.fila() - 1][coordenada.columna()].clonar();
		} else {
			newArray = new Celda[2];
			newArray[0] = this.board[coordenada.fila() - 1][coordenada.columna()].clonar(); // celda arriba
			newArray[1] = this.board[coordenada.fila() + 1][coordenada.columna()].clonar(); // celda abajo
		}
		return newArray;	
	}
	
	/**
	 * Consulta las celdas contiguas en la dirección horizontal (izquierda y
	 * derecha) a una coordenada específica en el tablero.
	 *
	 * @param coordenada Las coordenadas de la celda cuyas celdas contiguas
	 *                   horizontales se desean consultar.
	 * @return Un array de objetos Celda que contiene las celdas contiguas en la
	 *         dirección horizontal. Si las coordenadas están fuera de los límites
	 *         del tablero, se devuelve un array vacío.
	 */
	public Celda[] consultarCeldasContiguasEnHorizontal(Coordenada coordenada) {
		if (!(coordenada != null && coordenada.fila() >= 0 && coordenada.fila() < DIM &&
				coordenada.columna() >= 0 && coordenada.columna() < DIM)) return new Celda[0];
		Celda[] newArray;
		if (coordenada.columna() == 0) {
			newArray = new Celda[1]; // retorno celda derecha
			newArray[0] = this.board[coordenada.fila()][coordenada.columna() + 1].clonar();
		} else if (coordenada.columna() == DIM - 1) {
			newArray = new Celda[1]; // retorno celda izquierda
			newArray[0] = this.board[coordenada.fila()][coordenada.columna() - 1].clonar();
		} else {
			newArray = new Celda[2];
			newArray[0] = this.board[coordenada.fila()][coordenada.columna() - 1].clonar(); // izq
			newArray[1] = this.board[coordenada.fila()][coordenada.columna() + 1].clonar(); // der
		}
		return newArray;	
	}
	
	/**
	 * Consulta el número de columnas del tablero.
	 *
	 * @return El número de columnas en el tablero, que es 7 en este caso.
	 */
	public int consultarNumeroColumnas() {
		return DIM;
	}
	
	/**
	 * Consulta el número de filas del tablero.
	 *
	 * @return El número de filas en el tablero, que es 7 en este caso.
	 */
	public int consultarNumeroFilas() {
		return DIM;
	}

	/**
	 * Consulta el número de piezas de un tipo específico en el tablero.
	 *
	 * @param tipoPieza El tipo de pieza que se desea contar en el tablero.
	 * @return El número de piezas del tipo especificado en el tablero.
	 */
	public int consultarNumeroPiezas(TipoPieza tipoPieza) {
		int noPiezas = 0;
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				if (!this.board[i][j].estaVacia() &&
						this.board[i][j].consultarPieza().consultarTipoPieza() == tipoPieza) {
					noPiezas++;
				}
			}
		}
		return noPiezas;
	}
	
	/**
	 * Elimina una pieza de una celda específica del tablero.
	 *
	 * @param coordenada Las coordenadas de la celda de la que se eliminará la
	 *                   pieza.
	 */
	public void eliminarPieza(Coordenada coordenada) {
		if (coordenada != null && coordenada.fila() >= 0 && coordenada.fila() < DIM &&
				coordenada.columna() >= 0 && coordenada.columna() < DIM)
			this.board[coordenada.fila()][coordenada.columna()].eliminarPieza();
	}
	
	/**
	 * Obtiene una referencia a una celda específica del tablero.
	 *
	 * @param coordenada Las coordenadas de la celda que se desea obtener.
	 * @return La referencia a la celda especificada.
	 */
	public Celda obtenerCelda(Coordenada coordenada) {
		// devuelve la referencia a la celda
		if (coordenada != null && coordenada.fila() >= 0 && coordenada.fila() < DIM &&
				coordenada.columna() >= 0 && coordenada.columna() < DIM)
			return this.board[coordenada.fila()][coordenada.columna()];
		else
			return null;
	}

	/**
	 * Genera un código hash para el tablero.
	 *
	 * @return El código hash del tablero.
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(board);
		result = prime * result + Objects.hash(DIM);
		return result;
	}

	/**
	 * Compara el tablero con otro objeto para determinar si son iguales.
	 *
	 * @param obj El objeto con el que se comparará el tablero.
	 * @return true si los tableros son iguales, false en caso contrario.
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tablero other = (Tablero) obj;
		return DIM == other.DIM && Arrays.deepEquals(board, other.board);
	}
}
