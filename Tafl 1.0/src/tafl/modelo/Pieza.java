package tafl.modelo;

import java.util.Objects;

import tafl.util.Color;
import tafl.util.TipoPieza;

/**
 * Clase que representa una pieza en el juego Brandubh.
 * 
 * @author Jon Ander Incera Moreno
 * Creado 31/10/2023
 * @version 1.0 realizacion del codigo 
 * 			1.1 comentarios
 * 
 */
public class Pieza {

    private TipoPieza tipoPieza;

    /**
     * Constructor de una pieza con un tipo de pieza específico.
     *
     * @param tipoPieza El tipo de pieza (DEFENSOR, ATACANTE o REY).
     */
    public Pieza(TipoPieza tipoPieza) {
        this.tipoPieza = tipoPieza;
    }

    /**
     * Crea una copia (clon) de la pieza.
     *
     * @return Una copia de la pieza.
     */
    public Pieza clonar() {
        return new Pieza(tipoPieza);
    }

    /**
     * Consulta el color de la pieza (BLANCO o NEGRO).
     *
     * @return El color de la pieza.
     */
    public Color consultarColor() {
        if (TipoPieza.ATACANTE == tipoPieza) {
            return Color.NEGRO;
        } else {
            return Color.BLANCO;
        }
    }

    /**
     * Consulta el tipo de pieza (DEFENSOR, ATACANTE o REY).
     *
     * @return El tipo de pieza.
     */
    public TipoPieza consultarTipoPieza() {
        return tipoPieza;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pieza other = (Pieza) obj;
        return tipoPieza == other.tipoPieza;
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoPieza);
    }

    @Override
    public String toString() {
        return "Pieza [tipoPieza=" + tipoPieza + "]";
    }
}

