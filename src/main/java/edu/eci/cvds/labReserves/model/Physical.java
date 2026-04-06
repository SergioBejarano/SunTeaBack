package edu.eci.cvds.labReserves.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a physical resource available in a laboratory, such as projectors, televisions and computers.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Physical extends Resource {

    /**
     * -- GETTER --
     *  Gets the resource has a projector available.
     *
     *
     * -- SETTER --
     *  Set the resource has a projector available.
     *
     @return true if it has a projector, false otherwise.
      * @param projector true if it has a projector, false otherwise.
     */
    private boolean projector;
    /**
     * -- GETTER --
     *  Get the resource has a TV available.
     *
     *
     * -- SETTER --
     *  Set the resource has a TV available.
     *
     @return true if it has a TV, false otherwise.
      * @param TV true if it has a TV, false otherwise.
     */
    private boolean TV;
    /**
     * -- GETTER --
     *  Get the total number of computers available on the resource.
     *
     *
     * -- SETTER --
     *  Set the total number of computers available on the resource.
     *
     @return Total number of computers.
      * @param totalComputer Total number of computers.
     */
    private int totalComputer;

    /**
     * Constructor that initializes the attributes of the Physical class.
     *
     * @param name Name of the physical resource.
     * @param projector Indicates if the resource has a projector.
     * @param TV Indicates if the resource has a TV set.
     * @param totalComputer Total number of available computers.
     */
    public Physical(String name, boolean projector, boolean TV, int totalComputer) {
        super(name);
        this.projector = projector;
        this.TV = TV;
        this.totalComputer = totalComputer;
    }
}
