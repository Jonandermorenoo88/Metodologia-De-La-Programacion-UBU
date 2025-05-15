package tafl.textui;

import java.util.Scanner;

import tafl.control.Arbitro;
import tafl.control.ArbitroArdRi;
import tafl.control.ArbitroBrandubh;
import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.excepcion.TipoArbitroException;
import tafl.modelo.Celda;
import tafl.modelo.Jugada;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.Coordenada;
import tafl.util.Traductor;

/**
 * Tafl en modo texto.
 * 
 * Se abusa del uso del modificador static tanto en atributos como en métodos
 * para comprobar su similitud a variables globales y funciones globales de
 * otros lenguajes.
 * 
 * La programación en este código sigue más el paradigma de programación
 * estructurada en mayor medida que la orientación a objetos.
 * 
 * En algunos casos los métodos estáticos son meros envoltorios o "wrappers" de
 * invocaciones a métodos del árbitro.
 *
 * @author <a href="rmartico@ubu.es">Raúl Marticorena</a>
 * @author AÑADIR COAUTORES...
 * @since 1.0
 * @version 1.0
 * @see tafl.modelo
 * @see tafl.control
 * @see tafl.util
 */
public class Tafl {

	/** Tamaño en caracteres de una jugada. */
	private static final int TAMANO_JUGADA = 4;

	/** Posición en el texto de una jugada de la coordenada destino. */
	private static final int INICIO_COORDENADA_DESTINO = 2;

	/** Texto para interrumpir la partida. */
	private static final String TEXTO_SALIR = "salir";

	/**
	 * Tablero.
	 */
	private static Tablero tablero;

	/**
	 * Arbitro.
	 */
	private static Arbitro arbitro;

	/**
	 * Lector por teclado.
	 */
	private static Scanner scanner;

	/** Oculta el constructor por defecto. */
	private Tafl() {
	}

	/**
	 * Método raíz.
	 * 
	 * @param args argumentos de entrada
	 * @throws TipoArbitroException cuando no se elige un arbitro se sale una excepcion
	 * @throws CoordenadasIncorrectasException cuando son coordenadas nulas
	 */
	public static void main(String[] args) throws TipoArbitroException, CoordenadasIncorrectasException {
		// COMPLETAR POR EL ALUMNADO
		// REUTILIZAR AQUELLOS MÉTODOS YA PROPORCIONADOS QUE SEAN NECESARIOS
		try {
			if (args.length == 0) {
				mostrarErrorSeleccionandoTipoArbitro();
			} else {
				inicializarPartida(args);
				mostrarMensajeBienvenida();
				Jugada jugada;
				while (true) {
					mostrarTablero();
					System.out.println();
					String movimiento = recogerTextoDeJugadaPorTeclado();
					if (comprobarSalir(movimiento))
						break; // terminar
					if (validarFormato(movimiento)) {
						jugada = extraerJugada(movimiento);
						if (esLegal(jugada)) {
							realizarMovimientoYCapturas(jugada);
							if (comprobarFinalizacionPartida()) {
								mostrarGanador();
								break; // terminar
							}
							cambiarTurnoPartida();
						} else {
							mostrarErrorPorMovimientoIlegal(movimiento);
						}
					} else {
						mostrarErrorEnFormatoDeEntrada();
					}
				}
				finalizarPartida();
				cerrarRecursos();
			}
		} catch (Exception ex) {
			mostrarErrorInterno(new RuntimeException(ex));
		}
	}

	/**
	 * Inicializa el estado de los elementos de la partida.
	 * 
	 * @param args argumentos por teclaro para elegir el árbitro concreto a
	 *             instanciar
	 * @throws TipoArbitroException si el tipo de arbitro solicitado no está entre
	 *                              los permitidos
	 */
	private static void inicializarPartida(String[] args) throws TipoArbitroException {
		// Inicializaciones
		tablero = new Tablero();
		if (args.length == 0) {
			arbitro = new ArbitroBrandubh(tablero);
		} else if (args[0].equalsIgnoreCase("brandubh")) {
			arbitro = new ArbitroBrandubh(tablero);
		} else if (args[0].equalsIgnoreCase("ardri")) {
			arbitro = new ArbitroArdRi(tablero);
		} else {
			/*
			 * Esta solución se podría replantear con un método invocado previamente que
			 * valide el argumento en lugar de lanzar excepción. Es el típico contra-ejemplo
			 * en el que se gestiona el control de flujo con excepciones, no siendo la
			 * opción más recomendable.
			 */
			throw new TipoArbitroException(
					"Error en seleccion de tipo de árbitro para variante de Tafl con valor: " + args[0]);
		}
		// Cargar figuras...
		arbitro.colocarPiezasConfiguracionInicial();
		// Abrimos la lectura desde teclado
		scanner = new Scanner(System.in);
	}

	/**
	 * Recoge el texto de la jugada por teclado.
	 * 
	 * @return jugada jugada en formato texto
	 */
	private static String recogerTextoDeJugadaPorTeclado() {
		System.out
				.print("Introduce jugada turno con piezas de color " + arbitro.consultarTurno() + " (formato cfcf): ");
		return scanner.next();
	}

	/**
	 * Comprueba si se quiere finalizar la partida por parte de los usuarios.
	 * 
	 * @param jugada jugada en formato texto
	 * @return true si el usuario introduce salir, false en caso contrario
	 */
	private static boolean comprobarSalir(String jugada) {
		return jugada.equalsIgnoreCase(TEXTO_SALIR);
	}

	/**
	 * Valida la corrección del formato de la jugada. Solo comprueba la corrección
	 * del formato de entrada en cuanto al tablero, no la validez de la jugada en
	 * cuanto a las reglas del juego.
	 * 
	 * La jugada tiene que tener cuatro caracteres y contener letras y números de
	 * acuerdo a las reglas de la notación algebraica.
	 * 
	 * Otra mejor solución alternativa es el uso de expresiones regulares (se verán
	 * en la asignatura de 3º Procesadores del Lenguaje).
	 * 
	 * @param textoJugada a validar
	 * @return true si el formato de la jugada es correcta según las coordenadas
	 *         disponibles del tablero
	 */
	private static boolean validarFormato(String textoJugada) {
		if (textoJugada.length() == TAMANO_JUGADA) {
			String origen = textoJugada.substring(0, INICIO_COORDENADA_DESTINO);
			String destino = textoJugada.substring(INICIO_COORDENADA_DESTINO, TAMANO_JUGADA);
			// comprobar si ambos textos son correctos
			return Traductor.esTextoCorrectoParaCoordenada(origen) && Traductor.esTextoCorrectoParaCoordenada(destino);
		}
		return false;
	}

	/**
	 * Extrae la jugada a partir del texto introducido por teclado.
	 * 
	 * Se requiere que el texto haya sido validado previamente en cuanto al formato
	 * requerido en notación algebraica para un tablero de 7x7.
	 * 
	 * @param jugadaTexto texto con la jugada
	 * @return jugada
	 * @see #extraerCoordenada(String, int, int)
	 * @throws RuntimeException si se intenta acceder a jugada con coordenadas
	 *                          incorrectas que deberían haber sido validadas
	 *                          previamente
	 */
	private static Jugada extraerJugada(String jugadaTexto) {
		try {
			assert validarFormato(jugadaTexto) : "El texto ha debido ser validado previamente en cuanto a formato.";
			Coordenada coordenadaOrigen = extraerCoordenada(jugadaTexto, 0, INICIO_COORDENADA_DESTINO);
			Coordenada coordenadaDestino = extraerCoordenada(jugadaTexto, INICIO_COORDENADA_DESTINO, TAMANO_JUGADA);
			Celda origen = tablero.consultarCelda(coordenadaOrigen);
			Celda destino = tablero.consultarCelda(coordenadaDestino);
			return new Jugada(origen, destino);
		} catch (CoordenadasIncorrectasException ex) {
			throw new RuntimeException("Error en acceso a celdas con coordenadas mal obtenidas o mal validadas.", ex);
		}
	}

	/**
	 * Extrae una coordenada a partir del texto de entrada y de las posiciones
	 * [incio, fin) indicadas.
	 * 
	 * Dada una jugada en texto, extraerá la coordenada origen o destino, en función
	 * de la posición de inicio y fin dada.
	 * 
	 * @param jugada texto en formato notación algebraica (e.g. a1a3)
	 * @param inicio posición en el texto a partir del cual leer
	 * @param fin    posición final - 1, hasta donde leer el texto
	 * @return coordenada o null, si no es posible extraerla
	 */
	private static Coordenada extraerCoordenada(String jugada, int inicio, int fin) {
		if (jugada.length() != TAMANO_JUGADA)
			return null;
		String textoExtraido = jugada.substring(inicio, fin);
		return Traductor.consultarCoordenadaParaNotacionAlgebraica(textoExtraido);
	}

	/**
	 * Comprueba la legalidad de la jugada.
	 * 
	 * @param jugada jugada
	 * @return true si es legal, false en caso contrario
	 * @throws CoordenadasIncorrectasException
	 */
	private static boolean esLegal(Jugada jugada) {
		try {
			return arbitro.esMovimientoLegal(jugada);
		} catch (CoordenadasIncorrectasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Realizar la jugada completando el movimiento y las capturas correspondientes.
	 * 
	 * @param jugada jugada
	 * @throws IllegalArgumentException si se detecta un intento de realizar el
	 *                                  movimiento con jugada incorrecta previamente
	 *                                  validada
	 */
	private static void realizarMovimientoYCapturas(Jugada jugada) {
		try {
			arbitro.mover(jugada);
			arbitro.realizarCapturasTrasMover();
		} catch (CoordenadasIncorrectasException ex) {
			throw new IllegalArgumentException(
					"No debería intentar realizarse una jugada como " + jugada + " incorrecta.", ex);
		}
	}

	/**
	 * Comprueba si está finalizada la partida.
	 * 
	 * @return true si hay victoria de atacante o defensor, false en caso contrario
	 * @throws CoordenadasIncorrectasException
	 */
	private static boolean comprobarFinalizacionPartida() {
		try {
			return arbitro.haGanadoAtacante() || arbitro.haGanadoRey();
		} catch (CoordenadasIncorrectasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Cambia el turno de la partida.
	 */
	private static void cambiarTurnoPartida() {
		arbitro.cambiarTurno();
	}

	/**
	 * Finaliza la partida informando al usuario.
	 */
	private static void finalizarPartida() {
		System.out.println("Partida finalizada.");
	}

	/**
	 * Cierre de recursos abiertos en la aplicación.
	 * 
	 * En este ejemplo solo se ha abierto el scanner para leer del teclado.
	 */
	private static void cerrarRecursos() {
		if (scanner != null) {
			scanner.close();
		}
	}

	// Métodos para mostrar información en pantalla...

	/**
	 * Muestra el mensaje de bienvenida con instrucciones para finalizar la partida.
	 */
	private static void mostrarMensajeBienvenida() {
		System.out.println("Bienvenido al juego del Tafl 1.0 - Modo: " + arbitro.getClass().getSimpleName());
		System.out.println(
				"Atacan piezas de color " + Color.NEGRO + " y defienden piezas de color " + Color.BLANCO + ".");
		System.out.println("Para interrumpir partida introduzca \"salir\".");
		System.out.println("Disfrute de la partida...");
	}

	/**
	 * Muestra el ganador de la partida en pantalla.
	 * 
	 * @throws CoordenadasIncorrectasException
	 */
	private static void mostrarGanador() {
		try {
			if (arbitro.haGanadoAtacante()) {
				System.out.printf("%nHa ganado la partida el jugador atacante con piezas de color %s.%n",
						arbitro.consultarTurno());
			} else if (arbitro.haGanadoRey()) {
				System.out.printf("%nHa ganado la partida el jugador defensor con piezas de color %s.%n",
						arbitro.consultarTurno());
			} else {
				System.out.println("\nNo hay ganador.");
			}
		} catch (CoordenadasIncorrectasException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Muestra la información de error en el formato de entrada, mostrando ejemplos.
	 */
	private static void mostrarErrorEnFormatoDeEntrada() {
		System.out.println();
		System.out.println("Error en el formato de entrada.");
		System.out.println(
				"El formato debe ser letranumeroletranumero, por ejemplo a7a5 o g2e2, o bien introducir la cadena \"salir\" para finalizar la partida.");
		System.out.println("Las letras deben estar en el rango [a,g] y los números en el rango [1,7].");
	}

	/**
	 * Informa de la ilegalidad de la jugada intentada.
	 * 
	 * @param textoJugada texto de la jugada introducido por teclado
	 */
	private static void mostrarErrorPorMovimientoIlegal(String textoJugada) {
		System.out.printf("%nLa jugada %s es ilegal.%nRevise las reglas del juego.%n", textoJugada);
	}

	/**
	 * Muestra el estado del tablero con sus piezas actuales en pantalla.
	 */
	private static void mostrarTablero() {
		System.out.println();
		System.out.println(tablero.aTexto());
	}

	/**
	 * Muestra mensaje de error grave si el tipo de árbitro no es ninguno de los dos
	 * disponibles.
	 */
	private static void mostrarErrorSeleccionandoTipoArbitro() {
		System.err
				.println("El tipo de árbitro seleccionado no se corresponde con ninguna de las dos opciones válidas.");
		System.err.println("Debe introducir \"brandubh\" o \"ardri\".");
	}

	/**
	 * Muestra mensaje de error grave por error en el código del que no podemos
	 * recuperarnos.
	 * 
	 * @param ex excepción generada
	 */
	private static void mostrarErrorInterno(RuntimeException ex) {
		System.err.println("Error interno en código a corregir por el equipo informático.");
		System.err.println("Mensaje asociado de error: " + ex.getMessage());
		System.err.println("Traza detallada del error a reportar:");
		ex.printStackTrace();
		// sería mejor solución mandar dicha informacion de la traza a un fichero de log
		// en lugar de a la consola, pero esta solución se verá en otras asignaturas
	}

}