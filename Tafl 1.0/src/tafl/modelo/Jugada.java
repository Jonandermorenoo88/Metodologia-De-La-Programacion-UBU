package tafl.modelo;

import tafl.util.Coordenada;
import tafl.util.Sentido;

/**
 * Esta clase representa una jugada en el juego Brandubh, que consiste en un movimiento
 * desde una celda de origen a una celda de destino.
 * 
 * @author Jon Ander Incera Moreno
 * Creado 31/10/2023
 * @version 1.0 realizacion del codigo 
 * 			1.1 comentarios
 * 
 */
public record Jugada(Celda origen, Celda destino) {

    /**
     * Consulta el sentido del movimiento de la jugada (horizontal o vertical).
     *
     * @return El sentido del movimiento (Sentido.VERTICAL_N, Sentido.VERTICAL_S,
     * Sentido.HORIZONTAL_E, Sentido.HORIZONTAL_O) o null si no se puede determinar el sentido.
     */
    public Sentido consultarSentido() {
        if (origen == null || destino == null) {
            return null;
        }
        if (!esMovimientoHorizontalOVertical()) {
            return null;
        }

        Coordenada cOrigen = origen.consultarCoordenada();
        Coordenada cDestino = destino.consultarCoordenada();

        if (cOrigen.columna() == cDestino.columna()) {
            if (cDestino.fila() < cOrigen.fila()) {
                return Sentido.VERTICAL_N;
            } else {
                return Sentido.VERTICAL_S;
            }
        } else {
            if (cDestino.columna() < cOrigen.columna()) {
                return Sentido.HORIZONTAL_O;
            } else {
                return Sentido.HORIZONTAL_E;
            }
        }
    }

    /**
     * Verifica si la jugada es un movimiento horizontal o vertical.
     *
     * @return true si la jugada es horizontal o vertical, false de lo contrario.
     */
    public boolean esMovimientoHorizontalOVertical() {
        if (origen == null || destino == null || origen.equals(destino)
                || origen.consultarCoordenada().equals(destino.consultarCoordenada())) {
            return false;
        }
        return (origen.consultarCoordenada().fila() == destino.consultarCoordenada().fila())
                || (origen.consultarCoordenada().columna() == destino.consultarCoordenada().columna());
    }
}
