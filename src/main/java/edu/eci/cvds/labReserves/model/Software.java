package edu.eci.cvds.labReserves.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a software resource in the lab reservation system.
 * Extends the Resource class with OS and partition info.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Software extends Resource {

    /** The operating system of the software. */
    private String operativeSystem;

    /** Indicates if a special partition is required. */
    private boolean partition;

    /**
     * Constructor with parameters.
     *
     * @param pName Name of the software.
     * @param pOperativeSystem Operating system the software runs on.
     * @param pPartition Indicates if a special partition is required.
     */
    public Software(final String pName, final String pOperativeSystem,
                    final boolean pPartition) {
        super(pName);
        this.operativeSystem = pOperativeSystem;
        this.partition = pPartition;
    }
}
