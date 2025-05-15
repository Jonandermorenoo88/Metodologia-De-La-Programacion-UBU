package tafl.control;

import tafl.excepcion.CoordenadasIncorrectasException;
import tafl.modelo.Jugada;
import tafl.modelo.Tablero;
import tafl.util.Color;
import tafl.util.TipoPieza;

/**
 * Interfaz que define el comportamiento del árbitro en un juego de Tafl.
 * El árbitro controla el flujo del juego y valida las jugadas realizadas.
 * 
 * Creado 18/12/2023
 * @version 1.0 realizacion del codigo 
 * 			1.1 comentarios
 */
public interface Arbitro {

    /**
     * Cambia el turno actual del juego.
     */
    public void cambiarTurno();

    /**
     * Coloca piezas en el tablero en las coordenadas especificadas.
     * 
     * @param tipo           Tipos de piezas a colocar.
     * @param coordenadas    Coordenadas donde se colocarán las piezas.
     * @param turnoActual    Color del jugador que coloca las piezas.
     * @throws CoordenadasIncorrectasException Cuando las coordenadas son incorrectas.
     */
    public void colocarPiezas(TipoPieza[] tipo, int[][] coordenadas, Color turnoActual) throws CoordenadasIncorrectasException;

    /**
     * Consulta el número de jugada actual.
     * 
     * @return El número de la jugada actual.
     */
    public int consultarNumeroJugada();

    /**
     * Consulta el estado actual del tablero.
     * 
     * @return El tablero actual del juego.
     */
    public Tablero consultarTablero();

    /**
     * Consulta el color del jugador que tiene el turno actualmente.
     * 
     * @return El color del jugador con el turno actual.
     */
    public Color consultarTurno();

    /**
     * Verifica si una jugada es legal.
     * 
     * @param jugada La jugada a validar.
     * @return true si la jugada es legal, false en caso contrario.
     * @throws CoordenadasIncorrectasException Cuando las coordenadas son incorrectas.
     */
    public boolean esMovimientoLegal(Jugada jugada) throws CoordenadasIncorrectasException;

    /**
     * Verifica si el atacante ha ganado el juego.
     * 
     * @return true si el atacante ha ganado, false en caso contrario.
     * @throws CoordenadasIncorrectasException Cuando las coordenadas son incorrectas.
     */
    public boolean haGanadoAtacante() throws CoordenadasIncorrectasException;

    /**
     * Verifica si el rey ha ganado el juego.
     * 
     * @return true si el rey ha ganado, false en caso contrario.
     * @throws CoordenadasIncorrectasException Cuando las coordenadas son incorrectas.
     */
    public boolean haGanadoRey() throws CoordenadasIncorrectasException;

    /**
     * Realiza un movimiento en el juego.
     * 
     * @param jugada La jugada a realizar.
     * @throws CoordenadasIncorrectasException Cuando las coordenadas son incorrectas.
     */
    public void mover(Jugada jugada) throws CoordenadasIncorrectasException;

    /**
     * Realiza las capturas correspondientes después de un movimiento.
     * 
     * @throws CoordenadasIncorrectasException Cuando las coordenadas son incorrectas.
     */
    public void realizarCapturasTrasMover() throws CoordenadasIncorrectasException;

    /**
     * Coloca las piezas según la configuración inicial del juego.
     */
    public void colocarPiezasConfiguracionInicial();
}
