package edu.eci.cvds.labReserves.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a physical resource in a laboratory,
 * such as projectors, televisions and computers.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Physical extends Resource {

    /** Indicates if the resource has a projector available. */
    private boolean projector;

    /** Indicates if the resource has a television available. */
    private boolean tv;

    /** The total number of computers available. */
    private int totalComputer;

    /**
     * Constructor that initializes the attributes of the Physical class.
     *
     * @param pName Name of the physical resource.
     * @param pProjector Indicates if it has a projector.
     * @param pTv Indicates if it has a TV set.
     * @param pTotalComputer Total number of available computers.
     */
    public Physical(final String pName, final boolean pProjector,
                    final boolean pTv, final int pTotalComputer) {
        super(pName);
        this.projector = pProjector;
        this.tv = pTv;
        this.totalComputer = pTotalComputer;
    }
   /**
     * Indica si el laboratorio físico cuenta con televisor.
     *
     * @return true si tiene TV, false de lo contrario.
     */
    public final boolean isTV() {
        return tv;
    }

    /**
     * Define si el laboratorio físico cuenta con televisor.
     *
     * @param pTv booleano que indica la presencia de TV.
     */
    public final void setTV(final boolean pTv) {
        this.tv = pTv;
    }
}
