package tafl.control;

import java.util.List;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Celda;
import tafl.modelo.Jugada;
import tafl.modelo.Pieza;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.Coordenada;
import tafl.util.Sentido;
import tafl.util.TipoCelda;
import tafl.util.TipoPieza;

/**
 * El `ArbitroArdRi` controla y gestiona el desarrollo del juego Ardri, aplicando
 * reglas, validando movimientos y verificando el estado de la partida.
 * 
 * 
 * @author Jon Ander Incera Moreno Creado 18/12/2023
 * @version 1.0 realizacion del codigo 1.1 comentarios
 * 
 */
public class ArbitroArdRi extends ArbitroAbstracto {
	// Atributos de la clase Arbitro:
	private final Coordenada trono; // Coordenada del trono en el tablero.
	//private Coordenada ultimaPieza; // Coordenada de la última pieza jugada.
	private final int ultColumna; // El índice de la última columna en el tablero.
	private final int ultFila; // El índice de la última fila en el tablero.
	private final int mitad; // La posición de la mitad del tablero (donde está el trono).
	private final Coordenada[] provincias; // Arreglo con las coordenadas de las provincias en el tablero.

	/**
	 * contructor de ardri clase hija
	 * @param tablero describe
	 */
	public ArbitroArdRi(Tablero tablero) {
		super(tablero);
		// el contador de jugadas a cero se inicializa en la clase padre
		// la clase padre lanza una excepción si el tablero es nulo
		
	    // Inicialización de constantes relacionadas con el tablero
	    this.ultColumna = tablero.consultarNumeroColumnas() - 1; // Última columna del tablero.
	    this.ultFila = tablero.consultarNumeroFilas() - 1; // Última fila del tablero.
	    this.mitad = tablero.consultarNumeroFilas() / 2; // Valor de la mitad de las filas del tablero.

	    // Coordenadas de las provincias, un arreglo con las posiciones de las provincias en el tablero.
	    provincias = new Coordenada[4];
	    provincias[0] = new Coordenada(0, 0); // Esquina superior izquierda.
	    provincias[1] = new Coordenada(0, ultColumna); // Esquina superior derecha.
	    provincias[2] = new Coordenada(ultFila, 0); // Esquina inferior izquierda.
	    provincias[3] = new Coordenada(ultFila, ultColumna); // Esquina inferior derecha.

	    trono = new Coordenada(this.mitad, this.mitad); // Coordenada del trono en el tablero.
	}

	/**
	 * Coloca las piezas en sus posiciones iniciales en el tablero, siguiendo las reglas del juego.
	 */
	public void colocarPiezasConfiguracionInicial(){
		// pendiente probar!!
	    int filas = this.ultFila + 1; // Obtiene el número de filas del tablero.
	    int columnas = this.ultColumna + 1; // Obtiene el número de columnas del tablero.

	    for (int i = 0; i < filas; i++) { // Recorre las filas del tablero.
	        for (int j = 0; j < columnas; j++) { // Recorre las columnas del tablero.
	        	if (Math.abs(i - mitad) == 1) {
	        		if (Math.abs(j - mitad) == 1) {
	        			try {
							tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(i, j));
						} catch (CoordenadasIncorrectasException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		} else if (Math.abs(j - mitad) == 3) {
	        			try {
							tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(i, j));
						} catch (CoordenadasIncorrectasException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	        		}
	        	} else if (i == mitad) { // Si está en la fila central del tablero:
	                if (Math.abs(j - mitad) < 4) { // Verifica si la columna está dentro de cierto rango de la fila central.
	                    if (Math.abs(j - mitad) > 1) { // Si la columna no está en la posición de las provincias o el trono:
	                        try {
								this.tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(i, j));
							} catch (CoordenadasIncorrectasException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // Coloca un atacante en esa posición.
	                    } else if (j == mitad) { // Si está en la columna central:
	                        try {
								this.tablero.colocar(new Pieza(TipoPieza.REY), new Coordenada(i, j));
							} catch (CoordenadasIncorrectasException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // Coloca al rey en esa posición.
	                    } else { // Si está en la posición de una provincia o el trono:
	                        try {
								this.tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(i, j));
							} catch (CoordenadasIncorrectasException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // Coloca un defensor en esa posición.
	                    }
	                }
	            } else if (Math.abs(i - mitad) == 3) {
	            	if (Math.abs(j - mitad) == 1) {
	            		try {
							tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(i, j));
						} catch (CoordenadasIncorrectasException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
	            	}
	            }
	            if (j == mitad) { // Si está en la columna central del tablero:
	                if (Math.abs(i - mitad) < 4) { // Verifica si la fila está dentro de cierto rango de la columna central.
	                    if (Math.abs(i - mitad) > 1) { // Si la fila no está en la posición de las provincias o el trono:
	                        try {
								this.tablero.colocar(new Pieza(TipoPieza.ATACANTE), new Coordenada(i, j));
							} catch (CoordenadasIncorrectasException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // Coloca un atacante en esa posición.
	                    } else if (Math.abs(i - mitad) == 1) { // Si está en la posición de una provincia:
	                        try {
								this.tablero.colocar(new Pieza(TipoPieza.DEFENSOR), new Coordenada(i, j));
							} catch (CoordenadasIncorrectasException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} // Coloca un defensor en esa posición.
	                    }
	                }
	            }
	        }
	    }
	    this.turnoActual = Color.NEGRO; // Establece el turno inicial para el jugador negro.
	}

	/**
	 * Comprueba la legalidad de la jugada, siguiendo las reglas del juego.
	 * Solo se aplican las reglas de movimientos descritas en la Sección 1, sin verificar si la partida ha finalizado.
	 * 
	 * @param jugada La jugada que se desea verificar.
	 * @return true si el movimiento es legal, de lo contrario false.
	 * @throws CoordenadasIncorrectasException salta una excepcion cuando son coordenadas incorrectas
	 */
	@Override
	public boolean esMovimientoLegal(Jugada jugada) throws CoordenadasIncorrectasException {
		if (jugada == null) {
			throw new IllegalArgumentException();
		}
	    // Verifica si el movimiento es horizontal o vertical
	    if (!jugada.esMovimientoHorizontalOVertical()) {
	        return false;
	    }

	    // Obtiene la celda de origen y verifica si está vacía
	    Celda origen = jugada.origen();
	    if (origen.estaVacia())
	        return false;

	    // Obtiene la celda de destino y verifica si está vacía
	    Celda destino = jugada.destino();
	    if (!destino.estaVacia())
	        return false;

	    // Obtiene la pieza de la celda de origen
	    Pieza pieza = origen.consultarPieza();

	    // Verifica si la pieza que se mueve corresponde al turno actual
	    if (!pieza.consultarColor().equals(this.turnoActual))
	        return false;

	    // Verifica que el destino no sea igual al origen
	    if (destino.equals(jugada.origen()))
	        return false;

	    // Verifica si una pieza (distinta al rey) intenta ocupar el trono o una provincia
	    if (pieza.consultarTipoPieza() != TipoPieza.REY &&
	            (destino.consultarTipoCelda().equals(TipoCelda.PROVINCIA)
	                    || destino.consultarTipoCelda().equals(TipoCelda.TRONO))) {
	        return false;
	    }
	    
	    //Aqui solo se mueve una posicion adyacente ya que en adrdi cambia las reglas del juego
	    // Obtiene la coordenada de origen y destino de la jugada
	    Coordenada cOrigen = jugada.origen().consultarCoordenada();
	    Coordenada cDestino = jugada.destino().consultarCoordenada();
	    
	    return estaAlLado(cOrigen, cDestino); // retorna true si la posición del origen está justo al lado del destino
	}
	
	/**
	 * Comprueba si el jugador de los Atacantes ha ganado.
	 * @return true si el jugador de los Atacantes ha ganado, de lo contrario false.
	 * @throws CoordenadasIncorrectasException salta una excepcion cuando son coordenadas incorrectas
	 */
	@Override
	public boolean haGanadoAtacante() throws CoordenadasIncorrectasException {
	    // Verifica si no es el turno de los Atacantes, en ese caso, no han ganado
	    if (consultarTurno().equals(Color.BLANCO))
	        return false; 

	    // Obtiene la posición actual del rey
	    Coordenada posicionRey = posicionRey(); 

	    // Verifica si el rey está al lado de la última pieza (atacante) jugada
	    if (!estaAlLado(posicionRey, ultimaPieza))
	        return false;

	    // Si el rey está junto a una provincia, evalúa victoria rodeándolo con una pieza atacante y al lado de la provincia
	    if (estaJuntoAProvincia(posicionRey)) {
	        // Verifica si hay un atacante en horizontal alrededor del rey
	        int atacantes = contarAtacantesHorizontal(posicionRey);
	        if (atacantes == 1)
	            return true;
	        
	        // Verifica si hay un atacante en vertical alrededor del rey
	        atacantes = contarAtacantesVertical(posicionRey);
	        if (atacantes == 1)
	            return true;
	    }
	    
	    // Verifica si el rey está en un borde específico rodeado por atacantes
	    if (posicionRey.columna() == 0 || posicionRey.columna() == ultColumna) {
	        int atacantes = contarAtacantesVertical(posicionRey);
	        if (atacantes == 2)
	            return true;
	    } else if (posicionRey.fila() == 0 || posicionRey.fila() == ultFila) {
	        int atacantes = contarAtacantesHorizontal(posicionRey);
	        if (atacantes == 2)
	            return true;
	    } else if (posicionRey.columna() == 1 || posicionRey.columna() == ultColumna - 1 || posicionRey.fila() == 1
	            || posicionRey.fila() == ultFila - 1) {
	        int atacantes = contarAtacantesHorizontal(posicionRey);
	        if (atacantes == 2)
	            return true;
	        
	        atacantes = contarAtacantesVertical(posicionRey);
	        if (atacantes == 2)
	            return true;
	    }
	    
	    // Si el rey no está en ninguna condición especial, cuenta el número de atacantes en las celdas contiguas
	    List<Celda> contiguas = null;
		try {
			contiguas = tablero.consultarCeldasContiguas(posicionRey);
		} catch (CoordenadasIncorrectasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //Celda[] contiguas = tablero.consultarCeldasContiguas(posicionRey);
	    int atacantes = contarAtacantes(contiguas);

	    if (posicionRey.equals(trono)) // Si el rey está en el trono
	        return atacantes == 4; // Victoria si el rey está rodeado por 4 atacantes en el trono
	    else
	        return atacantes == 3; // Victoria si el rey no está en el trono y está rodeado por 3 atacantes en el resto del tablero.
	}

	/**
	 * Retorna la posición del rey en el tablero.
	 * 
	 * @return Coordenada que representa la posición del rey, o null si no se
	 *         encontró.
	 * @throws CoordenadasIncorrectasException 
	 */
	private Coordenada posicionRey() throws CoordenadasIncorrectasException {
		// Verifica si el trono está vacío, lo que indicaría que el rey no está en el
		// trono.
		if (!tablero.consultarCelda(this.trono).estaVacia())
			return this.trono; // Si el trono no está vacío, devuelve la posición del trono como la del rey.

		// Recorre el tablero para encontrar la posición del rey.
		for (int i = 0; i < tablero.consultarNumeroFilas(); i++) {
			for (int j = 0; j < tablero.consultarNumeroColumnas(); j++) {
				Celda celda = this.tablero.consultarCelda(new Coordenada(i, j));
				// Si la celda contiene al rey, devuelve su posición.
				if (esPieza(celda, TipoPieza.REY))
					return new Coordenada(i, j);
			}
		}

		// Si no se encuentra al rey, devuelve null indicando un error.
		return null; // Error: no se encontró la posición del rey
	}

	/**
	 * Verifica si dos coordenadas están adyacentes (una al lado de la otra).
	 * @param c1 La primera coordenada.
	 * @param c2 La segunda coordenada.
	 * @return true si c1 está adyacente a c2, de lo contrario false.
	 */
	private boolean estaAlLado(Coordenada c1, Coordenada c2) {
	    if (c1 == null || c2 == null)
	        return false;

	    // Comprueba si c1 está en la misma fila y una columna de diferencia o
	    // en la misma columna y una fila de diferencia con respecto a c2
	    return (c1.fila() == c2.fila() && Math.abs(c1.columna() - c2.columna()) == 1)
	            || (c1.columna() == c2.columna() && Math.abs(c1.fila() - c2.fila()) == 1);
	}
	
	/**
	 * Verifica si una coordenada está adyacente a alguna de las provincias.
	 * @param c1 La coordenada a evaluar.
	 * @return true si c1 está adyacente a alguna provincia, de lo contrario false.
	 */
	private boolean estaJuntoAProvincia(Coordenada c1) {
	    for (Coordenada provincia : provincias) {
	        if (estaAlLado(c1, provincia))
	            return true;
	    }
	    return false;
	}

	/**
	 * Cuenta el número de atacantes en las celdas contiguas en dirección horizontal desde la posición dada.
	 * @param posicion La posición a evaluar.
	 * @return El número total de atacantes encontrados en las celdas contiguas horizontales.
	 * @throws CoordenadasIncorrectasException 
	 */
	private int contarAtacantesHorizontal(Coordenada posicion) throws CoordenadasIncorrectasException {
		List<Celda> contiguas = this.tablero.consultarCeldasContiguasEnHorizontal(posicion); // Obtiene las celdas contiguas en dirección horizontal
	    return contarAtacantes(contiguas); // Retorna el número total de atacantes en las celdas contiguas
	}

	/**
	 * Cuenta el número de atacantes en las celdas contiguas en dirección vertical desde la posición dada.
	 * @param posicion La posición a evaluar.
	 * @return El número total de atacantes encontrados en las celdas contiguas verticales.
	 * @throws CoordenadasIncorrectasException 
	 */
	private int contarAtacantesVertical(Coordenada posicion) throws CoordenadasIncorrectasException {
		List<Celda> contiguas = this.tablero.consultarCeldasContiguasEnVertical(posicion); // Obtiene las celdas contiguas en dirección vertical
	    return contarAtacantes(contiguas); // Retorna el número total de atacantes en las celdas contiguas
	}

	/**
	 * Cuenta el número de atacantes en un conjunto de celdas contiguas.
	 * @param contiguas Un arreglo de celdas contiguas a evaluar.
	 * @return El número total de atacantes encontrados en las celdas contiguas.
	 */
	private int contarAtacantes(List<Celda> contiguas) {
	    int atacantes = 0; // Inicializa el contador de atacantes

	    for (Celda celda : contiguas) { // Itera sobre las celdas contiguas
	        if (esAtacante(celda)) // Verifica si la celda actual contiene un atacante
	            atacantes++; // Incrementa el contador si hay un atacante en la celda
	    }
	    return atacantes; // Retorna el número total de atacantes encontrados en las celdas contiguas
	}

	/**
	 * Verifica si la celda contiene una pieza del tipo especificado.
	 * @param celda La celda a verificar.
	 * @param tipo El tipo de pieza a comparar.
	 * @return true si la celda no está vacía y contiene una pieza del tipo especificado, de lo contrario false.
	 */
	private boolean esPieza(Celda celda, TipoPieza tipo) {
	    return !celda.estaVacia() && celda.consultarPieza().consultarTipoPieza() == tipo;
	}

	/**
	 * Verifica si la celda contiene un atacante.
	 * @param celda La celda a verificar.
	 * @return true si la celda no es nula y contiene un atacante, de lo contrario false.
	 */
	private boolean esAtacante(Celda celda) {
	    return celda != null && esPieza(celda, TipoPieza.ATACANTE);
	}

	@Override
	public boolean haGanadoRey() throws CoordenadasIncorrectasException {
		// Si es el turno del jugador NEGRO, no ha ganado el rey.
		if (consultarTurno().equals(Color.NEGRO)) {
			return false;
		}
		Coordenada pr = posicionRey();
		// Itera sobre las provincias.
		for (Coordenada provincia : provincias) {
			// Si la posición del rey coincide con alguna provincia, el rey ha ganado.
			if (pr.equals(provincia)) {
				return true; // Si el rey está en una provincia -> ganó el rey
			}
		}
		// Si el rey está en uno de los lados -> gana el rey
		int fila = pr.fila();
		int col = pr.columna();
		//comprobamos que si el rey llega a la fila cero o a la fila seis y despues
		//comprobamos si el rey llega a la columna cero y seis
		if (fila == 0 || fila == ultFila || col == 0 || col == ultColumna)
			return true;
		return false;
	}

	@Override
	public void realizarCapturasTrasMover() throws CoordenadasIncorrectasException {
		// Consulta la celda donde se movió la última pieza.
		Celda celda = tablero.consultarCelda(ultimaPieza);
		// Si la última pieza es un defensor, captura a los atacantes.
		if (esDefensor(celda)) { // Defensor
			capturarAtacantes();
			// Si la última pieza es un atacante, captura a los defensores.
		} else if (esAtacante(celda)) { // esAtacante
			capturarDefensores();
		}
	}


	/**
	 * Verifica si la celda contiene un defensor.
	 * @param celda La celda a verificar.
	 * @return true si la celda no es nula y contiene un defensor, de lo contrario false.
	 */
	private boolean esDefensor(Celda celda) {
	    return celda != null && esPieza(celda, TipoPieza.DEFENSOR);
	}

	/**
	 * Realiza capturas de atacantes según ciertas condiciones: - Si el rey no está
	 * en el trono y la última pieza está casi al lado del trono, captura atacante
	 * contra el trono. - Si la última pieza está casi junto a una provincia,
	 * captura atacante contra esa provincia. - Realiza capturas de atacantes en
	 * todas las direcciones adyacentes a la última pieza.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarAtacantes() throws CoordenadasIncorrectasException {

		// Si el rey no está en el trono y la última pieza está casi al lado del trono,
		// captura atacante contra el trono.
		if (!this.posicionRey().equals(this.trono) && // si el rey no está en el trono
				estaCasiAlLado(ultimaPieza, this.trono)) // está casi al lado del trono
			capturarAtacanteContraElTrono();
		// Si la última pieza está casi junto a una provincia, captura atacante contra
		// esa provincia.
		if (estaCasiJuntoAProvincia(ultimaPieza))
			capturarAtacanteContraProvincia();
		// Realiza capturas de atacantes en todas las direcciones adyacentes a la última
		// pieza.
		capturarAtacantesTodasDirecciones();
	}

	/**
	 * Realiza capturas de defensores según ciertas condiciones: - Si el rey no está
	 * en el trono y la última pieza está casi al lado del trono, captura defensor
	 * contra el trono. - Si la última pieza está casi junto a una provincia,
	 * captura defensor contra esa provincia. - Realiza capturas de defensores en
	 * todas las direcciones adyacentes a la última pieza.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarDefensores() throws CoordenadasIncorrectasException {
		// Si el rey no está en el trono y la última pieza está casi al lado del trono,
		// captura defensor contra el trono.
		if (!this.posicionRey().equals(this.trono) && // si el rey no está en el trono
				estaCasiAlLado(ultimaPieza, this.trono)) // está casi al lado del trono
			capturarDefensorContraElTrono();
		// Si la última pieza está casi junto a una provincia, captura defensor contra
		// esa provincia.
		if (estaCasiJuntoAProvincia(ultimaPieza))
			capturarDefensorContraProvincia();

		// Realiza capturas de defensores en todas las direcciones adyacentes a la
		// última pieza.
		capturarDefensoresTodasDirecciones();
	}

	/**
	 * Verifica si dos coordenadas están separadas por una casilla en horizontal o vertical.
	 * @param c1 La primera coordenada.
	 * @param c2 La segunda coordenada.
	 * @return true si c1 está a una casilla de distancia en horizontal o vertical de c2, de lo contrario false.
	 */
	private boolean estaCasiAlLado(Coordenada c1, Coordenada c2) {
	    if (c1 == null || c2 == null)
	        return false;

	    // Comprueba si c1 está en la misma fila y dos columnas de diferencia o
	    // en la misma columna y dos filas de diferencia con respecto a c2
	    return c1.fila() == c2.fila() && Math.abs(c1.columna() - c2.columna()) == 2
	            || c1.columna() == c2.columna() && Math.abs(c1.fila() - c2.fila()) == 2;
	}

	/**
	 * Captura un atacante contra el trono basado en la posición de la última pieza
	 * movida. Si la última pieza está cerca del trono, captura un atacante en la
	 * dirección correspondiente.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarAtacanteContraElTrono() throws CoordenadasIncorrectasException {
		// Verifica si la fila de la última pieza coincide con la mitad del tablero
		if (ultimaPieza.fila() == this.mitad) {
			// Si la columna de la última pieza es menor que la mitad, captura en dirección
			// este
			if (ultimaPieza.columna() < mitad) {
				capturarAtacante(ultimaPieza, Sentido.HORIZONTAL_E);
			} else {
				// Si la columna es mayor o igual a la mitad, captura en dirección oeste
				capturarAtacante(ultimaPieza, Sentido.HORIZONTAL_O);
			}
		} else { // Si la última pieza no está en la mitad de las filas
			// Verifica si la última pieza está encima o debajo de la mitad del tablero
			if (ultimaPieza.fila() < mitad) {
				// Si está arriba de la mitad, captura en dirección norte
				capturarAtacante(ultimaPieza, Sentido.VERTICAL_N);
			} else {
				// Si está abajo o a la misma altura, captura en dirección sur
				capturarAtacante(ultimaPieza, Sentido.VERTICAL_S);
			}
		}
	}

	/**
	 * Verifica si una coordenada está a una casilla de distancia en horizontal o vertical de alguna provincia.
	 * @param c1 La coordenada a evaluar.
	 * @return true si c1 está a una casilla de distancia en horizontal o vertical de alguna provincia, de lo contrario false.
	 */
	private boolean estaCasiJuntoAProvincia(Coordenada c1) {
	    for (Coordenada provincia : provincias) {
	        if (estaCasiAlLado(c1, provincia))
	            return true;
	    }
	    return false;
	}

	/**
	 * Realiza la captura de un atacante contra una provincia adyacente.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarAtacanteContraProvincia() throws CoordenadasIncorrectasException {
		/* Captura de atacante contra provincia en arriba/abajo */

		// Si la última pieza se encuentra dos filas por encima del borde inferior,
		// captura un atacante en la dirección norte.
		if (ultimaPieza.fila() == 2) {
			capturarAtacante(ultimaPieza, Sentido.VERTICAL_N);

			// Si la última pieza se encuentra dos filas por debajo del borde superior,
			// captura un atacante en la dirección sur.
		} else if (ultimaPieza.fila() == ultFila - 2) {
			capturarAtacante(ultimaPieza, Sentido.VERTICAL_S);

			/* Captura de atacante contra provincia en izq/der */

			// Si la última pieza se encuentra dos columnas a la izquierda del borde
			// derecho,
			// captura un atacante en la dirección oeste.
		} else if (ultimaPieza.columna() == 2) {
			capturarAtacante(ultimaPieza, Sentido.HORIZONTAL_O);
			// Si la última pieza se encuentra dos columnas a la derecha del borde
			// izquierdo,
			// captura un atacante en la dirección este.
		} else if (ultimaPieza.columna() == ultColumna - 2) {
			capturarAtacante(ultimaPieza, Sentido.HORIZONTAL_E);
		}
	}

	/**
	 * Realiza capturas de atacantes en todas las direcciones alrededor de la última
	 * pieza movida.
	 * 
	 * test de capturar de atacante contra provicia movimiendo un defensor
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarAtacantesTodasDirecciones() throws CoordenadasIncorrectasException {
		// Captura atacantes rodeados en la dirección norte de la última pieza
		capturarAtacanteRodeado(ultimaPieza, Sentido.VERTICAL_N);
		// Captura atacantes rodeados en la dirección sur de la última pieza
		capturarAtacanteRodeado(ultimaPieza, Sentido.VERTICAL_S);
		// Captura atacantes rodeados en la dirección este de la última pieza
		capturarAtacanteRodeado(ultimaPieza, Sentido.HORIZONTAL_E);
		// Captura atacantes rodeados en la dirección oeste de la última pieza
		capturarAtacanteRodeado(ultimaPieza, Sentido.HORIZONTAL_O);
	}

	/**
	 * Realiza la captura de un defensor en las cercanías del trono.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarDefensorContraElTrono() throws CoordenadasIncorrectasException {
		// Si la última pieza está en la misma fila que la mitad del tablero.
		if (ultimaPieza.fila() == this.mitad) {
			// Si la última pieza está a la izquierda de la mitad del tablero, captura un
			// defensor en dirección este.
			if (ultimaPieza.columna() < mitad) {
				capturarDefensor(ultimaPieza, Sentido.HORIZONTAL_E);
				// Si la última pieza está a la derecha de la mitad del tablero, captura un
				// defensor en dirección oeste.
			} else {
				capturarDefensor(ultimaPieza, Sentido.HORIZONTAL_O);
			}

			// Si la última pieza está en la misma columna que la mitad del tablero.
		} else { // ultimaPieza.columna() == mitad
			// Si la última pieza está arriba de la mitad del tablero, captura un defensor
			// en dirección norte.
			if (ultimaPieza.fila() < mitad) {
				capturarDefensor(ultimaPieza, Sentido.VERTICAL_N);
				// Si la última pieza está abajo de la mitad del tablero, captura un defensor en
				// dirección sur.
			} else {
				capturarDefensor(ultimaPieza, Sentido.VERTICAL_S);
			}
		}
	}

	/**
	 * Realiza la captura de un defensor contra una provincia adyacente.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarDefensorContraProvincia() throws CoordenadasIncorrectasException {
		/* Captura de defensor contra provincia en arriba/abajo */

		// Si la última pieza se encuentra dos filas por encima del borde inferior,
		// captura un defensor en la dirección norte.
		if (ultimaPieza.fila() == 2) {
			capturarDefensor(ultimaPieza, Sentido.VERTICAL_N);

			// Si la última pieza se encuentra dos filas por debajo del borde superior,
			// captura un defensor en la dirección sur.
		} else if (ultimaPieza.fila() == ultFila - 2) { // ultFila = 6
			capturarDefensor(ultimaPieza, Sentido.VERTICAL_S);

			/* Captura de defensor contra provincia en izq/der */

			// Si la última pieza se encuentra dos columnas a la izquierda del borde
			// derecho,
			// captura un defensor en la dirección oeste.
		} else if (ultimaPieza.columna() == 2) {
			capturarDefensor(ultimaPieza, Sentido.HORIZONTAL_O);

			// Si la última pieza se encuentra dos columnas a la derecha del borde
			// izquierdo,
			// captura un defensor en la dirección este.
		} else if (ultimaPieza.columna() == ultColumna - 2) {
			capturarDefensor(ultimaPieza, Sentido.HORIZONTAL_E);
		}
	}

	/**
	 * Realiza la captura de defensores en todas las direcciones adyacentes a la
	 * última pieza.
	 * 
	 * test de capturar de defensor contra el trono
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarDefensoresTodasDirecciones() throws CoordenadasIncorrectasException {
		// Captura un defensor en dirección norte de la última pieza si está rodeado.
		capturarDefensorRodeado(ultimaPieza, Sentido.VERTICAL_N);
		// Captura un defensor en dirección sur de la última pieza si está rodeado.
		capturarDefensorRodeado(ultimaPieza, Sentido.VERTICAL_S);
		// Captura un defensor en dirección este de la última pieza si está rodeado.
		capturarDefensorRodeado(ultimaPieza, Sentido.HORIZONTAL_E);
		// Captura un defensor en dirección oeste de la última pieza si está rodeado.
		capturarDefensorRodeado(ultimaPieza, Sentido.HORIZONTAL_O);
	}

	/**
	 * Captura un atacante si se encuentra en una posición específica en relación
	 * con una dirección dada.
	 *
	 * @param c Coordenada que representa la posición de referencia para buscar al
	 *          atacante.
	 * @param d Dirección en la que se verifica si hay un atacante para realizar la
	 *          captura.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarAtacante(Coordenada c, Sentido d) throws CoordenadasIncorrectasException { // capturas contra provincias/trono
		// Obtiene la celda vecina en la dirección d desde la coordenada c
		Celda celda = vecina(ultimaPieza, d);
		if (esAtacante(celda)) { // Comprueba si hay un atacante en la celda obtenida
			tablero.eliminarPieza(celda.consultarCoordenada()); // Realiza la captura del atacante
		}
	}

	/**
	 * Captura un atacante si está rodeado por dos defensores en una dirección
	 * específica.
	 *
	 * @param up Coordenada de referencia para determinar la posición del atacante.
	 * @param d  Dirección en la que se verifica si el atacante está rodeado por
	 *           defensores.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarAtacanteRodeado(Coordenada up, Sentido d) throws CoordenadasIncorrectasException {
		Coordenada atacante = vecino(up, d); // Obtiene la coordenada del vecino en la dirección d
		if (!tablero.estaEnTablero(atacante))
			return;
		if (esAtacante(tablero.consultarCelda(atacante))) { // Comprueba si hay un atacante en la coordenada atacante
			if (d == Sentido.HORIZONTAL_E || d == Sentido.HORIZONTAL_O) { // Si es dirección horizontal
				int defensor = contarDefensoresHorizontal(atacante); // Cuenta defensores en dirección horizontal
				if (defensor == 2) { // Si hay dos defensores, captura al atacante
					tablero.eliminarPieza(atacante); // Captura al atacante
				}
			} else { // Si es dirección vertical
				int defensor = contarDefensoresVertical(atacante); // Cuenta defensores en dirección vertical
				if (defensor == 2) { // Si hay dos defensores, captura al atacante
					tablero.eliminarPieza(atacante); // Captura al atacante
				}
			}
		}
	}

	/**
	 * Captura un defensor si se encuentra en una posición específica en relación
	 * con una dirección dada.
	 *
	 * @param c Coordenada que representa la posición de referencia para buscar al
	 *          defensor.
	 * @param d Dirección en la que se verifica si hay un defensor para realizar la
	 *          captura.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarDefensor(Coordenada c, Sentido d) throws CoordenadasIncorrectasException { // capturas contra provincias/trono
		// Obtiene la celda vecina en la dirección d desde la coordenada c
		Celda celda = vecina(ultimaPieza, d);
		if (esDefensor(celda)) { // Comprueba si hay un defensor en la celda obtenida
			try {
				tablero.eliminarPieza(celda.consultarCoordenada());
			} catch (CoordenadasIncorrectasException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// Realiza la captura del defensor
		}
	}

	/**
	 * Captura un defensor si está rodeado por dos atacantes en una dirección
	 * específica.
	 *
	 * @param up Coordenada de referencia para determinar la posición del defensor.
	 * @param d  Dirección en la que se verifica si el defensor está rodeado por
	 *           atacantes.
	 * @throws CoordenadasIncorrectasException 
	 */
	private void capturarDefensorRodeado(Coordenada up, Sentido d) throws CoordenadasIncorrectasException {
		Coordenada defensor = vecino(up, d); // Obtiene la coordenada del vecino en la dirección d
		if (!tablero.estaEnTablero(defensor))
			return;
		if (esDefensor(tablero.consultarCelda(defensor))) { // Comprueba si hay un defensor en la coordenada defensor
			if (d == Sentido.HORIZONTAL_E || d == Sentido.HORIZONTAL_O) { // Si es dirección horizontal
				int atacante = contarAtacantesHorizontal(defensor); // Cuenta atacantes en dirección horizontal
				if (atacante == 2) { // Si hay dos atacantes, captura al defensor
					tablero.eliminarPieza(defensor); // Captura al defensor
				}
			} else {// Si es dirección vertical
				int atacante = contarAtacantesVertical(defensor); // Cuenta atacantes en dirección vertical
				if (atacante == 2) { // Si hay dos atacantes, captura al defensor
					tablero.eliminarPieza(defensor); // Captura al defensor
				}
			}
		}
	}

	/**
	 * Devuelve la celda vecina a una coordenada en una dirección específica.
	 *
	 * @param c Coordenada que representa la posición base.
	 * @param d Dirección en la que se busca la celda vecina.
	 * @return La celda vecina a la coordenada c en la dirección d.
	 * @throws CoordenadasIncorrectasException 
	 */
	private Celda vecina(Coordenada c, Sentido d) throws CoordenadasIncorrectasException {
		// Devuelve la celda vecina en la dirección d desde la coordenada c
		return tablero.consultarCelda(vecino(c, d));
	}

	/**
	 * Devuelve la coordenada del vecino en una dirección específica desde una
	 * coordenada dada.
	 *
	 * @param c Coordenada base desde la cual se busca el vecino.
	 * @param d Dirección en la que se encuentra el vecino.
	 * @return La coordenada del vecino en la dirección d desde la coordenada c.
	 */
	private Coordenada vecino(Coordenada c, Sentido d) {
		// Calcula y devuelve la coordenada del vecino en la dirección d desde la
		// coordenada c
		return new Coordenada(c.fila() + d.consultarDesplazamientoEnFilas(),
				c.columna() + d.consultarDesplazamientoEnColumnas());
	}

	/**
	 * Cuenta el número de defensores en las celdas contiguas en dirección horizontal desde la posición dada.
	 * @param posicion La posición a evaluar.
	 * @return El número total de defensores encontrados en las celdas contiguas horizontales.
	 * @throws CoordenadasIncorrectasException 
	 */
	private int contarDefensoresHorizontal(Coordenada posicion) throws CoordenadasIncorrectasException {
		List<Celda> contiguas = tablero.consultarCeldasContiguasEnHorizontal(posicion); // Obtiene las celdas contiguas en dirección horizontal
	    //Celda[] contiguas = tablero.consultarCeldasContiguasEnHorizontal(posicion); // Obtiene las celdas contiguas en dirección horizontal
	    return contarDefensores(contiguas); // Retorna el número total de defensores en las celdas contiguas
	}

	/**
	 * Cuenta el número de defensores en las celdas contiguas en dirección vertical desde la posición dada.
	 * @param posicion La posición a evaluar.
	 * @return El número total de defensores encontrados en las celdas contiguas verticales.
	 * @throws CoordenadasIncorrectasException 
	 */
	private int contarDefensoresVertical(Coordenada posicion) throws CoordenadasIncorrectasException {
		List<Celda> contiguas = tablero.consultarCeldasContiguasEnVertical(posicion); // Obtiene las celdas contiguas en dirección vertical
	    //Celda[] contiguas = tablero.consultarCeldasContiguasEnVertical(posicion); // Obtiene las celdas contiguas en dirección vertical
	    return contarDefensores(contiguas); // Retorna el número total de defensores en las celdas contiguas
	}

	/**
	 * Cuenta el número de defensores presentes en las celdas contiguas.
	 * @param contiguas Un array de celdas contiguas a evaluar.
	 * @return El número total de defensores encontrados en las celdas contiguas.
	 */
	private int contarDefensores(List<Celda> contiguas) {
		int defensores = 0; // Inicializa el contador de defensores
		for (Celda celda : contiguas) {
			if (esDefensor(celda))// Verifica si la celda contiene un defensor
				defensores++; // Incrementa el contador si se encuentra un defensor
		}
		return defensores;// Devuelve el número total de defensores encontrados
	}
	
}
