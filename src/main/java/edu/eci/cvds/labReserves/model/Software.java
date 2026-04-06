package edu.eci.cvds.labReserves.model;

/**
 * Represents a software resource in the lab reservation system.
 * Extends the Resource class and includes additional information about the operating system and partition.
 */
public class Software extends Resource {

    private String operativeSystem;
    private boolean partition;

    /**
     * Default constructor.
     * Initializes a Software instance with no specific values.
     */
    public Software() {
        super();
    }

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

    // Getters y setters

    /**
     * Get the operating system on which the software is running.
     *
     * @return The operating system of the software.
     */
    public String getOperativeSystem() {
        return operativeSystem;
    }

    /**
     * Set the operating system on which the software runs.
     *
     * @param operativeSystem The operating system to assign.
     */
    public void setOperativeSystem(String operativeSystem) {
        this.operativeSystem = operativeSystem;
    }

    /**
     * Get the software requires a special partition.
     *
     * @return true if partition is required, false otherwise.
     */
    public boolean isPartition() {
        return partition;
    }

    /**
     * Set the software requires a special partition.
     *
     * @param partition true if partition is required, false otherwise.
     */
    public void setPartition(boolean partition) {
        this.partition = partition;
    }
}
