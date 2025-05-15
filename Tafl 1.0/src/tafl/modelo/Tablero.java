package tafl.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.util.Coordenada;
import tafl.util.TipoCelda;
import tafl.util.TipoPieza;

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
	//private Celda[][] board;
	List<List<Celda>> board;
	//atributo de una constante que este caso es siete
	private final int DIM = 7;
	
	
	/**
	 * Constructor de la clase Tablero. Inicializa la matriz de celdas y configura
	 * las celdas iniciales del juego.
	 */
	
	public Tablero() {
		//he convertido el contructor que pasa una lista y he quitado el array
	    //board = new Celda[DIM][DIM]; // Inicializa una matriz de celdas de tamaño DIM x DIM
		board = new ArrayList<>();
		Celda celda;
	    // Bucle para recorrer las filas
	    for (int i = 0; i < DIM; i++) {
	    	List<Celda> fila = new ArrayList<>(); //aqui lo que hago es crear una fila
	        // Bucle para recorrer las columnas
	        for (int j = 0; j < DIM; j++) {
	            // Verifica si la celda está en la primera fila y en las esquinas
	            if (i == 0 && (j == 0 || j == DIM - 1)) {
	                celda = new Celda(new Coordenada(i, j), TipoCelda.PROVINCIA);
	            } 
	            // Verifica si la celda está en la última fila y en las esquinas
	            else if (i == DIM - 1 && (j == 0 || j == DIM - 1)) {
	               celda = new Celda(new Coordenada(i, j), TipoCelda.PROVINCIA);
	            } 
	            // Verifica si la celda está en la posición (3, 3)
	            else if (i == 3 && j == 3) {
	                celda = new Celda(new Coordenada(i, j), TipoCelda.TRONO);
	            } 
	            // Si no cumple ninguna de las condiciones anteriores, se establece como celda normal
	            else {
	                celda = new Celda(new Coordenada(i, j)); // Otras celdas -> NORMAL
	            }
	            fila.add(celda);
	        }
	        board.add(fila);
	    }
	}

	/**
	 * Devuelve una representación en formato de texto del tablero.
	 *
	 * @return Una cadena de caracteres que representa el estado actual del tablero.
	 */
	public String aTexto() {
	    StringBuilder sb = new StringBuilder(); // Crea un StringBuilder para almacenar la representación del tablero

	    // Bucle para recorrer las filas del tablero
	    for (int i = 0; i < DIM; i++) {
	        sb.append((7 - i)); // Agrega el número de fila en orden descendente (ajuste visual)

	        // Bucle para recorrer las columnas del tablero
	        for (int j = 0; j < DIM; j++) {
	            sb.append(" "); // Añade un espacio entre columnas

	            // Verifica si la celda no contiene una pieza
	            //if (board[i][j].consultarPieza() == null) 
	            if(board.get(i).get(j).consultarPieza() == null){ //cambio para una lista
	                sb.append("-"); // Si no hay pieza, agrega "-" a la representación
	            } else {
	                // Si hay una pieza, agrega su representación de tipo de pieza al StringBuilder
	                sb.append(board.get(i).get(j).consultarPieza().consultarTipoPieza().toChar()); //correcion del error
	            }
	        }
	        sb.append("\n"); // Agrega un salto de línea al final de cada fila
	    }

	    sb.append("  a b c d e f g"); // Agrega la etiqueta de las columnas al final de la representación
	    return sb.toString(); // Devuelve la representación completa del tablero como una cadena de caracteres
	}
	
	/**
	 * Crea una copia (clon) del tablero actual.
	 *
	 * @return Una nueva instancia de Tablero que es una copia del tablero actual.
	 */
	public Tablero clonar() {
	    Tablero newTablero = new Tablero(); // Crea un nuevo objeto Tablero

	    // Bucle para recorrer las filas del tablero actual
	    for (int i = 0; i < DIM; i++) {
	        // Bucle para recorrer las columnas del tablero actual
	        for (int j = 0; j < DIM; j++) {
	            // Verifica si la celda en el tablero actual no está vacía
	            //if (!this.board[i][j].estaVacia()) 
	        	if (!this.board.get(i).get(j).estaVacia()){
	                // Coloca una copia de la pieza en la misma posición en el nuevo tablero
	                // this.board[i][j].consultarPieza() devuelve un clon de la pieza en la celda actual
	                //newTablero.board[i][j].colocar(this.board[i][j].consultarPieza());
	        		 newTablero.board.get(i).get(j).colocar(this.board.get(i).get(j).consultarPieza());
	            }
	        }
	    }

	    return newTablero; // Devuelve la copia (clon) del tablero actual como un nuevo objeto Tablero
	}

	
	/**
	 * Coloca una pieza en una celda específica del tablero.
	 *
	 * @param pieza      La pieza que se va a colocar en la celda.
	 * @param coordenada Las coordenadas de la celda en la que se colocará la pieza.
	 * @throws CoordenadasIncorrectasException cuando son coordenadas nulas salta una excepcion 
	 */
	public void colocar(Pieza pieza, Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (pieza == null || coordenada == null)
			throw new IllegalArgumentException();
		if (!estaEnTablero(coordenada))
			throw new CoordenadasIncorrectasException();
		if (coordenada.fila() >= 0 && coordenada.fila() < DIM &&
				coordenada.columna() >= 0 && coordenada.columna() < DIM) {
			this.board.get(coordenada.fila()).get(coordenada.columna()).colocar(pieza); //cambio por lista
			//this.board[coordenada.fila()][coordenada.columna()].colocar(pieza);
		}
	}
	
	/**
	 * Consulta la celda en el tablero que corresponde a una coordenada específica.
	 *
	 * @param coordenada Las coordenadas de la celda que se desea consultar.
	 * @return La referencia a la celda especificada o null si las coordenadas están
	 *         fuera de los límites del tablero.
	 * @throws CoordenadasIncorrectasException cuando son coordenadas nulas salta una excepcion
	 */
	public Celda consultarCelda(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null)
			throw new IllegalArgumentException();
		if (!estaEnTablero(coordenada))
			throw new CoordenadasIncorrectasException();
		Celda celda = this.board.get(coordenada.fila()).get(coordenada.columna()); //cambio por lista
		//Celda celda = this.board[coordenada.fila()][coordenada.columna()];
		return celda.clonar(); // retorno el clon de la celda
	}
	
	/**
	 * Consulta todas las celdas del tablero y devuelve una matriz de referencias a
	 * las celdas.
	 *
	 * @return Un array de objetos Celda que contiene todas las celdas del tablero
	 *         en un orden específico.
	 */
	//falta arreglar
	public List<Celda> consultarCeldas() {
		List<Celda> newList = new ArrayList<>();
	    for (int j = 0; j < DIM; j++) { // Bucle para recorrer por columnas
	        for (int i = 0; i < DIM; i++) { // Bucle para recorrer por filas
	            Celda celda = this.board.get(i).get(j); // Obtiene la referencia de la celda en la posición (i, j)
	            //newArray[idx++] = celda.clonar(); // Agrega una copia de la celda al nuevo arreglo
	            newList.add(celda.clonar());
	        }
	    }
	    return newList; // Devuelve el arreglo que representa el estado actual del tablero
	}
	
	/**
	 * Consulta todas las celdas contiguas a una coordenada específica en el tablero.
	 *
	 * @param coordenada Las coordenadas de la celda cuyas celdas contiguas se desean consultar.
	 * @return Un array de objetos Celda que contiene todas las celdas contiguas a la coordenada especificada.
	 *         Si las coordenadas están fuera de los límites del tablero, se devuelve un array vacío.
	 * @throws CoordenadasIncorrectasException cuando son coordenadas nulas salta una excepcion
	 */
//	//falta arreglar
	public List<Celda> consultarCeldasContiguas(Coordenada coordenada) throws CoordenadasIncorrectasException {
		List<Celda> newList = new ArrayList<>();
		if (coordenada == null)
			throw new IllegalArgumentException();
	    // Verifica si la coordenada está dentro de los límites del tablero
		if (!estaEnTablero(coordenada)) 
			throw new CoordenadasIncorrectasException();

	    // Consulta las celdas contiguas en vertical y horizontal a la coordenada especificada
	    //Celda[] celdasVertical = consultarCeldasContiguasEnVertical(coordenada);
	    //Celda[] celdasHorizontal = consultarCeldasContiguasEnHorizontal(coordenada);
	    List<Celda> newVertical = consultarCeldasContiguasEnVertical(coordenada);
	    List<Celda> newHorizontal = consultarCeldasContiguasEnHorizontal(coordenada);
	    
	    for (Celda celda : newVertical) {
	    	newList.add(celda);
	    }
	    
	    for (Celda celda : newHorizontal) {
	    	newList.add(celda);
	    }
	    // Crea un nuevo arreglo combinando las celdas contiguas encontradas en vertical y horizontal
	    //Celda[] newArray = new Celda[celdasVertical.length + celdasHorizontal.length];
	    //System.arraycopy(celdasVertical, 0, newArray, 0, celdasVertical.length);
	    //System.arraycopy(celdasHorizontal, 0, newArray, celdasVertical.length, celdasHorizontal.length);

	    return newList; // Devuelve un array que contiene todas las celdas contiguas a la coordenada especificada
	}

	
	/**
	 * Consulta las celdas contiguas en la dirección vertical (arriba y abajo) a una coordenada específica en el tablero.
	 *
	 * @param coordenada Las coordenadas de la celda cuyas celdas contiguas verticales se desean consultar.
	 * @return Un array de objetos Celda que contiene las celdas contiguas en la dirección vertical.
	 *         Si las coordenadas están fuera de los límites del tablero, se devuelve un array vacío.
	 * @throws CoordenadasIncorrectasException cuando son coordenadas nulas salta una excepcion
	 */
	//falta arreglar
	public List<Celda> consultarCeldasContiguasEnVertical(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null)
			throw new IllegalArgumentException();
	    // Verifica si la coordenada está dentro de los límites del tablero
		if (!estaEnTablero(coordenada)) 
			throw new CoordenadasIncorrectasException();
		List<Celda> newList = new ArrayList<>(); // creo una lista vacia

		// Comprueba si la coordenada está en el borde superior del tablero
	    if (coordenada.fila() == 0) {
	    	newList.add(this.board.get(coordenada.fila() + 1).get(coordenada.columna()).clonar());
	        //newArray = new Celda[1]; // Crea un arreglo para una celda contigua (abajo)
	        //newArray[0] = this.board[coordenada.fila() + 1][coordenada.columna()].clonar(); // Celda debajo
	    } 
	    // Comprueba si la coordenada está en el borde inferior del tablero
	    else if (coordenada.fila() == DIM - 1) {
	    	newList.add(this.board.get(coordenada.fila() - 1).get(coordenada.columna()).clonar());
	        //newArray = new Celda[1]; // Crea un arreglo para una celda contigua (arriba)
	        //newArray[0] = this.board[coordenada.fila() - 1][coordenada.columna()].clonar(); // Celda encima
	    } 
	    // Si la coordenada no está en el borde superior ni en el inferior del tablero
	    else {
	        //newArray = new Celda[2]; // Crea un arreglo para dos celdas contiguas (arriba y abajo)
	        //newArray[0] = this.board[coordenada.fila() - 1][coordenada.columna()].clonar(); // Celda arriba
	        //newArray[1] = this.board[coordenada.fila() + 1][coordenada.columna()].clonar(); // Celda abajo
	    	newList.add(this.board.get(coordenada.fila() - 1).get(coordenada.columna()).clonar());
	    	newList.add(this.board.get(coordenada.fila() + 1).get(coordenada.columna()).clonar());
	    }

	    return newList; // Devuelve un array con las celdas contiguas en dirección vertical
	}

	
	/**
	 * Consulta las celdas contiguas en la dirección horizontal (izquierda y derecha) a una coordenada específica en el tablero.
	 *
	 * @param coordenada Las coordenadas de la celda cuyas celdas contiguas horizontales se desean consultar.
	 * @return Un array de objetos Celda que contiene las celdas contiguas en la dirección horizontal.
	 *         Si las coordenadas están fuera de los límites del tablero, se devuelve un array vacío.
	 * @throws CoordenadasIncorrectasException cuando son coordenadas nulas salta una excepcion
	 */
	//falta arreglar
	public List<Celda> consultarCeldasContiguasEnHorizontal(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null)
			throw new IllegalArgumentException();
	    // Verifica si la coordenada está dentro de los límites del tablero
		if (!estaEnTablero(coordenada)) 
			throw new CoordenadasIncorrectasException();
	    List<Celda> newList = new ArrayList<>();

	    // Comprueba si la coordenada está en el borde izquierdo del tablero
	    if (coordenada.columna() == 0) {
	        //newArray = new Celda[1]; // Crea un arreglo para una celda contigua a la derecha
	        //newArray[0] = this.board[coordenada.fila()][coordenada.columna() + 1].clonar(); // Celda a la derecha
	    	newList.add(this.board.get(coordenada.fila()).get(coordenada.columna() + 1).clonar());
	    } 
	    // Comprueba si la coordenada está en el borde derecho del tablero
	    else if (coordenada.columna() == DIM - 1) {
	        //newArray = new Celda[1]; // Crea un arreglo para una celda contigua a la izquierda
	        //newArray[0] = this.board[coordenada.fila()][coordenada.columna() - 1].clonar(); // Celda a la izquierda
	    	newList.add(this.board.get(coordenada.fila()).get(coordenada.columna() - 1).clonar());
	    } 
	    // Si la coordenada no está en el borde izquierdo ni en el derecho del tablero
	    else {
	        //newArray = new Celda[2]; // Crea un arreglo para dos celdas contiguas (izquierda y derecha)
	        //newArray[0] = this.board[coordenada.fila()][coordenada.columna() - 1].clonar(); // Celda a la izquierda
	        //newArray[1] = this.board[coordenada.fila()][coordenada.columna() + 1].clonar(); // Celda a la derecha
	    	newList.add(this.board.get(coordenada.fila()).get(coordenada.columna() - 1).clonar());
	    	newList.add(this.board.get(coordenada.fila()).get(coordenada.columna() + 1).clonar());
	    }

	    return newList; // Devuelve un array con las celdas contiguas en dirección horizontal
	}

	
	/**
	 * Consulta el número de columnas del tablero.
	 *
	 * @return El número de columnas en el tablero, que es 7 en este caso.
	 */
	public int consultarNumeroColumnas() {
	    return DIM; // Devuelve la constante DIM que representa el número de columnas del tablero
	}

	/**
	 * Consulta el número de filas del tablero.
	 *
	 * @return El número de filas en el tablero, que es 7 en este caso.
	 */
	public int consultarNumeroFilas() {
	    return DIM; // Devuelve la constante DIM que representa el número de filas del tablero
	}


	/**
	 * Consulta el número de piezas de un tipo específico en el tablero.
	 *
	 * @param tipoPieza El tipo de pieza que se desea contar en el tablero.
	 * @return El número de piezas del tipo especificado en el tablero.
	 */
	public int consultarNumeroPiezas(TipoPieza tipoPieza) {
		if (tipoPieza == null)
			throw new IllegalArgumentException();
	    int noPiezas = 0; // Inicializa un contador para el número de piezas del tipo especificado

	    // Recorre todas las celdas del tablero
	    for (int i = 0; i < DIM; i++) {
	        for (int j = 0; j < DIM; j++) {
	            // Verifica si la celda no está vacía y si su tipo de pieza coincide con el tipo especificado
	            //if (!this.board[i][j].estaVacia() && this.board[i][j].consultarPieza().consultarTipoPieza() == tipoPieza) 
	        	if (!this.board.get(i).get(j).estaVacia() && this.board.get(i).get(j).consultarPieza().consultarTipoPieza() == tipoPieza){
	                noPiezas++; // Incrementa el contador si la pieza en la celda actual coincide con el tipo especificado
	            }
	        }
	    }

	    return noPiezas; // Devuelve el número total de piezas del tipo especificado en el tablero
	}

	
	/**
	 * Elimina una pieza de una celda específica del tablero.
	 *
	 * @param coordenada Las coordenadas de la celda de la que se eliminará la pieza.
	 * @throws CoordenadasIncorrectasException cuando son coordenadas nulas salta una excepcion
	 */
	public void eliminarPieza(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null)
			throw new IllegalArgumentException();
	    // Verifica si la coordenada está dentro de los límites del tablero
		if (!estaEnTablero(coordenada)) 
			throw new CoordenadasIncorrectasException();

		this.board.get(coordenada.fila()).get(coordenada.columna()).eliminarPieza(); // Elimina la pieza de la celda especificada
	}

	/**
	 * Obtiene una referencia a una celda específica del tablero.
	 *
	 * @param coordenada Las coordenadas de la celda que se desea obtener.
	 * @return La referencia a la celda especificada.
	 * @throws CoordenadasIncorrectasException cuando son coordenadas nulas salta un excepcion
	 */
	public Celda obtenerCelda(Coordenada coordenada) throws CoordenadasIncorrectasException {
		if (coordenada == null)
			throw new IllegalArgumentException();
	    // Verifica si la coordenada está dentro de los límites del tablero
		if (!estaEnTablero(coordenada)) 
			throw new CoordenadasIncorrectasException();

		return this.board.get(coordenada.fila()).get(coordenada.columna());
	}
	
	/**
	 * Verifica si una coordenada está dentro de los límites del tablero.
	 * 
	 * @param coordenada La coordenada a verificar.
	 * @return true si la coordenada está dentro del tablero, false en caso contrario.
	 * @throws IllegalArgumentException Cuando la coordenada proporcionada es nula.
	 */
	public boolean estaEnTablero(Coordenada coordenada) {
		// retorna true si la coordenada está en el tablero
		if (coordenada == null) 
			throw new IllegalArgumentException();
		return !(coordenada.fila() < 0 || coordenada.fila() >= DIM ||
				coordenada.columna() < 0 || coordenada.columna() >= DIM);
	}
	
	/**
	 * Genera un código hash para el tablero.
	 *
	 * @return El código hash del tablero.
	 */
	@Override
	public int hashCode() {
		return Objects.hash(DIM, board);
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
		return DIM == other.DIM && Objects.equals(board, other.board);
	}

	@Override
	public String toString() {
		return "Tablero [board=" + board + ", DIM=" + DIM + "]";
	}
	
}
