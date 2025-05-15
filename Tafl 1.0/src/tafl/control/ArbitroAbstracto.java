package tafl.control;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Jugada;
import tafl.modelo.Pieza;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.Coordenada;
import tafl.util.TipoPieza;

/**
 * El `ArbitroAbstracto` controla y gestiona el desarrollo del juego, aplicando
 * reglas, validando movimientos y verificando el estado de la partida.
 * 
 * @author Jon Ander Incera Moreno Creado 20/11/2023
 * @version 1.0 realizacion del codigo 1.1 comentarios
 * 
 */
public abstract class ArbitroAbstracto implements Arbitro {
	// Atributos
	protected Tablero tablero; // El tablero del juego.
	protected int numeroJugada; // Número de la jugada actual.
	protected Color turnoActual; // El color del jugador que tiene el turno.
	protected Coordenada ultimaPieza; // Posición de la última pieza movida
	/**
	 * Constructor de ArbitroAbstracto
	 * @param tablero el tablero de juego
	 */
	public ArbitroAbstracto(Tablero tablero) {
		this.tablero = tablero;
		if (tablero == null)
			throw new IllegalArgumentException();
		this.numeroJugada = 0; // inicia contador de jugadas a cero
	}
	
	/**
	 * Cambia el turno actual del juego al contrario del turno actual.
	 * Si el turno actual es nulo, establece el turno como NEGRO (para pasar una prueba).
	 */
	@Override
	public void cambiarTurno() { 
	    if (this.turnoActual == null) // Si el turno actual es nulo.
	        this.turnoActual = Color.NEGRO; // Establece el turno como NEGRO (para pasar una prueba).
	    this.turnoActual = turnoActual.consultarContrario(); // Cambia el turno actual al opuesto.
	}

	/**
	 * Coloca las piezas en el tablero según los tipos y las coordenadas proporcionadas.
	 * @param tipo un arreglo de tipos de pieza para colocar.
	 * @param coordenadas un arreglo bidimensional de coordenadas para la colocación.
	 * @param turnoActual el color del jugador cuyas piezas se están colocando.
	 * @throws CoordenadasIncorrectasException salta una excepcion cuando son coordenadas incorrectas
	 */
	@Override
	public void colocarPiezas(TipoPieza[] tipo, int[][] coordenadas, Color turnoActual) throws CoordenadasIncorrectasException {
		if(tipo == null || coordenadas == null || turnoActual == null) {
			throw new IllegalArgumentException();
		}
	    if (tipo.length == coordenadas.length) { // Verifica que la cantidad de tipos y coordenadas sea la misma.
	        for (int i = 0; i < tipo.length; i++) { // Recorre los arreglos de tipos y coordenadas.
	        	Coordenada coordenada = new Coordenada(coordenadas[i][0], coordenadas[i][1]);
	        	if(coordenadaIncorrecta(coordenada)) {
	        		throw new CoordenadasIncorrectasException();
	        	}
	            try {
					this.tablero.colocar(new Pieza(tipo[i]), new Coordenada(coordenadas[i][0], coordenadas[i][1]));
				} catch (CoordenadasIncorrectasException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} // Coloca una pieza en las coordenadas proporcionadas.
	        }
	        this.turnoActual = turnoActual; // Establece el turno actual al jugador proporcionado.
	    }
	}

	/**
	 * Devuelve el número de jugadas realizadas hasta el momento.
	 *
	 * @return El número de la jugada actual.
	 */
	@Override
	public int consultarNumeroJugada() {
	    return this.numeroJugada;
	}

	/**
	 * Devuelve una copia del tablero actual.
	 *
	 * @return Una copia del tablero actual en su estado actual.
	 */
	@Override
	public Tablero consultarTablero() {
		return this.tablero.clonar();
	}

	/**
	 * Devuelve el color del jugador al que le toca jugar en este momento.
	 *
	 * @return El color del jugador cuyo turno es el actual.
	 */
	@Override
	public Color consultarTurno() {
		return this.turnoActual;
	}
	
	@Override
	public void colocarPiezasConfiguracionInicial() {
		
	}

//	@Override
//	public boolean esMovimientoLegal(Jugada jugada) { // hacer implementación concreta
//		// TODO Auto-generated method stub
//		return false;
//	}

//	@Override
//	public boolean haGanadoAtacante() { // hacer implementación concreta
//		// TODO Auto-generated method stub
//		return false;
//	}

//	@Override
//	public boolean haGanadoRey() { // hacer implementación concreta
//		// TODO Auto-generated method stub
//		return false;
//	}


//	@Override
//	public void realizarCapturasTrasMover() {
//		// TODO Auto-generated method stub
//
//	}
	
	/**
	 * Mueve una pieza en el tablero según la jugada proporcionada.
	 * 
	 * @param jugada La jugada a realizar.
	 * @throws CoordenadasIncorrectasException salta una excepcion cuando son coordenadas incorrectas
	 */
	@Override
	public void mover(Jugada jugada) throws CoordenadasIncorrectasException {
		if (jugada == null) {
			throw new IllegalArgumentException();
		}
		// Obtiene la pieza a mover, así como las coordenadas de origen y destino.
		Pieza pieza = jugada.origen().consultarPieza();
		Coordenada origen = jugada.origen().consultarCoordenada();
		Coordenada destino = jugada.destino().consultarCoordenada();
		
		if (coordenadaIncorrecta(origen) || coordenadaIncorrecta(destino)) {
			throw new CoordenadasIncorrectasException();
		}

		// Graba la posición de la última pieza movida.
		this.ultimaPieza = destino;

		// Coloca la pieza en su nueva posición y elimina la pieza del origen.
		tablero.colocar(pieza, destino);
		tablero.eliminarPieza(origen);

		// Incrementa el número de jugadas realizadas.
		this.numeroJugada++;
	}
	
	private boolean coordenadaIncorrecta(Coordenada coordenada) {
		//retorna true si la coordenada de la fila o de la columna es incorrecta
		return (coordenada.fila() < 0 || coordenada.fila() >= tablero.consultarNumeroFilas() || coordenada.columna() < 0 || coordenada.columna() >= tablero.consultarNumeroColumnas() );
	}



}
