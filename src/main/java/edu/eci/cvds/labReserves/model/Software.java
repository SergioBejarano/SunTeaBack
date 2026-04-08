package edu.eci.cvds.labReserves.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a software resource in the lab reservation system.
 * Extends the Resource class and includes additional information about the operating system and partition.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Software extends Resource {

    /**
     * -- GETTER --
     *  Get the operating system on which the software is running.
     * -- SETTER --
     *  Set the operating system on which the software runs.
     *
     @return The operating system of the software.
      * @param operativeSystem The operating system to assign.
     */
    private String operativeSystem;
    /**
     * -- GETTER --
     *  Get the software requires a special partition.
     * -- SETTER --
     *  Set the software requires a special partition.
     *
     @return true if partition is required, false otherwise.
      * @param partition true if partition is required, false otherwise.
     */
    private boolean partition;

    /**
     * Constructor with parameters.
     *
     * @param name Name of the software.
     * @param operativeSystem Operating system the software runs on.
     * @param partition Indicates if the software requires a special partition.
     */
    public Software(String name, String operativeSystem, boolean partition) {
        super(name);
        this.operativeSystem = operativeSystem;
        this.partition = partition;
    }
}
